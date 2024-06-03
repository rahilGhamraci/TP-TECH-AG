/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package projet.tech.ag;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

/**
 *
 * @author Dell
 */
public class ProjetTechAg {

    /**
     * @param args the command line arguments
     */
   public static void main(String[] args) {

    try {
        jade.core.Runtime rt = jade.core.Runtime.instance();
        
        // Buyer container
        ProfileImpl p = new ProfileImpl();
        p.setParameter(Profile.GUI, "true");
        p.setParameter(Profile.LOCAL_HOST, "localhost");
        p.setParameter(Profile.LOCAL_PORT, "1099");
        p.setParameter(Profile.CONTAINER_NAME, "MainContainer"); // Nom de la plateforme principale

        ContainerController mc = rt.createMainContainer(p);
        
        // Seller 1 container
        ProfileImpl p1 = new ProfileImpl();
        p1.setParameter(Profile.GUI, "true");
        p1.setParameter(Profile.LOCAL_HOST, "localhost");
        p1.setParameter(Profile.LOCAL_PORT, "1099");
        p1.setParameter(Profile.CONTAINER_NAME, "SellerContainer1"); // Nom du contenair pour Seller1

        ContainerController mc1 = rt.createAgentContainer(p1);
        
        // Seller 2 container
        ProfileImpl p2 = new ProfileImpl();
        p2.setParameter(Profile.GUI, "true");
        p2.setParameter(Profile.LOCAL_HOST, "localhost");
        p2.setParameter(Profile.LOCAL_PORT, "1099");
        p2.setParameter(Profile.CONTAINER_NAME, "SellerContainer2"); // Nom du contenair pour Seller2

        ContainerController mc2 = rt.createAgentContainer(p2);
        
        // Créer et démarrer les agents
        AgentController Agt1 = mc.createNewAgent("Buyer", "projet.tech.ag.BuyerAgent", null);
        AgentController Agt2 = mc1.createNewAgent("Seller1", "projet.tech.ag.SellerAgent", null);
        AgentController Agt3 = mc2.createNewAgent("Seller2", "projet.tech.ag.SellerAgent", null);

        Agt1.start();
        Agt2.start();
        Agt3.start();
    } catch (Exception e) {
        System.out.println(e);
    }
}
}
