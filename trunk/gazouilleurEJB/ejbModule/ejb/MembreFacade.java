package ejb;


import java.util.Collection;

import entity.Membre;
import exception.MembreException;


@javax.ejb.Remote
public interface MembreFacade {
	
	/**
	 * Chercher un membre par id
	 * @param id
	 * @return
	 */
	public Membre getById(int id);
	
	/**
	 * Chercher un membre par pseudo
	 * @param pseudo
	 * @return
	 * @throws MembreException
	 */
	public Membre getByPseudo(String pseudo) throws MembreException;
	
	/**
	 * Chercher un membre par email
	 * @param email
	 * @return
	 * @throws MembreException
	 */
	public Membre getByEmail(String email) throws MembreException;
	
	/**
	 * S'inscrire
	 * @param membre
	 * @return
	 * @throws MembreException
	 */
	public Membre creerMembre (Membre membre) throws MembreException;
	
	/**
	 * Supprimer un membre
	 * @param membre
	 */
	public void supprimerMembre (Membre membre);
	
	/**
	 * Se connecter
	 * @param pseudo
	 * @param password
	 * @return
	 */
	public Membre connexionMembre (String pseudo, String password);
	
	/**
	 * Se d�connecter
	 * @param membre
	 * @return
	 */
	 public Membre deconnexionMembre (Membre membre);
	
	/**
	 * Modifier Profil
	 * @param membre
	 * @return
	 * @throws MembreException
	 */
	public Membre updateMembre (Membre membre) throws MembreException;
	
	/**
	 * Suivre un ami
	 * @param membre
	 * @param ami
	 */
	public Membre ajouterAmi (Membre membre, Membre ami);
	
	/**
	 * Ne plus suivre un ami
	 * @param membre
	 * @param ami
	 */
	public Membre supprimerAmi (Membre membre, Membre ami);
	
	/**
	 * Afficher ceux que je suis
	 * @param membre
	 * @return
	 */
	public Collection <Membre> getSuivi(Membre membre);
	
	/**
	 * Afficher ceux qui me suivent
	 * @param membre
	 * @return
	 */
	public Collection <Membre> getSuiveur(Membre membre);
	
	/**
	 * Rechercher ami par pseudo
	 * @param pseudo
	 * @return
	 */
	public Collection <Membre> rechercheByPseudo(String pseudo);
	
	/**
	 * Rechercher ami par email
	 * @param pseudo
	 * @return
	 */
	public Collection <Membre> rechercheByEmail(String email);
	
	/**
	 * Recherche tous les inscrits
	 */
	public Collection<Membre> rechercheTous();

}