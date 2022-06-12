package frontend.gui;

import com.google.gson.Gson;
import utils.config.DefaultConfig;
import frontend.client.ClientNetwork;
import utils.jsonparsing.literals.dataeggs.gamestate.GameStateEgg;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class GamePage extends JPanel {
    JFrame frame = new JFrame();
    ArrayList<Integer> arrayList = new ArrayList<>();
    ClientNetwork clientNetwork;
    int playerId;
    int gameId;
    int gameLevel;
    int hostId;
    int lastCardInGround;
    boolean isGameStarted;

    public GamePage(int gameId, int playerId) {
        isGameStarted = false;
        lastCardInGround = 5;
        hostId = 0;
        this.gameId = gameId;
        this.playerId = playerId;
        this.clientNetwork = clientNetwork;

        for (int j = 1; j < 10; j++) {
            arrayList.add(j * 9);
        }

        initializeFrame();

//        threadsForRepaint();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        printBack(g);


        if(!isGameStarted){
            if(hostId == playerId){

            }
            else{

            }
        }
        else{

        }

        printCards(arrayList, "Down", g);
        printCards(arrayList, "Right", g);
        printCards(arrayList, "Left", g);
        printCards(arrayList, "Up", g);

        printGameLevel(g);



    }

    public void printBack(Graphics g){
        BufferedImage imageBack = null;
        try {
            File file1 = new File(DefaultConfig.publicNameForPath + "BackMainPage2.png");
            imageBack = ImageIO.read(file1);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        int w = 600;
        int h = 600;
        g.drawImage(imageBack, 0, 0, (int) w, (int) h, null);
    }

    public void printCards(ArrayList<Integer> arrayList, String position, Graphics g){
        int counter;
        double distance = 636.0 * 600 / 1000;

        counter = (int) (distance /  ((int) (arrayList.size() / 2) + 1));

        int num = 0;
        if(position.toLowerCase().equals(("Down").toLowerCase()) | position.toLowerCase().equals(("Right").toLowerCase())){
            num = 0;
        }
        else{
            num = 1;
        }

        double start = 160.0 * 6 / 10;
        int numHelp = 0;

        for (int j = 0; j < arrayList.size(); j++) {
            BufferedImage imageCard = null;
            try {
                if(position.toLowerCase().equals(("Down").toLowerCase())){
                    File file = getNumOfCard(arrayList.get(j));
                    imageCard = ImageIO.read(file);
                }
                else if(position.toLowerCase().equals(("Up").toLowerCase())){
                    File file = new File(DefaultConfig.publicNameForPath + "backCard.png");
                    imageCard = ImageIO.read(file);
                }
                else {
                    File file = new File(DefaultConfig.publicNameForPath + "backCard2.png");
                    imageCard = ImageIO.read(file);
                }

            }
            catch (IOException e) {
                e.printStackTrace();
            }

            if(j == arrayList.size() / 2){
                counter = (int) (distance / (arrayList.size() - (arrayList.size() / 2) + 1));
                numHelp = 0;
                if(position.toLowerCase().equals(("Down").toLowerCase()) | position.toLowerCase().equals(("Right").toLowerCase())){
                    num++;
                }
                else{
                    num--;
                }
            }

            double wCard = 50.0 * 2 / 3;
            double hCard = 60.0 * 2 / 3;

            if(position.toLowerCase().equals(("Down").toLowerCase())){
                g.drawImage(imageCard, (int) (start + counter  + (numHelp) * counter), 500 + 50 * num, (int) wCard, (int) hCard, null);
                g.setFont(new Font(Font.SERIF, Font.BOLD, 20));
                g.setColor(getColorOfNumber(arrayList.get(j)));

                if(arrayList.get(j) == 100){
                    g.drawString(arrayList.get(j) + "", (int) (5.0 * 1 / 3 + start + counter  + (numHelp) * counter), (int) (500 + 50 * num + 40.0 * 2 / 3));
                }
                else if(arrayList.get(j) >= 10){
                    g.drawString(arrayList.get(j) + "", (int) (20.0 * 1 / 3 + start + counter  + (numHelp) * counter), (int) (500 + 50 * num + 40.0 * 2 / 3));
                }
                else{
                    g.drawString(arrayList.get(j) + "", (int) (35.0 * 1 / 3 + start + counter  + (numHelp) * counter), (int) (500 + 50 * num + 40.0 * 2 / 3));
                }
            }
            else if(position.toLowerCase().equals(("Right").toLowerCase())){
                g.drawImage(imageCard, 500 + 50 * num, (int) (start + counter  + (numHelp) * counter), (int) hCard, (int) wCard, null);
            }
            else if(position.toLowerCase().equals(("Left").toLowerCase())){
                g.drawImage(imageCard, 10 + 50 * num, (int) (start + counter  + (numHelp) * counter), (int) hCard, (int) wCard, null);
            }
            else if(position.toLowerCase().equals(("Up").toLowerCase())){
                g.drawImage(imageCard, (int) (start + counter  + (numHelp) * counter), 10 + 50 * num, (int) wCard, (int) hCard, null);
            }
            numHelp++;

        }
    }

    public File getNumOfCard(int num){
        File file;
        if(num <= 10){
            file = new File(DefaultConfig.publicNameForPath + "1-10.png");
        }
        else if(num <= 20){
            file = new File(DefaultConfig.publicNameForPath + "11-20.png");
        }
        else if(num <= 30){
            file = new File(DefaultConfig.publicNameForPath + "21-30.png");
        }
        else if(num <= 40){
            file = new File(DefaultConfig.publicNameForPath + "31-40.png");
        }
        else if(num <= 50){
            file = new File(DefaultConfig.publicNameForPath + "41-50.png");
        }
        else if(num <= 60){
            file = new File(DefaultConfig.publicNameForPath + "51-60.png");
        }
        else if(num <= 70){
            file = new File(DefaultConfig.publicNameForPath + "61-70.png");
        }
        else if(num <= 80){
            file = new File(DefaultConfig.publicNameForPath + "71-80.png");
        }
        else if(num <= 90){
            file = new File(DefaultConfig.publicNameForPath + "81-90.png");
        }
        else {
            file = new File(DefaultConfig.publicNameForPath + "91-100.png");
        }

        return file;
    }

    public Color getColorOfNumber(int num){
        Color color;
        if(num <= 10){
            color = new Color(242, 202, 42);
        }
        else if(num <= 20){
            color = new Color(50, 4, 188);
        }
        else if(num <= 30){
            color = new Color(20, 109, 96);
        }
        else if(num <= 40){
            color = new Color(143, 5, 5);

        }
        else if(num <= 50){
            color = new Color(5, 50, 143);

        }
        else if(num <= 60){
            color = new Color(17, 93, 55);
        }
        else if(num <= 70){
            color = new Color(151, 104, 196);
        }
        else if(num <= 80){
            color = new Color(45, 7, 84);
        }
        else if(num <= 90){
            color = new Color(241, 10, 240);
        }
        else {
            color = new Color(14, 199, 245);

        }

        return color;
    }

    public void printGameLevel(Graphics g)  {
        g.setFont(new Font(Font.SERIF, Font.BOLD, 30));
        g.setColor(Color.BLACK);
        g.drawString("Level: " + gameLevel, (int) 140, (int) 480);
    }

    public void initializeFrame(){
        frame.setTitle("Game");
        frame.setSize(614, 638);
        this.setSize(614, 638);
        frame.setLocation(0,0);
        frame.getContentPane().add(this);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.WHITE);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void threadsForRepaint(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                while(true){
                    //  TODO
                    Gson gson = new Gson();

                    GameStateEgg gameStateEgg = gson.fromJson(clientNetwork.updateGame(gameId, playerId), GameStateEgg.class);
                    isGameStarted = gameStateEgg.isGameHasStarted();
                    repaint();

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    public static void main(String[] args) {

        new GamePage(0, 0);

    }

}