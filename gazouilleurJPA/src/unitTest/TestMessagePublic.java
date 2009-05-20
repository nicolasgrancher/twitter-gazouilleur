package unitTest;

import java.util.Date;

import entity.*;
import junit.framework.TestCase;

public class TestMessagePublic extends TestCase {
	
	public void testId() {
		MessagePublic m1 = new MessagePublic();
		m1.setId(1);
		assertEquals(1, m1.getId());
	}
	public void testMessage() {
		MessagePublic m1 = new MessagePublic();
		m1.setMessage("Bonjour à tous !");
		assertEquals("Bonjour à tous !", m1.getMessage());
	}
	public void testEmetteur() {
		MessagePublic m1 = new MessagePublic();
		Membre membre = new Membre();
		m1.setEmetteur(membre);
		assertEquals(membre, m1.getEmetteur());
	}
	public void testDate() {
		MessagePublic m1 = new MessagePublic();
		Date date = new Date(java.lang.System.currentTimeMillis());
		m1.setDate(date);
		assertEquals(date, m1.getDate());
	}
	public void testHashCode() {
		Date date = new Date(java.lang.System.currentTimeMillis());
		
		MessagePublic m1 = new MessagePublic();
		m1.setDate(date);
		m1.setMessage("Message1");
		
		MessagePublic m2 = new MessagePublic();
		m2.setDate(date);
		m2.setMessage("Message1");
		
		MessagePublic m3 = new MessagePublic();
		m3.setDate(new Date(java.lang.System.currentTimeMillis()-1000));
		m3.setMessage("Message1");
		
		assertEquals(m1.hashCode(), m2.hashCode());
		assertTrue(m1.hashCode() != m3.hashCode());
	}
	public void testEquals() {
		Membre emetteur1 = new Membre();
		emetteur1.setPseudo("Toto");
		Membre emetteur2 = new Membre();
		emetteur2.setPseudo("Titi");
		
		MessagePublic m1 = new MessagePublic();
		m1.setDate(new Date(java.lang.System.currentTimeMillis()));
		m1.setMessage("Message1");
		m1.setEmetteur(emetteur1);
		
		MessagePublic m2 = new MessagePublic();
		m2.setDate(new Date(java.lang.System.currentTimeMillis()));
		m2.setMessage("Message1");
		m2.setEmetteur(emetteur1);
		
		MessagePublic m3 = new MessagePublic();
		m3.setDate(new Date(java.lang.System.currentTimeMillis()));
		m3.setMessage("Message1");
		m3.setEmetteur(emetteur2);
		
		assertTrue(m1.equals(m2));
		assertFalse(m1.equals(m3));
	}
	public void testToString() {
		Membre emetteur1 = new Membre();
		emetteur1.setPseudo("Toto");
		Date date = new Date(java.lang.System.currentTimeMillis());
		
		MessagePublic m1 = new MessagePublic();
		m1.setDate(date);
		m1.setMessage("Message1");
		m1.setEmetteur(emetteur1);
		
		MessagePublic m2 = new MessagePublic();
		m2.setDate(date);
		m2.setMessage("Message1");
		m2.setEmetteur(emetteur1);
		
		assertEquals(m1.toString(), m2.toString());
	}
}
