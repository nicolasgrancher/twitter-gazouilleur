package entity;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "membre", uniqueConstraints = {@UniqueConstraint(columnNames={"pseudo"})})
public class Membre implements Serializable {
	private static final long serialVersionUID = -3343648899161328469L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String pseudo;
	
	private String nom;
	
	private String prenom;
	
	private String email;
	
	private String password;
	
	@OneToMany(mappedBy="emetteur")
	private List<MessagePublic> messagesPublics;
	
	@OneToMany(mappedBy="emetteur")
	private List<MessagePrive> messagesPrivesRecus;
	
	@OneToMany(mappedBy="emetteur")
	private List<MessagePrive> messagesPrivesEmis;
	
	@ManyToMany
	@JoinTable(name = "membre_suivi", joinColumns = @JoinColumn(name = "membre_num", referencedColumnName = "id"), 
									   inverseJoinColumns = @JoinColumn(name = "suivi_num", referencedColumnName = "id"))
    private List<Membre> listSuivis; // les amis que le membre suit
	
	@ManyToMany
	@JoinTable(name = "membre_suiveur", joinColumns = @JoinColumn(name = "membre_num", referencedColumnName = "id"), 
										inverseJoinColumns = @JoinColumn(name = "suiveur_num", referencedColumnName = "id"))
	private List<Membre> listSuiveurs; // les amis qui suivent le membre

	public Membre() {
		super();
		listSuivis = new ArrayList<Membre>();
		listSuiveurs = new ArrayList<Membre>();
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
	public void setMessagesPublics(List<MessagePublic> messagesPublics) {
		this.messagesPublics = messagesPublics;
	}
	public List<MessagePublic> getMessagesPublics() {
		return messagesPublics;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
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
	
	@ManyToMany(fetch=FetchType.EAGER)
    public List<Membre> getListSuivis() {
        return listSuivis;
    }
    public void setListSuivis(List<Membre> listSuivis) {
        this.listSuivis = listSuivis;
    }
	
	@ManyToMany(fetch=FetchType.EAGER)
    public List <Membre> getListSuiveurs() {
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
		return getPseudo() + " (" + getPrenom()+ " " + getNom() + ")";
	}
	
	//Methodes Métiers
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
}
