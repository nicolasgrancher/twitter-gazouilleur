package unitTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.MembreFacade;
import ejb.MessagePublicFacade;
import entity.Membre;
import entity.MessagePublic;
import exception.MembreException;

import junit.framework.TestCase;

public class TestMessagePublicBean extends TestCase  {
	private Context annuaire;
	private Membre emetteur;
	
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
}
