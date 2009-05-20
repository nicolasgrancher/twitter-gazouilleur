package ejb;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(mappedName="MessagePublicBean")
public class MessagePublicBean implements MessagePublicFacade {

        @PersistenceContext(unitName = "gazouilleurJPA")
         protected EntityManager manager;
 }