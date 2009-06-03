package entity;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.Email;
import org.hibernate.validator.NotEmpty;

@Entity
@NamedQueries({
	@NamedQuery(name="findByPseudo", query="SELECT m FROM Membre as m WHERE m.pseudo LIKE CONCAT('%',?1,'%')"),
	@NamedQuery(name="findByPseudoExact", query="SELECT m FROM Membre as m WHERE m.pseudo = ?1"),
	@NamedQuery(name="findByEmail", query="SELECT m FROM Membre as m WHERE m.email LIKE CONCAT('%',?1,'%')"),
	@NamedQuery(name="findByPseudoAndPassword", query="Select m FROM Membre m WHERE m.pseudo = ?1 AND m.password = ?2"),
	@NamedQuery(name="findAll", query="Select m FROM Membre m WHERE m.pseudo != ?1")
})
@Table(name = "membre", uniqueConstraints = {@UniqueConstraint(columnNames={"pseudo"})})
public class Membre implements Serializable, Comparable<Membre> {
	private static final long serialVersionUID = -3343648899161328469L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotEmpty(message="Le champ Pseudo ne doit pas être vide.")
	private String pseudo;
	
	@NotEmpty(message="Le champ Nom ne doit pas être vide.")
	private String nom;
	
	@NotEmpty(message="Le champ Prénom ne doit pas être vide.")
	private String prenom;
	
	@NotEmpty(message="Le champ Email ne doit pas être vide.")
	@Email(message="Le champs Email doit contenir une adresse e-mail.")
	private String email;
	
	@NotEmpty(message="Le champ Mot de passe ne doit pas être vide.")
	private String password;
	
	@OneToMany(mappedBy="emetteur", cascade = {CascadeType.REMOVE})
	private List<MessagePublic> messagesPublics;
	
	@OneToMany(mappedBy="destinataire", cascade = {CascadeType.REMOVE})
	private List<MessagePrive> messagesPrivesRecus;
	
	@OneToMany(mappedBy="emetteur", cascade = {CascadeType.REMOVE})
	private List<MessagePrive> messagesPrivesEmis;
	
	@ManyToMany(cascade = {CascadeType.REMOVE})
	@JoinTable(name = "suivis", joinColumns = @JoinColumn(name = "suiveur_num", referencedColumnName = "id"), 
									   inverseJoinColumns = @JoinColumn(name = "suivi_num", referencedColumnName = "id"))
    private List<Membre> listSuivis; // les amis que le membre suit
	
	@ManyToMany(cascade = {CascadeType.REMOVE}, mappedBy="listSuivis")
//	@JoinTable(name = "suivis", joinColumns = @JoinColumn(name = "suivi_num", referencedColumnName = "id"), 
//										inverseJoinColumns = @JoinColumn(name = "suiveur_num", referencedColumnName = "id"))
	private List<Membre> listSuiveurs; // les amis qui suivent le membre

	public Membre() {
		super();
		listSuivis = new ArrayList<Membre>();
		listSuiveurs = new ArrayList<Membre>();
		messagesPublics = new ArrayList<MessagePublic>();
		messagesPrivesRecus = new ArrayList<MessagePrive>();
		messagesPrivesEmis = new ArrayList<MessagePrive>();
	}
	
	public Membre (String pseudo, String nom, String prenom, String email, String password) {
        this();
        this.pseudo = pseudo;
        this.nom = nom;
		this.prenom = prenom;
        this.email = email;
        this.password = password;
    }
 
	public Membre(int id) {
		this();
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	
	public void setMessagesPublics(List<MessagePublic> messagesPublics) {
		this.messagesPublics = messagesPublics;
	}
	public List<MessagePublic> getMessagesPublics() {
		return messagesPublics;
	}
	public void setMessagesPrivesRecus(List<MessagePrive> messagesPrivesRecus) {
		this.messagesPrivesRecus = messagesPrivesRecus;
	}
	public List<MessagePrive> getMessagesPrivesRecus() {
		return messagesPrivesRecus;
	}
	public void setMessagesPrivesEmis(List<MessagePrive> messagesPrivesEmis) {
		this.messagesPrivesEmis = messagesPrivesEmis;
	}
	public List<MessagePrive> getMessagesPrivesEmis() {
		return messagesPrivesEmis;
	}
	
    public List<Membre> getListSuivis() {
    	listSuivis.size();
        return listSuivis;
    }
    public void setListSuivis(List<Membre> listSuivis) {
        this.listSuivis = listSuivis;
    }
	
    public List <Membre> getListSuiveurs() {
    	listSuiveurs.size();
		return listSuiveurs;
	}
	public void setListSuiveurs(List<Membre> listSuiveurs) {
        this.listSuiveurs = listSuiveurs;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pseudo == null) ? 0 : pseudo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Membre other = (Membre) obj;
		if (pseudo == null) {
			if (other.pseudo != null)
				return false;
		} else if (!pseudo.equals(other.pseudo))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return getPseudo();
	}
	
	//Methodes M�tiers
	public void ajouterSuivi(Membre membre) {
		if (!this.getListSuivis().contains(membre)) {
			this.getListSuivis().add(membre);
            membre.ajouterSuiveur(this);
        }
	}
	
    public void supprimerSuivi(Membre membre) {
		if (this.getListSuivis().contains(membre)) {
            this.getListSuivis().remove(membre);
            membre.supprimerSuiveur(this);
        }
	}
	
	public void ajouterSuiveur(Membre membre) {
	    if (!this.getListSuiveurs().contains(membre)) {
			this.getListSuiveurs().add(membre);
            membre.ajouterSuivi(this);
        }
    }
	
	public void supprimerSuiveur(Membre membre) {
		if (this.getListSuiveurs().contains(membre)) {
			membre.supprimerSuivi(this);
			this.getListSuiveurs().remove(membre);
		}
	}

	public int compareTo(Membre o) {
		return this.pseudo.compareTo(o.getPseudo());
	}
}
