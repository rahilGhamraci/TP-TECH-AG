/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projet.tech.ag;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.core.AID;
import jade.core.ContainerID;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuyerAgent extends Agent {

    private double bestScore = Double.MAX_VALUE;
    private String bestSeller = "";

    protected void setup() {
        // Ajouter un comportement pour traiter les réponses des vendeurs
        addBehaviour(new PurchaseRequestBehaviour());
    }

    private class PurchaseRequestBehaviour extends CyclicBehaviour {

        public void action() {
            String initialContainer = null;
            try {
                initialContainer = getContainerController().getContainerName();
            } catch (ControllerException ex) {
                Logger.getLogger(BuyerAgent.class.getName()).log(Level.SEVERE, null, ex);
            }
            // Se déplacer vers le conteneur de l'agent vendeur 1
            //ContainerController container1 = getContainerController();
            ContainerID destination1 = new ContainerID("SellerContainer1", null);

            // Déplacer l'agent vers le conteneur de destination
            doMove(destination1);
            try {
                // Prendre en compte le délai nécessaire pour que l'agent acheteur rejoigne le conteneur de l'agent vendeur 1
                Thread.sleep(1000);
                // Envoyer une demande de proposition d'achat à l'agent vendeur 1
                ACLMessage msg1 = new ACLMessage(ACLMessage.REQUEST);
                msg1.addReceiver(new AID("Seller1", AID.ISLOCALNAME));
                msg1.setContent("Demande de proposition d'achat");
                send(msg1);
                // Attendre la réponse de l'agent vendeur 1
                ACLMessage reply1 = blockingReceive();
                // Traiter la réponse de l'agent vendeur 1
                processReply(reply1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Se déplacer vers le conteneur de l'agent vendeur 2
            //ContainerController container2 = getContainerController();
            ContainerID destination2 = new ContainerID("SellerContainer2", null);

            // Déplacer l'agent vers le conteneur de destination
            doMove(destination2);
            try {
                // Prendre en compte le délai nécessaire pour que l'agent acheteur rejoigne le conteneur de l'agent vendeur 2
                Thread.sleep(1000);
                // Envoyer une demande de proposition d'achat à l'agent vendeur 2
                ACLMessage msg2 = new ACLMessage(ACLMessage.REQUEST);
                msg2.addReceiver(new AID("Seller2", AID.ISLOCALNAME));
                msg2.setContent("Demande de proposition d'achat");
                send(msg2);
                // Attendre la réponse de l'agent vendeur 2
                ACLMessage reply2 = blockingReceive();
                // Traiter la réponse de l'agent vendeur 2
                processReply(reply2);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Une fois que les deux réponses ont été traitées, afficher le meilleur score et l'agent vendeur correspondant
            System.out.println("Le meilleur score est : " + bestScore + " proposé par l'agent vendeur : " + bestSeller);

            // Revenir au conteneur initial
            doDelete();

        }

        // Méthode pour traiter la réponse de l'agent vendeur et calculer le score de l'offre
        private void processReply(ACLMessage reply) {
            if (reply != null && reply.getPerformative() == ACLMessage.PROPOSE) {
                // Analyser les détails de l'offre
                String[] offerDetails = reply.getContent().split(",");
                int price = Integer.parseInt(offerDetails[0].split(":")[1].trim());
                int quality = Integer.parseInt(offerDetails[1].split(":")[1].trim());
                int deliveryCost = Integer.parseInt(offerDetails[2].split(":")[1].trim());
                // Calculer le score de l'offre
                double score = (5 * price) - (quality * 8) + (3 * deliveryCost);
                // Mettre à jour le meilleur score et l'agent vendeur correspondant si nécessaire
                if (score < bestScore) {
                    bestScore = score;
                    bestSeller = reply.getSender().getLocalName();
                }
                // Afficher le score de l'offre
                System.out.println("L'offre est évaluée avec un score de : " + score);

            }
        }
    }
}
