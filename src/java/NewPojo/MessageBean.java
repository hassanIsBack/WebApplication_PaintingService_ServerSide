/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NewPojo;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Hossain
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/newQueue"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MessageBean implements MessageListener {

    public MessageBean() {
    }

    @Override
    public void onMessage(Message message) {
        SessionBean bean = null;
        TextMessage textMessage = null;
        System.out.println("**** I am in Message Bean ******");
        try {
            if (message instanceof TextMessage) {
                textMessage = (TextMessage) message;
                bean = new SessionBean();
                JSONObject object = new JSONObject(textMessage.getText());
                String name = object.getString("customerName");
                String phone = object.getString("phoneNumber");
                String wallMaterial = object.getString("material");
                int wallArea = Integer.parseInt(object.getString("wallArea"));
                int numberOfCoatings = Integer.parseInt(object.getString("numberOfCoatings"));
                int ceilingArea = Integer.parseInt(object.getString("ceilingArea"));
                bean.calculatePrice(name, phone, wallMaterial, wallArea, numberOfCoatings, ceilingArea);

            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        } catch (JMSException ex) {
            Logger.getLogger(MessageBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
