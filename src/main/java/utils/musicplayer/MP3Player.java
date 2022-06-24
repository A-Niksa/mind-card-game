package utils.musicplayer;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class MP3Player {
    private final String mp3FileToPlay;
    private javazoom.jl.player.Player jlPlayer;

    public MP3Player(String mp3FileToPlay) {
        this.mp3FileToPlay = mp3FileToPlay;
    }

    public void play() {
        try {
            FileInputStream fileInputStream = new FileInputStream(mp3FileToPlay);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            jlPlayer = new javazoom.jl.player.Player(bufferedInputStream);
        } catch (Exception e) {
            System.out.println("Problem playing mp3 file " + mp3FileToPlay);
            System.out.println(e.getMessage());
        }

        new Thread() {
            public void run() {
                while (true) {
                    try {
                        jlPlayer.play();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                }
            }
        }.start();


    }

    public void close() {
        if (jlPlayer != null) jlPlayer.close();
    }
}