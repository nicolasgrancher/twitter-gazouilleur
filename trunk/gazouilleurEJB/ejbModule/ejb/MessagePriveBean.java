package ejb;


import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entity.Membre;
import entity.MessagePrive;

@Stateless(mappedName="MessagePriveBean")
public class MessagePriveBean implements MessagePriveFacade {
	
	@PersistenceContext(unitName = "gazouilleurJPA")
	protected EntityManager entityMgr;
	
	public EntityManager getEntityMgr() {
		return entityMgr;
	}

	public void setEntityMgr(EntityManager entityMgr) {
		this.entityMgr = entityMgr;
	}

	public MessagePrive getById(int id) {
		return (MessagePrive) getEntityMgr().find(MessagePrive.class, id);
	}
    	
	public MessagePrive envoyerMessagePrive(MessagePrive message) {
		try{
			/*getEntityMgr().merge(message.getEmetteur());
			getEntityMgr().merge(message.getDestinataire());*/
			getEntityMgr().persist(message);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return message;
	}
	
	public Collection<MessagePrive> getMessagesPrivesEmis(Membre membre){
		membre = getEntityMgr().find(Membre.class, membre.getId());
		Collection<MessagePrive> collection = membre.getMessagesPrivesEmis();
		collection.size(); // chargement de la collection persistée
		Collections.sort((List<MessagePrive>) collection);
		Collections.reverse((List<MessagePrive>) collection);
		return collection;
	}
	
	public Collection<MessagePrive> getMessagesPrivesRecus(Membre membre){
		membre = getEntityMgr().find(Membre.class, membre.getId());
		Collection<MessagePrive> collection = membre.getMessagesPrivesRecus();
		collection.size(); // chargement de la collection persistée
		Collections.sort((List<MessagePrive>) collection);
		Collections.reverse((List<MessagePrive>) collection);
		return collection;
	}
	
	public void supprimerMessagePrive(MessagePrive message) {
		message = entityMgr.merge(message);
		entityMgr.remove(message);
	}
 }