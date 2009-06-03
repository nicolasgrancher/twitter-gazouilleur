package MessageDrivenBeans;

import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.ejb.ActivationConfigProperty;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import entity.MessagePublic;

@MessageDriven(mappedName = "topic/MsgPublicTopic", activationConfig = {
	@ActivationConfigProperty(propertyName="destination",propertyValue="topic/MsgPublicTopic"),
	@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
})
//@ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable")
public class MessagePublicMDB implements MessageListener {
	
	@Override
	public void onMessage(Message message) {
        try {
            if (message instanceof ObjectMessage) {
                ObjectMessage msg = (ObjectMessage) message;
                MessagePublic messagePublic = (MessagePublic) msg.getObject();
                sendEMail(messagePublic);
            } else {
                // Créer une exception?
            }
        } catch (JMSException e) {
            throw new EJBException(e);
        } catch (MessagingException e) {
            throw new EJBException(e);
        }
   }
	
	private void sendEMail(MessagePublic messagePublic) throws MessagingException {
		// Envoyer un message à tous les suiveurs de l'emetteur (present dans le header du message reçus)
		
	}
	
}
