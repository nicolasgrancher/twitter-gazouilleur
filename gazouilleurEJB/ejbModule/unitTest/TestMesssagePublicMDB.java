package unitTest;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import entity.Membre;

public class TestMesssagePublicMDB {
	private Context annuaire;
	private Membre emetteur;
	
	protected void setUp() throws Exception {
		// Initialisation du contexte EJB
		annuaire = new InitialContext();
		annuaire.addToEnvironment(InitialContext.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		annuaire.addToEnvironment(InitialContext.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		annuaire.addToEnvironment(InitialContext.PROVIDER_URL, "jnp://localhost:1099");
	}
	
	
	public void testOnMessage() throws NamingException{
		
	}
}
