package ejb;


import java.util.*;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import entity.Membre;
import exception.MembreException;


@Stateless(mappedName="MembreBean")
public class MembreBean implements MembreFacade {

	@PersistenceContext(unitName = "gazouilleurJPA")
	protected EntityManager entityMgr;

	public MembreBean () {}

	public EntityManager getEntityMgr() {
		return entityMgr;
	}

	public void setEntityMgr(EntityManager entityMgr) {
		this.entityMgr = entityMgr;
	}

	/**
	 * Récupération de membre par ID, Pseudo et Email.
	 */
	public Membre getById(int id) {
		return (Membre) entityMgr.find(Membre.class, id);
	}

	public Membre getByPseudo(String pseudo) throws MembreException {
		Query q = entityMgr.createQuery("SELECT m FROM Membre as m WHERE m.pseudo = ?1");
		try{
			Membre membre = (Membre)q.setParameter(1, pseudo).getSingleResult();
			return membre;
		} catch(NoResultException e){
			throw new MembreException("Erreur GetByPseudo : Membre avec le pseudo "+pseudo+" n'existe pas");
		}
	}

	public Membre getByEmail(String email) throws MembreException {
		Query q = entityMgr.createQuery("SELECT m FROM Membre as m WHERE m.email = ?1");
		try{
			Membre membre = (Membre)q.setParameter(1, email).getSingleResult();
			return membre;
		} catch(NoResultException e){
			throw new MembreException("Erreur GetByEmail : Membre avec l'email "+email+" n'existe pas");
		}
	}

	/**
	 * S’inscrire
	 */
	public Membre creerMembre (Membre membre) throws MembreException { 
		try{
			entityMgr.persist(membre);
		}
		catch(Exception e){
			e.printStackTrace();
			throw new MembreException("Pseudo déja utilisé");
		}
		return membre;
	}
	
	public void supprimerMembre (Membre membre) {
		membre = entityMgr.merge(membre);
		entityMgr.remove(membre);
	}

	/**
	 * Se connecter
	 */
	public Membre connexionMembre (String pseudo, String password) {
		try {
			Query q = entityMgr.createQuery("Select m FROM Membre m WHERE m.pseudo = ?1 AND m.password = ?2");
			Membre membre = (Membre) q.setParameter(1, pseudo).setParameter(2, password).getSingleResult();
			return membre;
		} catch(NoResultException e) {
			return null;
		}
	}

	/**
	 * Se déconnecter
	 */
	public Membre deconnexionMembre (Membre membre) {return null;}

	/**
	 * Modifier Profil
	 */
	public Membre updateMembre (Membre membre) throws MembreException {
		Membre membreupdate = (Membre) this.getById(membre.getId());
		String pseudo = membre.getPseudo();
		String password = membre.getPassword();
		String email = membre.getEmail();
		//Vérification des donnees
		if(membre !=null){
			membreupdate.setNom(membre.getNom());
			membreupdate.setPrenom(membre.getPrenom());

			if(pseudo == null || pseudo.equals(""))
				throw new MembreException("Erreur Update : Pseudo manquant");
			membreupdate.setPseudo(membre.getPseudo());

			if(password == null || password.equals(""))
				throw new MembreException("Erreur Update : Mot de passe manquant");
			membreupdate.setPassword(password);

			if(email == null || email.equals(""))
				throw new MembreException("Erreur Update : Email manquant");
			membreupdate.setEmail(membre.getEmail());
			
			//Update en base
			try {
				membreupdate = entityMgr.merge(membreupdate);
				return membreupdate;
			} catch (Exception e) {
				throw new MembreException("Erreur Update : Email existe deja");
			}

		} else {
			throw new MembreException("Erreur Update : Membre n'existe pas");
		}
	}

	/**
	 * Suivre un ami
	 */
	public void ajouterAmi (Membre membre, Membre ami) {
		if (membre != null && ami != null) {
			membre = getEntityMgr().merge(membre);
			ami = getEntityMgr().merge(ami);
			
			membre.ajouterSuivi(ami);
			ami.ajouterSuivi(membre);
		}
	}

	/**
	 * Ne plus suivre un ami
	 */
	public void supprimerAmi (Membre membre, Membre ami) {
		if (membre != null && ami != null) {
			Membre membre1 = getEntityMgr().merge(membre);
			Membre suivi = getEntityMgr().merge(ami);
			membre1.supprimerSuivi(suivi);
		}	
	}

	/**
	 * Afficher ceux que je suis
	 */
	public Collection <Membre> getSuivi(Membre membre) {
		membre = getEntityMgr().merge(membre);
		Collection<Membre> collection = membre.getListSuivis();
		return collection;
	}
	/**
	 * Afficher ceux qui me suivent
	 */
	public Collection <Membre> getSuiveur(Membre membre) {
		membre = getEntityMgr().merge(membre);
		Collection<Membre> collection = membre.getListSuivis();
		return collection;
	}

	/**
	 * Rechercher ami
	 */
	@SuppressWarnings("unchecked")
	public Collection <Membre> rechercheByPseudo(String pseudo) {
		try {
			Query q = entityMgr.createQuery("SELECT m FROM Membre as m WHERE m.pseudo LIKE CONCAT('%',?0,'%')");
			ArrayList<Membre> membres= (ArrayList<Membre>) q.setParameter(0, pseudo).getResultList();
			return membres;
		} catch(NoResultException e) {
			return null;
		}
	}
}