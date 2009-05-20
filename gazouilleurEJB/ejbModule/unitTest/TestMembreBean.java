package unitTest;

import javax.naming.*;

import org.jboss.ejb3.interceptors.registry.InterceptorRegistry;

import junit.framework.TestCase;
import ejb.*;
import entity.*;
import exception.MembreException;


public class TestMembreBean extends TestCase {
	private Context annuaire;
	
	protected void setUp() throws Exception {
		annuaire = new InitialContext();
		annuaire.addToEnvironment(InitialContext.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		annuaire.addToEnvironment(InitialContext.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		annuaire.addToEnvironment(InitialContext.PROVIDER_URL, "jnp://localhost:1099");
	}

	public void testCreerMembre() throws MembreException, NamingException { 
		Membre m = new Membre();
		m.setPseudo("Toto");
		MembreFacade facade = (MembreFacade) annuaire.lookup("MembreBean");
		facade.creerMembre(m);
	}
	
	public void testGetByPseudo() throws MembreException, NamingException {
		MembreFacade facade = (MembreFacade) annuaire.lookup("MembreBean");
		Membre m = facade.getByPseudo("Toto");
		
		assertEquals("Toto", m.getPseudo());
	}
	
	public void testGetById() throws MembreException, NamingException {
		MembreFacade facade = (MembreFacade) annuaire.lookup("MembreBean");
		
		Membre m = facade.getById(1);
		
		assertEquals(1, m.getId());
	}
	
	public void testGetByEmail() throws MembreException, NamingException {
		Membre m1 = new Membre();
		m1.setEmail("test@test.com");
		MembreFacade facade = (MembreFacade) annuaire.lookup("MembreBean");
		facade.creerMembre(m1);
		
		Membre m2 = facade.getByEmail("test@test.com");
		
		assertEquals("test@test.com", m2.getEmail());
	}

	public void testConnexionMembre() {
		// TODO compléter méthode
		fail("compléter test case");
	}

	public void testDeconnexionMembre() {
		// TODO compléter méthode
		fail("compléter test case");
	}

	public void testUpdateMembre() throws MembreException, NamingException {
		MembreFacade facade = (MembreFacade) annuaire.lookup("MembreBean");
		
		Membre m2 = facade.getByPseudo("Toto");
		m2.setEmail("adresse@mail.com");
		m2.setPassword("12345678");
		facade.updateMembre(m2);
		
		Membre m3 = facade.getByPseudo("Toto");
		assertEquals("adresse@mail.com", m3.getEmail());
	}

	public void testAjouterAmi() {
		// TODO compléter méthode
		fail("compléter test case");
	}

	public void testSupprimerAmi() {
		// TODO compléter méthode
		fail("compléter test case");
	}

	public void testGetSuivi() {
		// TODO compléter méthode
		fail("compléter test case");
	}

	public void testGetSuiveur() {
		// TODO compléter méthode
		fail("compléter test case");
	}

	public void testRechercheByPseudo() {
		// TODO compléter méthode
		fail("compléter test case");
	}
	
	public void testSupprimerMembre() throws NamingException {
		MembreFacade facade = (MembreFacade) annuaire.lookup("MembreBean");
		Membre m1 = facade.getById(1);
		facade.supprimerMembre(m1);
	}
}
