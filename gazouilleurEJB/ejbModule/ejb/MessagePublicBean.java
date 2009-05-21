package ejb;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import entity.MessagePublic;

@Stateless(mappedName="MessagePublicBean")
public class MessagePublicBean implements MessagePublicFacade {

        @PersistenceContext(unitName = "gazouilleurJPA")
         protected EntityManager manager;
        
        public MessagePublic getById(int id) {
        	// TODO méthode à compléter
        	return null;
        }
        
        public MessagePublic publierMessagePublic() {
        	// TODO méthode à compléter
        	return null;
        }
        
        public MessagePublic supprimerMessagePublic() {
        	// TODO méthode à compléter
        	return null;
        }
 }