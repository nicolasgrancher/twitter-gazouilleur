package ejb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
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
	
	// Used for JMS
	@Resource(mappedName="ConnectionFactory")
	private ConnectionFactory connectionFactory;
	@Resource(mappedName="topic/MsgPublicTopic")
	private Topic destinationMsgPublic;
	private Connection connection;
	
	// M�thodes appel�es avant et apr�s postage
	@PostConstruct
    public void openConnection() {
        try {
            connection = connectionFactory.createConnection();
        } catch (JMSException e) {
            //logger.throwing(cname, "openConnection", e); (voir page 132 du boukin pour ajouter les logs)
            throw new EJBException(e);
        }
    }

    @PreDestroy
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException e) {
                //logger.throwing(cname, "closeConnection", e); (idem)
                throw new EJBException(e);
            }
        }
    }
	
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
		Collections.sort((List<MessagePublic>) collection);
		Collections.reverse((List<MessagePublic>) collection);
		return collection;
	}
	
	public Collection<MessagePublic> getMessagesPublicsFor(Membre membre){
		membre = getEntityMgr().find(Membre.class, membre.getId());
		Collection<Membre> suivis = membre.getListSuivis();
		Iterator<Membre> iSuivis = suivis.iterator();
		List<MessagePublic> collection = new ArrayList<MessagePublic>();
		while(iSuivis.hasNext()) {
			collection.addAll(getMessagesPublicsFrom(iSuivis.next()));
		}
		collection.addAll(getMessagesPublicsFrom(membre)); // le membre voit ses propres messages
		Collections.sort(collection);
		Collections.reverse(collection);
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
	
	// A tester avec test unitaire
	public void publishMessagePublic(MessagePublic msgPublic){
		Session session = null;
		try{
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			MessageProducer producer = session.createProducer(destinationMsgPublic);
			
			// Create a message
            ObjectMessage objectMessage = session.createObjectMessage();
            
            // Add in header ??
            Membre emetteur = msgPublic.getEmetteur();
            objectMessage.setIntProperty("�metteur : ", emetteur.getId());
            
            objectMessage.setObject(msgPublic);
			
			producer.send(objectMessage);
			
		}catch (JMSException e){
			throw new EJBException(e);
		}finally{
			try {
                session.close();
            } catch (JMSException e) {
                throw new EJBException(e);
            }
		}
	}
 }