package unitTest;

import java.util.Date;

import entity.*;
import junit.framework.TestCase;

public class TestMessagePrive
extends TestCase
{
	private MessagePrive m1;
	private MessagePrive m2;
	private MessagePrive m3;
	private Membre e1;
	private Membre e2;
	private Membre d1;
	private Membre d2;
	private Date date1;
	
	protected void setUp()
	throws Exception {
		m1 = new MessagePrive();
		m2 = new MessagePrive();
		m3 = new MessagePrive();
		e1 = new Membre();
		e1.setPseudo("Toto");
		e2 = new Membre();
		e2.setPseudo("Toto");
		d1 = new Membre();
		d1.setPseudo("Bobo");
		d2 = new Membre();
		d2.setPseudo("Bobo");
		date1 = new Date();
		
		e1.setNom("Dupond");
		e1.setPrenom("Jean");
		e1.setEmail("test@test.com");
		e1.setPassword("12345678");
		e2.setPseudo("Toto");
		e2.setNom("Dupond");
		e2.setPrenom("Jean");
		e2.setEmail("test@test.com");
		e2.setPassword("12345678");
		
		d1.setNom("Martin");
		d1.setPrenom("Marc");
		d1.setEmail("testdest@test.com");
		d1.setPassword("98765432");
		d2.setPseudo("Bobo");
		d2.setNom("Martin");
		d2.setPrenom("Marc");
		d2.setEmail("testdest@test.com");
		d2.setPassword("98765432");
		
		m1.setId(1);
		m2.setId(2);
		m3.setId(3);
		m1.setMessage("hello world !");
		m2.setMessage("hello world !");
		m1.setEmetteur(e1);
		m2.setEmetteur(e2);
		m1.setDestinataire(d1);
		m2.setDestinataire(d2);
		m1.setDate(date1);
		m2.setDate(date1);
		
		m3.setDate(date1);
		m3.setEmetteur(e1);
		m3.setDestinataire(d1);
		m3.setMessage("You ain't what you should be!");
	}

	protected void tearDown()
	throws Exception {
		super.tearDown();
	}
	
	public void testId() {
		assertEquals(1, m1.getId());
	}
	public void testMessagePrive(){
		assertNotSame(m1, m2);
	}
	
	public void testMessage(){
		assertEquals("hello world !", m1.getMessage());
	}
	
	public void testEmetteur(){
		assertEquals(e1, m1.getEmetteur());
	}
	
	public void testDestinataire(){
		assertEquals(d1, m1.getDestinataire());
	}
	
	public void testDate(){
		assertEquals(date1, m1.getDate());
	}
	
	public void testHashCode() {
		assertEquals(m1.hashCode(), m2.hashCode());
		assertFalse(m1.hashCode() == m3.hashCode());
	}

	public void testEquals() {
		assertTrue(m1.equals(m2));
		assertFalse(m1.equals(m3));
	}
	
	public void testToString(){
		assertEquals(m1.toString(), m2.toString());
		assertFalse(m1.toString().equals(m3.toString()));
	}
	
}
