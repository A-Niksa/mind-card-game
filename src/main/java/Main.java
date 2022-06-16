import MusicPlayer.MP3Player;
import frontend.client.ClientNetwork;
import frontend.gui.firstMenuPage.FirstMenuPage;
import utils.config.ConfigFetcher;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javazoom.jl.player.Player;
import utils.config.DefaultConfig;

import java.io.*;

public class Main {
    private static JFrame frame;

    public static void playMusic(String path1, String path2){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
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
        playMusic(DefaultConfig.publicNameForPath + "m.mp3", DefaultConfig.publicNameForPath + "m2.mp3");

        
        var clientNetwork = new ClientNetwork();
        int playerId = clientNetwork.addNewPlayer();
        new FirstMenuPage(clientNetwork, playerId);

    }
}



