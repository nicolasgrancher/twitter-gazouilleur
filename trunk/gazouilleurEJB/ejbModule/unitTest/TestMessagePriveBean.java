package unitTest;

import java.util.ArrayList;
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
		for (int i = 0; i < emetteur.getMessagesPrivesEmis().size(); i++) {
			facade.supprimerMessagePrive(emetteur.getMessagesPrivesEmis().get(i));
			emetteur.getMessagesPrivesEmis().remove(i);
		}
		
		
		destinataire.setMessagesPrivesEmis((List<MessagePrive>)facade.getMessagesPrivesEmis(destinataire));
		for (int i = 0; i < destinataire.getMessagesPrivesEmis().size(); i++) {
			facade.supprimerMessagePrive(destinataire.getMessagesPrivesEmis().get(i));
			destinataire.getMessagesPrivesEmis().remove(i);
		}
		
		facadeMembre.supprimerMembre(emetteur);
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
	
	public void testRechercheMessagesPrives() throws NamingException {
		MessagePriveFacade facade = (MessagePriveFacade) annuaire.lookup("MessagePriveBean");
		
		MessagePrive message = new MessagePrive();
		message.setDate(new Date());
		message.setEmetteur(emetteur);
		message.setDestinataire(destinataire);
		message.setMessage("Voici un test de message !");
		message = facade.envoyerMessagePrive(message);
		
		MessagePrive message2 = new MessagePrive();
		message2.setDate(new Date());
		message2.setEmetteur(destinataire);
		message2.setDestinataire(emetteur);
		message2.setMessage("Hello world !");
		message2 = facade.envoyerMessagePrive(message2);
		
		Collection<String> motsCles = new ArrayList<String>();
		motsCles.add("test");
		
		Collection<MessagePrive> messages = facade.rechercheMessagesPrives(motsCles, emetteur);
		
		assertEquals(1, messages.size());
	}
}
