package controlleur;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.jms.Session;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import org.richfaces.component.html.HtmlModalPanel;

import com.sun.corba.se.spi.protocol.RequestDispatcherRegistry;
import com.sun.tools.ws.processor.model.Request;

import ejb.MembreFacade;
import ejb.MessagePriveFacade;
import ejb.MessagePublicFacade;
import entity.Membre;
import entity.MessagePrive;
import entity.MessagePublic;
import exception.MembreException;

public class MembreControlleur extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private static MembreFacade membreFacade;
	
	@EJB
	private static MessagePublicFacade messagePublicFacade;
	
	@EJB
	private static MessagePriveFacade messagePriveFacade;
	
	private Membre membre = new Membre();
	private boolean estConnecte;
	
	private String password2;
	private String messagePublic;
	private String messagePrive;
	private String ajoutSuivi;
	
	private boolean closePanelInscription = false;
	private boolean closePanelConnexion = false;
	private boolean suivisPollEnabled = false;

	private Collection<Membre> listeMembres = new ArrayList<Membre>();
	private Collection<MessagePublic> messagesPublics = new ArrayList<MessagePublic>();
	private Collection<MessagePrive> messagesPrivesEmis = new ArrayList<MessagePrive>();
	private Collection<MessagePrive> messagesPrivesRecus = new ArrayList<MessagePrive>();

	public String creerMembre() {
		closePanelInscription = false;
		System.out.println("membre = "+membre);
		System.out.println("id = "+membre.getId());
		try {
			if(!verifierPassword()){
				throw new MembreException("Les mots de passe ne correspondent pas.");
			}
			membre = membreFacade.creerMembre(membre);
			closePanelInscription = true;
			estConnecte = true;
			listerMembres();
		} catch (MembreException e) {
			FacesContext.getCurrentInstance().addMessage("formInscription", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		} catch (Exception e) {
			System.out.println("membre = "+membre);
			System.out.println("id = "+membre.getId());
			e.printStackTrace();
		}
		return null;
	}
	
	public String deconnexion(){
		membre = new Membre();
		estConnecte = false;
		initVar();
		return "deconnexion";
	}
	
	public String connexion(){
		closePanelConnexion = false;
		membre = membreFacade.connexionMembre(membre.getPseudo(), membre.getPassword());
		if(membre == null) {
			membre = new Membre();
			FacesContext.getCurrentInstance().addMessage("formConnexion", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login/mot de passe invalide", "Login/mot de passe invalide"));
			return null;
		}
		estConnecte = true;
		closePanelConnexion = true;
		recupererMessagesPublics();
		listerMembres();
		System.out.println(listeMembres);
		//return "connexion";
		return null;
	}
	
	public String ajouterAmi() {
		try {
			Membre ami = membreFacade.getByPseudo(ajoutSuivi);
			membre = membreFacade.ajouterAmi(membre, ami);
			recupererMessagesPublics();
		} catch (MembreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String supprimerAmi() {
		try {
			Membre ami = membreFacade.getByPseudo(ajoutSuivi);
			membre = membreFacade.supprimerAmi(membre, ami);
			recupererMessagesPublics();
		} catch (MembreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public String publierMessagePublic() {
		MessagePublic message = new MessagePublic();
		message.setMessage(messagePublic);
		message.setEmetteur(membre);
		message.setDate(new Date());
		messagePublicFacade.publierMessagePublic(message);
		return null;
	}
	
	public String publierMessagePrive() {
		MessagePrive message = new MessagePrive();
		message.setMessage(messagePrive);
		message.setEmetteur(membre);
		message.setDate(new Date());
		messagePriveFacade.envoyerMessagePrive(message);
		return null;
	}
	
	public String recupererMessagesPublics() {
		messagesPublics = messagePublicFacade.getMessagesPublicsFor(membre);
		return null;
	}
	
	public String recupererMessagesPrivesEmis() {
		messagesPrivesEmis = messagePriveFacade.getMessagesPrivesEmis(membre);
		return null;
	}
	
	public String recupererMessagesPrivesRecus() {
		messagesPrivesRecus = messagePriveFacade.getMessagesPrivesRecus(membre);
		return null;
	}
	
	private void initVar() {
		password2 = "";
		messagePublic = "";
		ajoutSuivi = "";
		listeMembres = null;
		messagesPublics = null;
	}
		
	private boolean verifierPassword() {
		return password2.equals(membre.getPassword());
	}
	
	public String listerMembres() {
		listeMembres = membreFacade.rechercheTous();
		return null;
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

	public String getMessagePublic() {
		return messagePublic;
	}

	public void setMessagePublic(String messagePublic) {
		this.messagePublic = messagePublic;
	}

	public Collection<Membre> getListeMembres() {
		return listeMembres;
	}

	public void setListeMembres(Collection<Membre> listeMembres) {
		this.listeMembres = listeMembres;
	}

	public boolean isSuivisPollEnabled() {
		return suivisPollEnabled;
	}
	
	public String setSuivisPollEnabledToTrue() {
		suivisPollEnabled = true;
		return null;
	}

	public void setSuivisPollEnabled(boolean suivisPollEnabled) {
		this.suivisPollEnabled = suivisPollEnabled;
	}
	
	public String setSuivisPollEnabledToFalse() {
		suivisPollEnabled = false;
		return null;
	}

	public void setAjoutSuivi(String ajoutSuivi) {
		this.ajoutSuivi = ajoutSuivi;
	}

	public String getAjoutSuivi() {
		return ajoutSuivi;
	}

	public Collection<MessagePublic> getMessagesPublics() {
		return messagesPublics;
	}

	public void setMessagesPublics(Collection<MessagePublic> messagesPublics) {
		this.messagesPublics = messagesPublics;
	}

	public Collection<MessagePrive> getMessagesPrivesEmis() {
		return messagesPrivesEmis;
	}

	public void setMessagesPrivesEmis(Collection<MessagePrive> messagesPrives) {
		this.messagesPrivesEmis = messagesPrives;
	}
	
	public Collection<MessagePrive> getMessagesPrivesRecus() {
		return messagesPrivesRecus;
	}

	public void setMessagesPrivesRecus(Collection<MessagePrive> messagesPrives) {
		this.messagesPrivesRecus = messagesPrives;
	}

	public static void setMessagePriveFacade(MessagePriveFacade messagePriveFacade) {
		MembreControlleur.messagePriveFacade = messagePriveFacade;
	}

	public static MessagePriveFacade getMessagePriveFacade() {
		return messagePriveFacade;
	}

	public String getMessagePrive() {
		return messagePrive;
	}

	public void setMessagePrive(String messagePrive) {
		this.messagePrive = messagePrive;
	}
}
