// 06/24/2022
// Ali Banayeean Zade & Arsha Niksa
// https://github.com/A-Niksa/mind-card-game

import utils.config.ConfigFetcher;
import utils.config.ConfigIdentifier;
import utils.musicplayer.MP3Player;
import frontend.client.ClientNetwork;
import frontend.gui.firstMenuPage.FirstMenuPage;

import javax.swing.*;

public class Main {
    private static JFrame frame;

    public static void playMusic(String path1, String path2) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    MP3Player mp3Player = new MP3Player(path1);
                    mp3Player.play();
                    try {
                        Thread.sleep((5 * 60 + 21) * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    MP3Player mp3Player2 = new MP3Player(path2);
                    mp3Player2.play();
                    try {
                        Thread.sleep((5 * 60 + 15) * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        String path = ConfigFetcher.fetch(ConfigIdentifier.PRIVATE_NAME_FOR_PATH);
        playMusic(path + "m.mp3", path + "m2.mp3");


        var clientNetwork = new ClientNetwork();
        int playerId = clientNetwork.addNewPlayer();
        new FirstMenuPage(clientNetwork, playerId);

    }
}



