package ejb;


import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entity.Membre;
import entity.MessagePrive;

@Stateless(mappedName="MessagePriveBean")
public class MessagePriveBean implements MessagePriveFacade {
	
	@PersistenceContext(unitName = "gazouilleurJPA")
	protected EntityManager manager;
	
	public MessagePrive getById(int id) {
		// TODO méthode à compléter
		return null;
	}
    	
	public MessagePrive envoyerMessagePrive(MessagePrive message) {
		// TODO méthode à compléter
		return null;
	}
	
	public Collection<MessagePrive> getMessagesPrives(Membre membre){
		// TODO méthode à compléter
		return null;
	}
	
	public Collection<MessagePrive> getMessagesPrivesNonLus(Membre membre){
		// TODO méthode à compléter
		return null;
	}
	
	public Collection<MessagePrive> getMessagesPrivesLus(Membre membre){
		// TODO méthode à compléter
		return null;
	}
	
	public MessagePrive supprimerMessagePrive(MessagePrive message) {
		// TODO méthode à compléter
		return null;
	}
 }