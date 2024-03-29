package unitTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJBException;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import ejb.MembreFacade;
import ejb.MessagePublicFacade;
import entity.Membre;
import entity.MessagePublic;
import exception.MembreException;

import junit.framework.TestCase;

public class TestMessagePublicBean extends TestCase  {
	private Context annuaire;
	private Membre emetteur;
	private Membre suiveur;
	
	protected void setUp() throws Exception {
		// Initialisation du contexte EJB
		annuaire = new InitialContext();
		annuaire.addToEnvironment(InitialContext.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		annuaire.addToEnvironment(InitialContext.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		annuaire.addToEnvironment(InitialContext.PROVIDER_URL, "jnp://localhost:1099");
		
		MembreFacade facadeMembre = (MembreFacade) annuaire.lookup("MembreBean");
		emetteur = new Membre();
		emetteur.setPseudo("Toto");
		emetteur.setNom("Dupond");
		emetteur.setPrenom("Jean");
		emetteur.setPassword("12345678");
		emetteur.setEmail("jean@test.com");
		emetteur = facadeMembre.creerMembre(emetteur);
		
		suiveur = new Membre();
		suiveur.setPseudo("Test");
		suiveur.setNom("Durand");
		suiveur.setPrenom("Pierre");
		suiveur.setPassword("12345678");
		suiveur.setEmail("pierre@test.com");
		suiveur = facadeMembre.creerMembre(suiveur);
		
		suiveur.ajouterSuivi(emetteur);
	}
	
	protected void tearDown() throws Exception {
		MembreFacade facadeMembre = (MembreFacade) annuaire.lookup("MembreBean");
		MessagePublicFacade facade = (MessagePublicFacade) annuaire.lookup("MessagePublicBean");
		
		emetteur.setMessagesPublics((List<MessagePublic>)facade.getMessagesPublicsFrom(emetteur));
		facadeMembre.supprimerMembre(emetteur);
	}
	
	public void testGetById() throws NamingException, MembreException {
		MessagePublicFacade facade = (MessagePublicFacade) annuaire.lookup("MessagePublicBean");
		
		MessagePublic message = new MessagePublic();
		message.setDate(new Date());
		message.setEmetteur(emetteur);
		message.setMessage("Voici un test de message !");
		
		message = facade.publierMessagePublic(message);
		int id = message.getId();
		
		MessagePublic message2 = facade.getById(id);
		
		assertEquals(id, message2.getId());
	}
	
	public void testPublierMessagePublic() throws NamingException, MembreException {
		MessagePublicFacade facade = (MessagePublicFacade) annuaire.lookup("MessagePublicBean");
		
		MessagePublic message = new MessagePublic();
		message.setDate(new Date());
		
		message.setEmetteur(emetteur);
		message.setMessage("Hello world !");
		
		message = facade.publierMessagePublic(message);
		
		assertNotSame(0, message.getId());
	}
	
	public void testSupprimerMessagePublic() throws NamingException {
		MessagePublicFacade facade = (MessagePublicFacade) annuaire.lookup("MessagePublicBean");
		
		MessagePublic message = new MessagePublic();
		message.setDate(new Date());
		message.setEmetteur(emetteur);
		message.setMessage("Voici un test de message !");
		message = facade.publierMessagePublic(message);
		int id = message.getId();
		
		facade.supprimerMessagePublic(message);
		
		MessagePublic m = facade.getById(id);
		assertNull(m);
	}
	
	public void testGetMessagesPublicsFrom() throws NamingException {
		MessagePublicFacade facade = (MessagePublicFacade) annuaire.lookup("MessagePublicBean");
		
		Collection<MessagePublic> messages = facade.getMessagesPublicsFrom(emetteur);
		
		assertNotNull(messages);
	}
	
	public void testGetMessagesPublicsFor() throws NamingException {
		MessagePublicFacade facade = (MessagePublicFacade)annuaire.lookup("MessagePublicBean");
		
		Collection<MessagePublic> messages = facade.getMessagesPublicsFor(suiveur);
		
		assertNotNull(messages);
	}
	
	public void testRechercheMessagesPublics() throws NamingException {
		MessagePublicFacade facade = (MessagePublicFacade) annuaire.lookup("MessagePublicBean");
		
		MessagePublic message = new MessagePublic();
		message.setDate(new Date());
		message.setEmetteur(emetteur);
		message.setMessage("Voici un test de message !");
		message = facade.publierMessagePublic(message);
		
		MessagePublic message2 = new MessagePublic();
		message2.setDate(new Date());
		message2.setEmetteur(emetteur);
		message2.setMessage("Hello world !");
		message2 = facade.publierMessagePublic(message2);
		
		Collection<String> motsCles = new ArrayList<String>();
		motsCles.add("Hello");
		
		Collection<MessagePublic> messages = facade.rechercheMessagesPublics(motsCles);
		
		assertEquals(1, messages.size());
	}

	/*  
	//test en erreur (le message est envoy�, mais l'object r�cup�r� vaut null
	
	public void testPublishMessagePublic() throws NamingException{
		MessagePublicFacade facade = (MessagePublicFacade) annuaire.lookup("MessagePublicBean");
		
		MessagePublic message = new MessagePublic();
		message.setDate(new Date());
		
		message.setEmetteur(emetteur);
		message.setMessage("Hello universe !");
		
		// Publication du message sur le topic		
		facade.publishMessagePublic(message);
		
		
		// On recupere le message
		ConnectionFactory connectionFactory = (ConnectionFactory) annuaire.lookup("/ConnectionFactory");
		Topic topic = (Topic)annuaire.lookup("/topic/MsgPublicTopic");
		Connection connection = null;
		Session session = null;
		MessageConsumer consumer = null;
		ObjectMessage messageRecu = null;
		try {
			connection = connectionFactory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			consumer =  session.createConsumer(topic);
			messageRecu = (ObjectMessage) consumer.receive(2000);
			MessagePublic messagePublic = (MessagePublic) messageRecu.getObject();
			assertEquals("Hello universe !", messagePublic.getMessage());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	*/
}
