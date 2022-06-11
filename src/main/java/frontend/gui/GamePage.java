package frontend.gui;

import config.ConfigClass;
import frontend.client.ClientNetwork;

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

    public GamePage(ClientNetwork clientNetwork, int gameId, int playerId) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.clientNetwork = clientNetwork;

        for (int j = 1; j < 12; j++) {
            arrayList.add(j * 9);
        }

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

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        printBack(g);

        printCards(arrayList, "Down", g);
        printCards(arrayList, "Right", g);
        printCards(arrayList, "Left", g);
        printCards(arrayList, "Up", g);

    }

    public void printBack(Graphics g){
        BufferedImage imageBack = null;
        try {
            File file1 = new File(ConfigClass.publicNameForPath + "BackMainPage2.png");
            imageBack = ImageIO.read(file1);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        int w = 600;
        int h = 600;
        g.drawImage(imageBack, 0, 0, (int) w, (int) h, null);
    }

    public File getNumOfCard(int num){
        File file;
        if(num <= 10){
            file = new File(ConfigClass.publicNameForPath + "1-10.png");
        }
        else if(num <= 20){
            file = new File(ConfigClass.publicNameForPath + "11-20.png");
        }
        else if(num <= 30){
            file = new File(ConfigClass.publicNameForPath + "21-30.png");
        }
        else if(num <= 40){
            file = new File(ConfigClass.publicNameForPath + "31-40.png");
        }
        else if(num <= 50){
            file = new File(ConfigClass.publicNameForPath + "41-50.png");
        }
        else if(num <= 60){
            file = new File(ConfigClass.publicNameForPath + "51-60.png");
        }
        else if(num <= 70){
            file = new File(ConfigClass.publicNameForPath + "61-70.png");
        }
        else if(num <= 80){
            file = new File(ConfigClass.publicNameForPath + "71-80.png");
        }
        else if(num <= 90){
            file = new File(ConfigClass.publicNameForPath + "81-90.png");
        }
        else {
            file = new File(ConfigClass.publicNameForPath + "91-100.png");
        }

        return file;
    }

    public void printCards(ArrayList<Integer> arrayList, String position, Graphics g){
        int counter;
        double distance = 636.0 * 600 / 1000;

        if(arrayList.size() > 6){
            counter = (int) (distance / 7);
        }
        else{
            counter = (int) (distance / (arrayList.size() + 1));
        }

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
                    File file = new File(ConfigClass.publicNameForPath + "backCard.png");
                    imageCard = ImageIO.read(file);
                }
                else {
                    File file = new File(ConfigClass.publicNameForPath + "backCard2.png");
                    imageCard = ImageIO.read(file);
                }


            }
            catch (IOException e) {
                e.printStackTrace();
            }

            if(j == 6){
                counter = (int) (distance / (arrayList.size() - 6 + 1));
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

}