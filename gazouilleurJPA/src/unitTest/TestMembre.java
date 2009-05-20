package unitTest;

import java.util.ArrayList;
import java.util.List;

import entity.*;

import junit.framework.TestCase;

public class TestMembre extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testId() {
		Membre m = new Membre();
		m.setId(3);
		assertEquals(3, m.getId());
	}
	public void testPseudo() {
		Membre m = new Membre();
		m.setPseudo("Toto");
		assertEquals("Toto", m.getPseudo());
	}
	public void testNom() {
		Membre m = new Membre();
		m.setPseudo("Membre1");
		m.setNom("Martin");
		assertEquals("Martin", m.getNom());
	}
	public void testPrenom() {
		Membre m = new Membre();
		m.setPseudo("Membre1");
		m.setPrenom("Jean");
		assertEquals("Jean", m.getPrenom());
	}
	public void testEmail() {
		Membre m = new Membre();
		m.setPseudo("Membre1");
		m.setEmail("test@test.com");
		assertEquals("test@test.com", m.getEmail());
	}
	public void testPassword() {
		Membre m = new Membre();
		m.setPseudo("Toto");
		m.setPassword("12345678");
		assertEquals("12345678", m.getPassword());
	}
	public void testMessagesPublics() {
		Membre m = new Membre();
		m.setPseudo("Membre1");
		List<MessagePublic> messagesPublics = new ArrayList<MessagePublic>();
		m.setMessagesPublics(messagesPublics);
		for(int i=0 ; i < messagesPublics.size() ; i++) {
			assertEquals(messagesPublics.get(i), m.getMessagesPublics().get(i));
		}
	}
	public void testMessagesPrivesRecus() {
		Membre m = new Membre();
		m.setPseudo("Membre1");
		ArrayList<MessagePrive> messagesPrives = new ArrayList<MessagePrive>();
		m.setMessagesPrivesRecus(messagesPrives);
		for(int i=0 ; i < messagesPrives.size() ; i++) {
			assertEquals(messagesPrives.get(i), m.getMessagesPrivesRecus().get(i));
		}
	}
	public void testMessagesPrivesEmis() {
		Membre m = new Membre();
		m.setPseudo("Membre1");
		ArrayList<MessagePrive> messagesPrives = new ArrayList<MessagePrive>();
		m.setMessagesPrivesEmis(messagesPrives);
		for(int i=0 ; i < messagesPrives.size() ; i++) {
			assertEquals(messagesPrives.get(i), m.getMessagesPrivesEmis().get(i));
		}
	}
	public void testListSuivis() {
		Membre m = new Membre();
		m.setPseudo("Membre1");
		ArrayList<Membre> suivis = new ArrayList<Membre>();
		m.setListSuivis(suivis);
		for(int i=0 ; i < suivis.size() ; i++) {
			assertEquals(suivis.get(i), m.getListSuivis().get(i));
		}
	}
	public void testListSuiveurs() {
		Membre m = new Membre();
		m.setPseudo("Membre1");
		ArrayList<Membre> suiveurs = new ArrayList<Membre>();
		m.setListSuiveurs(suiveurs);
		for(int i=0 ; i < suiveurs.size() ; i++) {
			assertEquals(suiveurs.get(i), m.getListSuiveurs().get(i));
		}
	}
	public void testMembre() {
		Membre m1 = new Membre();
		m1.setPseudo("Membre1");
		Membre m2 = new Membre();
		m2.setPseudo("Membre2");
		assertNotSame(m1, m2);
	}
	public void testMembre2() {
		Membre m1 = new Membre("Membre1", "Martin", "Jean", "test@test.com", "12345678");
		assertEquals("Membre1", m1.getPseudo());
		assertEquals("Martin", m1.getNom());
		assertEquals("Jean", m1.getPrenom());
		assertEquals("test@test.com", m1.getEmail());
		assertEquals("12345678", m1.getPassword());
	}
	public void testMembre3() {
		Membre m1 = new Membre(1);
		assertEquals(1, m1.getId());
	}
	public void testHashCode() {
		Membre m1 = new Membre();
		m1.setPseudo("Toto");
		m1.setNom("Dupond");
		m1.setPrenom("Jean");
		m1.setEmail("test@test.com");
		m1.setPassword("12345678");
		
		Membre m2 = new Membre();
		m2.setPseudo("Toto");
		m2.setNom("Dupond");
		m2.setPrenom("Jean");
		m2.setEmail("test@test.com");
		m2.setPassword("12345678");
		
		Membre m3 = new Membre();
		m3.setPseudo("Bob");
		
		assertEquals(m1.hashCode(), m2.hashCode());
		assertTrue(m1.hashCode() != m3.hashCode());
	}
	public void testEquals() {
		Membre m1 = new Membre();
		m1.setPseudo("Toto");
		m1.setNom("Dupond");
		m1.setPrenom("Jean");
		m1.setEmail("test@test.com");
		m1.setPassword("12345678");
		
		Membre m2 = new Membre();
		m2.setPseudo("Toto");
		m2.setNom("Dupond");
		m2.setPrenom("Jean");
		m2.setEmail("test@test.com");
		m2.setPassword("12345678");
		
		Membre m3 = new Membre();
		m3.setPseudo("Bob");
		
		assertTrue(m1.equals(m2));
		assertTrue(!m1.equals(m3));
	}
	public void testToString() {
		Membre m1 = new Membre();
		m1.setPseudo("Toto");
		m1.setNom("Dupond");
		m1.setPrenom("Jean");
		m1.setEmail("test@test.com");
		m1.setPassword("12345678");
		
		Membre m2 = new Membre();
		m2.setPseudo("Toto");
		m2.setNom("Dupond");
		m2.setPrenom("Jean");
		m2.setEmail("test@test.com");
		m2.setPassword("12345678");
		
		assertEquals(m1.toString(), m2.toString());
	}
	
	public void testAjouterSuivi() {
		Membre m1 = new Membre();
		m1.setPseudo("Membre1");
		Membre m2 = new Membre();
		m2.setPseudo("Membre2");
		Membre m3 = new Membre();
		m3.setPseudo("Membre3");
		m1.ajouterSuivi(m2);
		m1.ajouterSuivi(m3);
		assertEquals(m2, m1.getListSuivis().get(0));
		assertEquals(m1, m2.getListSuiveurs().get(0));
		
		assertEquals(m3, m1.getListSuivis().get(1));
		assertEquals(m1, m3.getListSuiveurs().get(0));
	}
	public void testSupprimerSuivi() {
		Membre m1 = new Membre();
		m1.setPseudo("Membre1");
		Membre m2 = new Membre();
		m2.setPseudo("Membre2");
		Membre m3 = new Membre();
		m3.setPseudo("Membre3");
		m1.ajouterSuivi(m2);
		m1.ajouterSuivi(m3);
		m1.supprimerSuivi(m2);
		assertEquals(m3, m1.getListSuivis().get(0));
		assertFalse(m2.getListSuiveurs().contains(m1));
	}
	public void testAjouterSuiveur() {
		Membre m1 = new Membre();
		m1.setPseudo("Membre1");
		Membre m2 = new Membre();
		m2.setPseudo("Membre2");
		Membre m3 = new Membre();
		m3.setPseudo("Membre3");
		m1.ajouterSuiveur(m2);
		m1.ajouterSuiveur(m3);
		assertEquals(m1.getListSuiveurs().get(0), m2);
		assertEquals(m2.getListSuivis().get(0), m1);
		
		assertEquals(m3, m1.getListSuiveurs().get(1));
		assertEquals(m1, m3.getListSuivis().get(0));
	}
	public void testSupprimerSuiveur() {
		Membre m1 = new Membre();
		m1.setPseudo("Membre1");
		Membre m2 = new Membre();
		m2.setPseudo("Membre2");
		Membre m3 = new Membre();
		m3.setPseudo("Membre3");
		m1.ajouterSuiveur(m2);
		m1.ajouterSuiveur(m3);
		m1.supprimerSuiveur(m2);
		assertEquals(m3, m1.getListSuiveurs().get(0));
		assertFalse(m2.getListSuivis().contains(m1));
	}
}
