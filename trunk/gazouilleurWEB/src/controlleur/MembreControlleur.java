package controlleur;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;

import org.richfaces.component.html.HtmlModalPanel;

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
	private boolean estConnecte;
	private String password2;
	
	private boolean closePanelInscription = false;
	private boolean closePanelConnexion = false;
	
	public String creerMembre() {
		closePanelInscription = false;
		try {
//			if(!verifierPassword()){
//				throw new Exception("Les mots de passe ne correspondent pas.");
//			}
			System.out.println(membre);
			//System.out.println(membre);
			membre = membreFacade.creerMembre(membre);
			closePanelInscription = true;
			estConnecte = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//membre.setPseudo(membre.getPassword());
		//System.out.println(membre);
		return "membre.cree";
	}
	
	public String deconnexion(){
		membre = new Membre();
		estConnecte = false;
		return "deconnexion";
	}
	
	public String connexion(){
		closePanelConnexion = false;
		membre = membreFacade.connexionMembre(membre.getPseudo(), membre.getPassword());
		estConnecte = true;
		closePanelConnexion = true;
		return "connexion";
	}
	
	private boolean verifierPassword() {
		return password2.equals(membre.getPassword());
	}

	public Membre getMembre() {
		return membre;
	}

	public void setMembre(Membre membre) {
		this.membre = membre;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public boolean isClosePanelInscription() {
		return closePanelInscription;
	}

	public void setClosePanelInscription(boolean closePanelInscription) {
		this.closePanelInscription = closePanelInscription;
	}

	public boolean isEstConnecte() {
		return estConnecte;
	}

	public void setEstConnecte(boolean estConnecte) {
		this.estConnecte = estConnecte;
	}

	public boolean isClosePanelConnexion() {
		return closePanelConnexion;
	}

	public void setClosePanelConnexion(boolean closePanelConnexion) {
		this.closePanelConnexion = closePanelConnexion;
	}
}
