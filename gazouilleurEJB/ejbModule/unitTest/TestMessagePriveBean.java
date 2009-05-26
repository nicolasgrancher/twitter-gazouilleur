package unitTest;

import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.MessagePriveFacade;
import entity.Membre;
import entity.MessagePrive;

import junit.framework.TestCase;

public class TestMessagePriveBean extends TestCase  {
	private Context annuaire;
	
	protected void setUp() throws Exception {
		annuaire = new InitialContext();
		annuaire.addToEnvironment(InitialContext.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		annuaire.addToEnvironment(InitialContext.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		annuaire.addToEnvironment(InitialContext.PROVIDER_URL, "jnp://localhost:1099");
	}
	
	public void testGetById() throws NamingException {
		MessagePriveFacade facade = (MessagePriveFacade) annuaire.lookup("MessagePriveBean");
		
		MessagePrive message = new MessagePrive();
		message.setDate(new Date());
		
		Membre destinataire = new Membre();
		destinataire.setPseudo("Titi");
		destinataire.setNom("Titi");
		destinataire.setPrenom("Titi");
		destinataire.setPassword("1234568");
		destinataire.setEmail("titi@test.com");
		
		Membre emetteur = new Membre();
		emetteur.setPseudo("Toto");
		destinataire.setNom("Toto");
		destinataire.setPrenom("Toto");
		destinataire.setPassword("1234568");
		destinataire.setEmail("toto@test.com");
		message.setEmetteur(emetteur);
		
		message.setMessage("Voici un test de message !");
		
		facade.envoyerMessagePrive(message);
		
		MessagePrive message2 = facade.getById(1);
		
		assertEquals(1, message2.getId());
	}
	
	public void testEnvoyerMessagePrive() {
		fail("compl�ter m�thode");
	}
	
	public void testGetMessagesPrives() {
		fail("compl�ter m�thode");
	}
	
	public void testGetMessagesPrivesNonLus() {
		fail("compl�ter m�thode");
	}
	
	public void testGetMessagesPrivesLus() {
		fail("compl�ter m�thode");
	}
	
	public void testSupprimerMessagePrive() {
		fail("compl�ter m�thode");
	}
}
