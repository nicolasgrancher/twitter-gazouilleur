package controlleur;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServlet;

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
	private String supprSuivi;
	private String destinataireMessagePrive;
	
	private boolean closePanelInscription = false;
	private boolean closePanelConnexion = false;
	private boolean closePanelModifInfo = false;
	
	private boolean suivisPollEnabled = true;
	private boolean destinatairesPollEnabled = true;
	
	private boolean ongletHomeActif = false;
	private boolean ongletSuiveurActif = false;
	private boolean ongletMessageActif = false;

	private Collection<Membre> listeMembres = new ArrayList<Membre>();
	private Collection<MessagePublic> messagesPublics = new ArrayList<MessagePublic>();
	private Collection<MessagePublic> messagesPerso = new ArrayList<MessagePublic>();
	private Collection<MessagePrive> messagesPrivesEmis = new ArrayList<MessagePrive>();
	private Collection<MessagePrive> messagesPrivesRecus = new ArrayList<MessagePrive>();
	
	private Collection<String> motsClesRecherche = new ArrayList<String>();
	private Collection<MessagePublic> messagesPublicsRecherche = new ArrayList<MessagePublic>();
	private Collection<MessagePrive> messagesPrivesRecherche = new ArrayList<MessagePrive>();
	
	private int pageMessagesPublicsTableScroller = 1;
	private int pageMessagesPersosTableScroller = 1;
	private int pageMessagesPublicsRechercheTableScroller = 1;
	private int pageMessagesPrivesEmisTableScroller = 1;
	private int pageMessagesPrivesRecusTableScroller = 1;
	private int pageMessagesPrivesRechercheTableScroller = 1;

	public String creerMembre() {
		closePanelInscription = false;
		try {
			if(membre.getPassword() == ""){
				throw new MembreException("Le champ mot de passe ne doit pas être vide.");
			}
			if(!verifierPassword()){
				throw new MembreException("Les mots de passe ne correspondent pas.");
			}
			membre = membreFacade.creerMembre(membre);
			closePanelInscription = true;
			estConnecte = true;
			ongletHomeActif = true;
			ongletMessageActif = false;
			ongletSuiveurActif = false;
			pageMessagesPublicsTableScroller = 1;
			setPageMessagesPersosTableScroller(1);
			pageMessagesPublicsRechercheTableScroller = 1;
			pageMessagesPrivesEmisTableScroller = 1;
			pageMessagesPrivesRecusTableScroller = 1;
			pageMessagesPrivesRechercheTableScroller = 1;
			suivisPollEnabled = true;
			destinatairesPollEnabled = true;
			listerMembres();
		} catch (MembreException e) {
			FacesContext.getCurrentInstance().addMessage("formInscription", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String updateMembre() {
		closePanelModifInfo = false;
		try {
			if(membre.getPassword() == ""){
				throw new MembreException("Le champ mot de passe ne doit pas être vide.");
			}
			if(!verifierPassword()){
				throw new MembreException("Les mots de passe ne correspondent pas.");
			}
			membre = membreFacade.updateMembre(membre);
			membre.getListSuiveurs();
			membre.getListSuivis();
			setClosePanelModifInfo(true);
		} catch (MembreException e) {
			FacesContext.getCurrentInstance().addMessage("formModifInfo", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} 
	
	public String deconnexion(){
		membre = new Membre();
		estConnecte = false;
		suivisPollEnabled = false;
		destinatairesPollEnabled = false;
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
		suivisPollEnabled = true;
		destinatairesPollEnabled = true;
		closePanelConnexion = true;
		ongletHomeActif = true;
		ongletMessageActif = false;
		ongletSuiveurActif = false;
		pageMessagesPublicsTableScroller = 1;
		setPageMessagesPersosTableScroller(1);
		pageMessagesPublicsRechercheTableScroller = 1;
		pageMessagesPrivesEmisTableScroller = 1;
		pageMessagesPrivesRecusTableScroller = 1;
		pageMessagesPrivesRechercheTableScroller = 1;
		recupererMessagesPublics();
		recupererMessagesPerso();
		recupererMessagesPrivesEmis();
		recupererMessagesPrivesRecus();
		listerMembres();
		return null;
	}
	
	public String ajouterAmi() {
		try {
			Membre ami = membreFacade.getByPseudo(ajoutSuivi);
			membre = membreFacade.ajouterAmi(membre, ami);
			membre.setListSuiveurs((List<Membre>)membreFacade.getSuiveur(membre));
			membre.setListSuivis((List<Membre>)membreFacade.getSuivi(membre));
			recupererMessagesPublics();
		} catch (MembreException e) {
			FacesContext.getCurrentInstance().addMessage("ajoutSuiviForm", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}
		return null;
	}
	
	public String supprimerAmi() {
		try {
			Membre ami = membreFacade.getByPseudo(supprSuivi);
			membre = membreFacade.supprimerAmi(membre, ami);
			membre.setListSuiveurs((List<Membre>)membreFacade.getSuiveur(membre));
			membre.setListSuivis((List<Membre>)membreFacade.getSuivi(membre));
			recupererMessagesPublics();
		} catch (MembreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public String publierMessagePublic() {
		try {
			if(messagePublic.length() == 0) {
				throw new MembreException("Le message est vide");
			}
			if(messagePublic.length() > 140) {
				throw new MembreException("Le message ne doit pas excéder 140 caractères");
			}
			MessagePublic message = new MessagePublic();
			message.setMessage(messagePublic);
			message.setEmetteur(membre);
			message.setDate(new Date());
			messagePublicFacade.publierMessagePublic(message);
			recupererMessagesPerso();
			recupererMessagesPublics();
		} catch (MembreException e) {
			FacesContext.getCurrentInstance().addMessage("message_form", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}
		return null;
	}
	
	public String publierMessagePrive() {
		try {
			if(messagePrive.length() == 0) {
				throw new MembreException("Le message est vide");
			}
			if(messagePrive.length() > 140) {
				throw new MembreException("Le message ne doit pas excéder 140 caractères");
			}
			MessagePrive message = new MessagePrive();
			message.setMessage(messagePrive);
			message.setEmetteur(membre);
			message.setDestinataire(membreFacade.getByPseudo(destinataireMessagePrive));
			if(message.getDestinataire().equals(membre)) {
				throw new MembreException("Vous ne pouvez pas vous envoyer de message privé");
			}
			message.setDate(new Date());
			messagePriveFacade.envoyerMessagePrive(message);
			recupererMessagesPrivesEmis();
			recupererMessagesPrivesRecus();
		} catch (MembreException e) {
			FacesContext.getCurrentInstance().addMessage("messagesPrivesForm", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
		}
		return null;
	}
	
	public String recupererMessagesPublics() {
		messagesPublics = messagePublicFacade.getMessagesPublicsFor(membre);
		return null;
	}
	
	public String recupererMessagesPerso() {
		messagesPerso = messagePublicFacade.getMessagesPublicsFrom(membre);
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
		supprSuivi = "";
		listeMembres = null;
		messagesPublics = null;
		messagesPerso = null;
		messagesPrivesEmis = null;
		messagesPrivesRecus = null;
		ongletHomeActif = false;
		ongletMessageActif = false;
		ongletSuiveurActif = false;
	}
		
	private boolean verifierPassword() {
		return password2.equals(membre.getPassword());
	}
	
	public String listerMembres() {
		listeMembres = membreFacade.rechercheTous(membre);
		return null;
	}
	
	public String listerSuiveurs() {
		membre = membreFacade.rafraichirMembre(membre);
		membre.setListSuiveurs((List<Membre>)membreFacade.getSuiveur(membre));
		membre.getListSuiveurs();
		return null;
	}
	
	/*public String listerSuivis() {
		membre = membreFacade.rafraichirMembre(membre);
		membre.setListSuivis((List<Membre>)membreFacade.getSuivi(membre));
		System.out.println("Lister suivi");
		return null;
	}*/
	
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

	public Collection<MessagePublic> getMessagesPerso() {
		return messagesPerso;
	}

	public void setMessagesPerso(Collection<MessagePublic> messagesPerso) {
		this.messagesPerso = messagesPerso;
	}

	public void setDestinataireMessagePrive(String destinataireMessagePrive) {
		this.destinataireMessagePrive = destinataireMessagePrive;
	}

	public String getDestinataireMessagePrive() {
		return destinataireMessagePrive;
	}


	public void setClosePanelModifInfo(boolean closePanelModifInfo) {
		this.closePanelModifInfo = closePanelModifInfo;
	}

	public boolean isClosePanelModifInfo() {
		return closePanelModifInfo;
	}

	public String getMotsClesRecherche() {
		String s = "";
		for (int i = 0; i < motsClesRecherche.size(); i++) {
			s += ((ArrayList<String>)motsClesRecherche).get(i) + " ";
		}
		return s;
	}

	public void setMotsClesRecherche(String motsClesRecherche) {
		Collection<String> collection = new ArrayList<String>();
		String[] tab = motsClesRecherche.split(" ");
		for (int i = 0; i < tab.length; i++) {
			collection.add(tab[i]);
		}
		this.motsClesRecherche = collection;
	}
	
	public Collection<MessagePublic> getMessagesPublicsRecherche() {
		return messagesPublicsRecherche;
	}

	public void setMessagesPublicsRecherche(
			Collection<MessagePublic> messagesPublicsRecherche) {
		this.messagesPublicsRecherche = messagesPublicsRecherche;
	}
	
	public Collection<MessagePrive> getMessagesPrivesRecherche() {
		return messagesPrivesRecherche;
	}

	public void setMessagesPrivesRecherche(
			Collection<MessagePrive> messagesPrivesRecherche) {
		this.messagesPrivesRecherche = messagesPrivesRecherche;
	}

	public String rechercherMessagesPublics() {
		setMessagesPublicsRecherche(messagePublicFacade.rechercheMessagesPublics(motsClesRecherche));
		setPageMessagesPublicsRechercheTableScroller(1);
		return null;
	}
	
	public String rechercherMessagesPrives() {
		setMessagesPrivesRecherche(messagePriveFacade.rechercheMessagesPrives(motsClesRecherche, membre));
		setPageMessagesPrivesRechercheTableScroller(1);
		return null;
	}

	public void setOngletHomeActif(boolean ongletHomeActif) {
		this.ongletHomeActif = ongletHomeActif;
	}
	
	public void setOngletHomeActifToTrue() {
		this.ongletHomeActif = true;
	}
	
	public void setOngletHomeActifToFalse() {
		this.ongletHomeActif = false;
	}

	public boolean isOngletHomeActif() {
		return ongletHomeActif;
	}

	public void setOngletSuiveurActif(boolean ongletSuiveurActif) {
		this.ongletSuiveurActif = ongletSuiveurActif;
	}
	
	public void setOngletSuiveurActifToTrue() {
		this.ongletSuiveurActif = true;
	}
	
	public void setOngletSuiveurActifToFalse() {
		this.ongletSuiveurActif = false;
	}

	public boolean isOngletSuiveurActif() {
		return ongletSuiveurActif;
	}

	public void setOngletMessageActif(boolean ongletMessageActif) {
		this.ongletMessageActif = ongletMessageActif;
	}
	
	public void setOngletMessageActifToTrue() {
		this.ongletMessageActif = true;
	}
	
	public void setOngletMessageActifToFalse() {
		this.ongletMessageActif = false;
	}

	public boolean isOngletMessageActif() {
		return ongletMessageActif;
	}

	public void setPageMessagesPublicsTableScroller(
			int pageMessagesPublicsTableScroller) {
		this.pageMessagesPublicsTableScroller = pageMessagesPublicsTableScroller;
	}

	public int getPageMessagesPublicsTableScroller() {
		return pageMessagesPublicsTableScroller;
	}

	public void setPageMessagesPersosTableScroller(
			int pageMessagesPersosTableScroller) {
		this.pageMessagesPersosTableScroller = pageMessagesPersosTableScroller;
	}

	public int getPageMessagesPersosTableScroller() {
		return pageMessagesPersosTableScroller;
	}

	public int getPageMessagesPublicsRechercheTableScroller() {
		return pageMessagesPublicsRechercheTableScroller;
	}

	public void setPageMessagesPublicsRechercheTableScroller(
			int pageMessagesPublicsRechercheTableScroller) {
		this.pageMessagesPublicsRechercheTableScroller = pageMessagesPublicsRechercheTableScroller;
	}

	public int getPageMessagesPrivesEmisTableScroller() {
		return pageMessagesPrivesEmisTableScroller;
	}

	public void setPageMessagesPrivesEmisTableScroller(
			int pageMessagesPrivesEmisTableScroller) {
		this.pageMessagesPrivesEmisTableScroller = pageMessagesPrivesEmisTableScroller;
	}

	public int getPageMessagesPrivesRecusTableScroller() {
		return pageMessagesPrivesRecusTableScroller;
	}

	public void setPageMessagesPrivesRecusTableScroller(
			int pageMessagesPrivesRecusTableScroller) {
		this.pageMessagesPrivesRecusTableScroller = pageMessagesPrivesRecusTableScroller;
	}

	public int getPageMessagesPrivesRechercheTableScroller() {
		return pageMessagesPrivesRechercheTableScroller;
	}

	public void setPageMessagesPrivesRechercheTableScroller(
			int pageMessagesPrivesRechercheTableScroller) {
		this.pageMessagesPrivesRechercheTableScroller = pageMessagesPrivesRechercheTableScroller;
	}

	public void setSupprSuivi(String supprSuivi) {
		this.supprSuivi = supprSuivi;
	}

	public String getSupprSuivi() {
		return supprSuivi;
	}

	public void setDestinatairesPollEnabled(boolean destinatairesPollEnabled) {
		this.destinatairesPollEnabled = destinatairesPollEnabled;
	}

	public boolean isDestinatairesPollEnabled() {
		return destinatairesPollEnabled;
	}
	
	public void setDestinatairesPollEnabledToTrue() {
		this.destinatairesPollEnabled = true;
	}
	
	public void setDestinatairesPollEnabledToFalse() {
		this.destinatairesPollEnabled = false;
	}

}
