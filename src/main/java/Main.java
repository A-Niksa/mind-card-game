import frontend.client.ClientNetwork;
import frontend.gui.firstMenuPage.FirstMenuPage;
import utils.config.ConfigFetcher;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Main {
    private static JFrame frame;


    public static void main(String[] args) {

        var clientNetwork = new ClientNetwork();
        int playerId = clientNetwork.addNewPlayer();

        new FirstMenuPage(clientNetwork, playerId);


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
