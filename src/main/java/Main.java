import frontend.client.ClientNetwork;
import frontend.gui.firstMenuPage.FirstMenuPage;

import javax.swing.*;
import java.awt.*;

public class Main {
    private static JFrame frame;


    public static void main(String[] args) {
        new FirstMenuPage(0);

        var clientNetwork = new ClientNetwork();
        int playerId = clientNetwork.addNewPlayer();
//        new FirstMenuPage(playerId);


        while (true){
            System.out.println("id: " + playerId);
            System.out.println("Test connection: " + clientNetwork.testConnection());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
