package entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "message_public")
public class MessagePublic implements Serializable {
	private static final long serialVersionUID = 1300826293782127962L;

	@Id
	@GeneratedValue
	private int id;
	
	private String message;
	
	@ManyToOne
	@JoinColumn(name="emetteur")
	private Membre emetteur;
	
	private Date date;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setEmetteur(Membre emetteur) {
		this.emetteur = emetteur;
	}
	public Membre getEmetteur() {
		return emetteur;
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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((emetteur == null) ? 0 : emetteur.hashCode());
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
		MessagePublic other = (MessagePublic) obj;
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
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return date.toString() + emetteur.toString() + ": " + message;
	}
	
}
