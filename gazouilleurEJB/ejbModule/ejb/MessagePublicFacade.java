package ejb;

import java.util.Collection;

import entity.Membre;
import entity.MessagePublic;


@javax.ejb.Remote
public interface MessagePublicFacade {
	
	public MessagePublic getById(int id);
	
	/**
	 * Créer un message public
	 * @return
	 */
	public MessagePublic publierMessagePublic(MessagePublic message);
	
	/**
	 * Supprimer un message public
	 * @return
	 */
	public void supprimerMessagePublic(MessagePublic message);
	
	/**
	 * Récupère tous les messages publics du membre passé en paramètre
	 * @param membre
	 * @return
	 */
	public Collection<MessagePublic> getMessagesPublicsFrom(Membre membre);
	
	public Collection<MessagePublic> getMessagesPublicsFor(Membre membre);
	/**
	 * Recherche message public
	 * @param membres
	 * @return
	 */
	public Collection<MessagePublic> rechercheMessagesPublics(Collection<String> motsCles);

	/**
	 * Publie un message public sur un topic
	 * @param membres
	 * @return
	 */
	//public void publishMessagePublic(MessagePublic msgPublic);

}