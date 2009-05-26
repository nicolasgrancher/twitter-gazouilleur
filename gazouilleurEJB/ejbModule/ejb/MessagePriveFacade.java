package ejb;

import java.util.Collection;

import entity.Membre;
import entity.MessagePrive;



@javax.ejb.Remote
public interface MessagePriveFacade {
	
	public MessagePrive getById(int id);
	
	/**
	 * Envoi un message privé
	 * @param message
	 * @return
	 */
	public MessagePrive envoyerMessagePrive(MessagePrive message);
	
	/**
	 * R�cup�re tous les messages privés d'un membre
	 * @param membre
	 * @return
	 */
	public Collection<MessagePrive> getMessagesPrives(Membre membre);
	
	/**
	 * Récupère tous les messages privés non lus d'un membre
	 * @param membre
	 * @return
	 */
	public Collection<MessagePrive> getMessagesPrivesNonLus(Membre membre);
	
	/**
	 * Récupère tous les messages privés lus d'un membre
	 * @param membre
	 * @return
	 */
	public Collection<MessagePrive> getMessagesPrivesLus(Membre membre);
	
	/**
	 * Supprime un message privé
	 * @param message
	 * @return
	 */
	public MessagePrive supprimerMessagePrive(MessagePrive message);
}