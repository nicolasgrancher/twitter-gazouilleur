package ejb;

import entity.MessagePublic;


@javax.ejb.Remote
public interface MessagePublicFacade {
	
	public MessagePublic getById(int id);
	
	/**
	 * Cr�er un message public
	 * @return
	 */
	public MessagePublic publierMessagePublic();
	
	/**
	 * Supprimer un message public
	 * @return
	 */
	public MessagePublic supprimerMessagePublic();
}