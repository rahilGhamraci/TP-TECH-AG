/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projet.tech.ag;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.Random;

/**
 *
 * @author Dell
 */
public class SellerAgent extends Agent{
       
    protected void setup() {
        addBehaviour(new OfferRequestsServer());
    }

    private class OfferRequestsServer extends CyclicBehaviour {
        public void action() {
            ACLMessage msg = myAgent.receive();
            if (msg != null) {
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.PROPOSE);
                 int price = generateRandomValue(5000, 10000);
                 int quality = generateRandomValue(1, 5);
                 int deliveryCost = generateRandomValue(5, 15);
               
                 String content = "Prix: " + price + ", Qualité: " + quality + ", Frais de livraison: " + deliveryCost;
                 System.out.println(content+" seller:"+ getLocalName());
                reply.setContent(content);
                myAgent.send(reply);
            } else {
                block();
            }
        }
           private int generateRandomValue(int min, int max) {
            Random random = new Random();
            return random.nextInt(max - min + 1) + min;
        }
    }
    
}
