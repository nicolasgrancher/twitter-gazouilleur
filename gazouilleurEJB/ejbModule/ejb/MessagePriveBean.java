package ejb;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(mappedName="MessagePriveBean")
public class MessagePriveBean implements MessagePriveFacade {

        @PersistenceContext(unitName = "gazouilleurJPA")
         protected EntityManager manager;
 }