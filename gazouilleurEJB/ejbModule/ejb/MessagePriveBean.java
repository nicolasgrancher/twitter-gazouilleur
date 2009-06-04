package ejb;


import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
	
	@SuppressWarnings("unchecked")
	public Collection<MessagePrive> rechercheMessagesPrives(Collection<String> motsCles, Membre membre){
		try {
			String expressionRecherche = "SELECT m FROM MessagePrive as m ";
			expressionRecherche += " WHERE (m.emetteur = " + membre.getId() + " OR m.destinataire = " + membre.getId() + ")";
			for (String motCle : motsCles) {
					expressionRecherche += " AND ";
				expressionRecherche += " m.message LIKE CONCAT('%','" + motCle + "','%') ";
			}
			Query q = entityMgr.createQuery(expressionRecherche);
			Collection<MessagePrive> messages = q.getResultList();
			Collections.sort((List<MessagePrive>) messages);
			Collections.reverse((List<MessagePrive>)messages);
			return messages;
		} catch(NoResultException e) {
			return null;
		}
	}
 }