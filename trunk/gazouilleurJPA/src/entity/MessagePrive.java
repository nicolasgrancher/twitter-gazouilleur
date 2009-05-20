package entity;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "message_prive")
public class MessagePrive {
	
	@Id	
	@GeneratedValue
	private Long id;
	
	private String message;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="emetteur")
	private Membre emetteur;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="destinataire")
	private Membre destinataire;
	
	
	@Temporal(TemporalType.DATE)
	private Date date;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEmetteur(Membre emetteur) {
		this.emetteur = emetteur;
	}
	public Membre getEmetteur() {
		return emetteur;
	}
	public void setDestinataire(Membre destinataire) {
		this.destinataire = destinataire;
	}
	public Membre getDestinataire() {
		return destinataire;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getDate() {
		return date;
	}
	
	@Override
	public int hashCode() {
		// On choisit les deux nombres impairs
		final int prime = 17;
		int result = 7;
		// Pour chaque attribut, on calcule le hashcode
	    // que l'on ajoute au résultat après l'avoir multiplié
	    // par le nombre "multiplieur" :
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((emetteur == null) ? 0 : emetteur.hashCode());
		result = prime * result + ((destinataire == null) ? 0 : destinataire.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
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
		MessagePrive other = (MessagePrive) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (emetteur == null) {
			if (other.emetteur != null)
				return false;
		} else if (!emetteur.equals(other.emetteur))
			return false;
		if (destinataire == null) {
			if (other.destinataire != null)
				return false;
		} else if (!destinataire.equals(other.destinataire))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return date.toString() +"\n"+ emetteur.toString() +" @ "+ destinataire + " :\n " + message;
	}
	
}
