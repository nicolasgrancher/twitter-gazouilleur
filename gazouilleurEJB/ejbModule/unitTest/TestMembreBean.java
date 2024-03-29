package unitTest;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.naming.*;

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
		m.setNom("Dupond");
		m.setPrenom("Jean");
		m.setEmail("jean@dupond.com");
		m.setPassword("87654321");
		MembreFacade facade = (MembreFacade) annuaire.lookup("MembreBean");
		m = facade.creerMembre(m);
		assertNotSame(0, m.getId());
		assertNotNull(m.getPseudo());
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
		m1.setPseudo("test");
		m1.setNom("Gamotte");
		m1.setPrenom("Albert");
		m1.setEmail("test@test.com");
		m1.setPassword("12345678");
		MembreFacade facade = (MembreFacade) annuaire.lookup("MembreBean");
		m1 = facade.creerMembre(m1);
		
		Membre m2 = facade.getByEmail("test@test.com");
		
		assertEquals("test@test.com", m2.getEmail());
	}

	public void testConnexionMembre() throws MembreException, NamingException {
		MembreFacade facade = (MembreFacade) annuaire.lookup("MembreBean");
		Membre m = new Membre();
		m.setPseudo("Jean");
		m.setNom("Louis");
		m.setPrenom("Jean");
		m.setPassword("12345678");
		m.setEmail("jean-louis@free.fr");
		m = facade.creerMembre(m);
		Membre membre = facade.connexionMembre("Jean", "12345678");
		assertNotNull(membre);
	}

	public void testDeconnexionMembre() throws MembreException, NamingException {
		MembreFacade facade = (MembreFacade) annuaire.lookup("MembreBean");
		Membre membre = facade.getByPseudo("Jean");
		membre = facade.deconnexionMembre(membre);
		assertNull(membre);
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
	
	public void testGetSuivi() throws MembreException, NamingException {
		MembreFacade facade = (MembreFacade) annuaire.lookup("MembreBean");
		Membre membre = facade.getByPseudo("Toto");
		membre.setListSuivis((List<Membre>) facade.getSuivi(membre));
		assertNotNull(membre.getListSuivis());
	}

	public void testGetSuiveur() throws MembreException, NamingException {
		MembreFacade facade = (MembreFacade) annuaire.lookup("MembreBean");
		Membre membre = facade.getByPseudo("Toto");
		membre.setListSuiveurs((List<Membre>) facade.getSuiveur(membre));
		assertNotNull(membre.getListSuiveurs());
	}

	public void testAjouterAmi() throws MembreException, NamingException {
		MembreFacade facade = (MembreFacade) annuaire.lookup("MembreBean");
		Membre membre = facade.getByPseudo("Toto");
		Membre ami = new Membre();
		ami.setPseudo("Titi");
		ami.setNom("Titi");
		ami.setPrenom("Titi");
		ami.setPassword("12345678");
		ami.setEmail("titi@toto.com");
		ami = facade.creerMembre(ami);
		
		facade.ajouterAmi(membre, ami);
		
		Membre membre2 = facade.getByPseudo("Toto");
		membre2.setListSuivis((List<Membre>) facade.getSuivi(membre2));
		
		assertEquals(ami, membre2.getListSuivis().get(0));
	}

	public void testSupprimerAmi() throws MembreException, NamingException {
		MembreFacade facade = (MembreFacade) annuaire.lookup("MembreBean");
		Membre membre = facade.getByPseudo("Toto");
		Membre ami = facade.getByPseudo("Titi");
		
		facade.supprimerAmi(membre, ami);
		
		Membre membre2 = facade.getByPseudo("Toto");
		membre2.setListSuivis((List<Membre>) facade.getSuivi(membre2));
		
		assertEquals(0, membre2.getListSuivis().size());
	}

	public void testRechercheByPseudo() throws MembreException, NamingException {
		MembreFacade facade = (MembreFacade) annuaire.lookup("MembreBean");
		Collection<Membre> membres = facade.rechercheByPseudo("Toto");
		Iterator<Membre> it = membres.iterator();
		while (it.hasNext()) {
			Membre membre = (Membre) it.next();
			assertEquals("Toto", membre.getPseudo());
		}
	}
	
	public void testRechercheByEmail() throws MembreException, NamingException {
		MembreFacade facade = (MembreFacade) annuaire.lookup("MembreBean");
		Collection<Membre> membres = facade.rechercheByPseudo("adresse@mail.com");
		Iterator<Membre> it = membres.iterator();
		while (it.hasNext()) {
			Membre membre = (Membre) it.next();
			assertEquals("adresse@mail.com", membre.getPseudo());
		}
	}
	
	public void testSupprimerMembre() throws MembreException, NamingException {
		MembreFacade facade = (MembreFacade) annuaire.lookup("MembreBean");
		Membre m1 = facade.getByPseudo("Toto");
		facade.supprimerMembre(m1);
		
		try {
			facade.getByPseudo("Toto");
			// si l'execution continue jusqu'ici c'est que le membre "Toto" n'a pas été correctement supprimé
			fail();
		}
		catch (MembreException e) {
			// si une exception est levée c'est que le membre "Toto" n'existe pas. Il a donc bien été supprimé
			assertTrue(true);
		}
	}
}
