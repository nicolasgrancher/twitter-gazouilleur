package controlleur;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;

import ejb.MembreFacade;
import entity.Membre;

public class MembreControlleur extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private static MembreFacade membreFacade;
	
	private Membre membre = new Membre();
	
	public String doCreerMembre() {
		try {
			System.out.println(membreFacade);
			//membre = membreFacade.creerMembre(membre);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		login = password;
		return "membre.cree";
	}

	public Membre getMembre() {
		return membre;
	}

	public void setMembre(Membre membre) {
		this.membre = membre;
	}
}
