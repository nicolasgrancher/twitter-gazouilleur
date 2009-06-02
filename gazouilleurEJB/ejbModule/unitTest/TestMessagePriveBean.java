package unitTest;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.MembreFacade;
import ejb.MessagePriveFacade;
import entity.Membre;
import entity.MessagePrive;

import junit.framework.TestCase;

public class TestMessagePriveBean extends TestCase  {
	private Context annuaire;
	private Membre emetteur;
	private Membre destinataire;
	
	protected void setUp() throws Exception {
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
		
		destinataire = new Membre();
		destinataire.setPseudo("Titi");
		destinataire.setNom("Gamotte");
		destinataire.setPrenom("Albert");
		destinataire.setPassword("12345678");
		destinataire.setEmail("al@test.com");
		destinataire = facadeMembre.creerMembre(destinataire);
	}
	
	protected void tearDown() throws Exception {
		MembreFacade facadeMembre = (MembreFacade) annuaire.lookup("MembreBean");
		MessagePriveFacade facade = (MessagePriveFacade) annuaire.lookup("MessagePriveBean");
		
		emetteur.setMessagesPrivesEmis((List<MessagePrive>)facade.getMessagesPrivesEmis(emetteur));
		facadeMembre.supprimerMembre(emetteur);
		
		destinataire.setMessagesPrivesEmis((List<MessagePrive>)facade.getMessagesPrivesEmis(destinataire));
		facadeMembre.supprimerMembre(destinataire);
	}
	
	public void testGetById() throws NamingException {
		MessagePriveFacade facade = (MessagePriveFacade) annuaire.lookup("MessagePriveBean");
		
		MessagePrive message = new MessagePrive();
		message.setDate(new Date());
		message.setEmetteur(emetteur);
		message.setDestinataire(destinataire);
		message.setMessage("Voici un test de message !");
		
		message = facade.envoyerMessagePrive(message);
		int id = message.getId();
		
		MessagePrive message2 = facade.getById(id);
		assertEquals(id, message2.getId());
	}
	
	public void testEnvoyerMessagePrive() throws NamingException {
		MessagePriveFacade facade = (MessagePriveFacade) annuaire.lookup("MessagePriveBean");
		
		MessagePrive message = new MessagePrive();
		message.setDate(new Date());
		
		message.setEmetteur(emetteur);
		message.setDestinataire(destinataire);
		message.setMessage("Hello world !");
		
		message = facade.envoyerMessagePrive(message);
		
		assertNotSame(0, message.getId());
	}
	
	public void testGetMessagesPrivesEmis() throws NamingException {
		MessagePriveFacade facade = (MessagePriveFacade) annuaire.lookup("MessagePriveBean");
		
		Collection<MessagePrive> messages = facade.getMessagesPrivesEmis(emetteur);
		
		assertNotNull(messages);
	}
	
	public void testGetMessagesPrivesRecus() throws NamingException {
		MessagePriveFacade facade = (MessagePriveFacade) annuaire.lookup("MessagePriveBean");
		
		Collection<MessagePrive> messages = facade.getMessagesPrivesRecus(emetteur);
		
		assertNotNull(messages);
	}
	
	
	public void testSupprimerMessagePrive() throws NamingException {
		MessagePriveFacade facade = (MessagePriveFacade) annuaire.lookup("MessagePriveBean");
		
		MessagePrive message = new MessagePrive();
		message.setDate(new Date());
		message.setEmetteur(emetteur);
		message.setDestinataire(destinataire);
		message.setMessage("Voici un test de message !");
		message = facade.envoyerMessagePrive(message);
		int id = message.getId();
		
		facade.supprimerMessagePrive(message);
		
		MessagePrive m = facade.getById(id);
		assertNull(m);
	}
}
