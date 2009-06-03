package ejb;


import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
		Query q = entityMgr.createNamedQuery("findByPseudoExact");
		try{
			Membre membre = (Membre)q.setParameter(1, pseudo).getSingleResult();
			return membre;
		} catch(NoResultException e){
			throw new MembreException("Erreur GetByPseudo : Membre avec le pseudo "+pseudo+" n'existe pas");
		}
	}

	public Membre getByEmail(String email) throws MembreException {
		Query q = entityMgr.createNamedQuery("findByEmail");
		try{
			Membre membre = (Membre)q.setParameter(1, email).getSingleResult();
			return membre;
		} catch(NoResultException e){
			throw new MembreException("Erreur GetByEmail : Membre avec l'email "+email+" n'existe pas");
		}
	}

	/**
	 * S'inscrire
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
			Query q = entityMgr.createNamedQuery("findByPseudoAndPassword");
			Membre membre = (Membre) q.setParameter(1, pseudo).setParameter(2, password).getSingleResult();
			membre.getListSuivis();
			membre.getListSuiveurs();
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
		//Vérification des données
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
	public Membre ajouterAmi (Membre membre, Membre ami) {
		if (membre != null && ami != null) {
			membre = getEntityMgr().merge(membre);
			ami = getEntityMgr().merge(ami);
			
			membre.ajouterSuivi(ami);
			
			membre = getEntityMgr().merge(membre);
			ami = getEntityMgr().merge(ami);
			
			return membre;
		}
		return null;
	}

	/**
	 * Ne plus suivre un ami
	 */
	public Membre supprimerAmi (Membre membre, Membre ami) {
		if (membre != null && ami != null) {
			membre = getEntityMgr().merge(membre);
			ami = getEntityMgr().merge(ami);
			
			membre.supprimerSuivi(ami);
			
			membre = getEntityMgr().merge(membre);
			ami = getEntityMgr().merge(ami);
			
			return membre;
		}	
		return null;
	}

	/**
	 * Afficher ceux que je suis
	 */
	public Collection <Membre> getSuivi(Membre membre) {
		membre = getEntityMgr().merge(membre);
		Collection<Membre> collection = membre.getListSuivis();
		collection.size(); // chargement de la collection persistée
		return collection;
	}
	/**
	 * Afficher ceux qui me suivent
	 */
	public Collection <Membre> getSuiveur(Membre membre) {
		membre = getEntityMgr().merge(membre);
		Collection<Membre> collection = membre.getListSuivis();
		collection.size(); // chargement de la collection persistée
		return collection;
	}

	/**
	 * Rechercher ami par pseudo
	 */
	@SuppressWarnings("unchecked")
	public Collection <Membre> rechercheByPseudo(String pseudo) {
		try {
			Query q = entityMgr.createNamedQuery("findByPseudo");
			ArrayList<Membre> membres= (ArrayList<Membre>) q.setParameter(1, pseudo).getResultList();
			return membres;
		} catch(NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Rechercher ami par email
	 */
	@SuppressWarnings("unchecked")
	public Collection <Membre> rechercheByEmail(String email){
		try {
			Query q = entityMgr.createNamedQuery("findByEmail");
			ArrayList<Membre> membres= (ArrayList<Membre>) q.setParameter(1, email).getResultList();
			return membres;
		} catch(NoResultException e) {
			return null;
		}
	}
	
	/**
	 * Lister tous les membres inscrits
	 */
	@SuppressWarnings("unchecked")
	public Collection<Membre> rechercheTous() {
		try {
			Query q = entityMgr.createNamedQuery("findAll");
			ArrayList<Membre> membres = (ArrayList<Membre>) q.getResultList();
			Collections.sort(membres);
			return membres;
		} catch (NoResultException e) {
			return null;
		}
	}
}