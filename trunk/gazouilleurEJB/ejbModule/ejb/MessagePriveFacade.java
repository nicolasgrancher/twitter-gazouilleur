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
	 * Récupère tous les messages privés émis d'un membre
	 * @param membre
	 * @return
	 */
	public Collection<MessagePrive> getMessagesPrivesEmis(Membre membre);
	
	/**
	 * Récupère tous les messages privés reçus d'un membre
	 * @param membre
	 * @return
	 */
	public Collection<MessagePrive> getMessagesPrivesRecus(Membre membre);
	
	/**
	 * Supprime un message privé
	 * @param message
	 */
	public void supprimerMessagePrive(MessagePrive message);
}