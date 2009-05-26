package ejb;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entity.Membre;
import entity.MessagePublic;

@Stateless(mappedName="MessagePublicBean")
public class MessagePublicBean implements MessagePublicFacade {
	
	@PersistenceContext(unitName = "gazouilleurJPA")
	protected EntityManager entityMgr;
	
	public EntityManager getEntityMgr() {
		return entityMgr;
	}

	public void setEntityMgr(EntityManager entityMgr) {
		this.entityMgr = entityMgr;
	}

	public MessagePublic getById(int id) {
		return (MessagePublic) entityMgr.find(MessagePublic.class, id);
	}
	
	public MessagePublic publierMessagePublic(MessagePublic message){
		try{
			entityMgr.persist(message);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return message;
	}
	
	public void supprimerMessagePublic(MessagePublic message) {
		message = entityMgr.merge(message);
		entityMgr.remove(message);
	}
	
	public Collection<MessagePublic> getMessagesPublicsFrom(Membre membre){
		membre = getEntityMgr().find(Membre.class, membre.getId());
		Collection<MessagePublic> collection = membre.getMessagesPublics();
		collection.size(); // chargement de la collection persistée
		return collection;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<MessagePublic> rechercheMessagesPublics(Collection<String> motsCles){
		try {
			String expressionRecherche = "SELECT m FROM MessagePublic as m ";
			boolean first = true;
			for (String motCle : motsCles) {
				if(first) {
					expressionRecherche += " WHERE ";
					first = false;
				}else {
					expressionRecherche += " AND ";
				}
				expressionRecherche += " m.message LIKE CONCAT('%','" + motCle + "','%') ";
			}
			Query q = entityMgr.createQuery(expressionRecherche);
			Collection<MessagePublic> messages = q.getResultList();
			return messages;
		} catch(NoResultException e) {
			return null;
		}
	}
 }