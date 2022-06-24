package frontend.gui.game;

import api.dataeggs.gamestate.Emoji;
import api.dataeggs.gamestate.EmojiEgg;
import backend.logic.models.cards.NumberedCard;
import utils.config.DefaultConfig;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Draw {
    public static void drawEmojis(Graphics g, ArrayList<EmojiEgg> lastStatusOfPlayers){
        for (int i = 0; i < lastStatusOfPlayers.size(); i++) {
            if(lastStatusOfPlayers.get(i).getPlayerId() == -1){
                continue;
            }
            else if(lastStatusOfPlayers.get(i).getEmoji() == Emoji.NOTHING){
                continue;
            }

            BufferedImage imageCard = null;
            try {
                File file = new File(DefaultConfig.publicNameForPath + lastStatusOfPlayers.get(i).getEmoji() + ".png");
                imageCard = ImageIO.read(file);
            }

            catch (IOException e) {
                e.printStackTrace();
            }

            double start = 160.0 * 6 / 10;

            double wCard = 140.0 * 6 / 10;
            double hCard = 121.0 * 6 / 10;
            if(i == 0){
                g.drawImage(imageCard, (int) (840.0 * 6 / 10), (int) (850.0 * 6 / 10), (int) wCard, (int) hCard, null);
            }
            else if(i == 1){
                g.drawImage(imageCard, (int) (21.0 * 6 / 10), (int) (850.0 * 6 / 10), (int) wCard, (int) hCard, null);
            }
            else if(i == 2){
                g.drawImage(imageCard, (int) (21.0 * 6 / 10), (int) (32.0 * 6 / 10), (int) wCard, (int) hCard, null);
            }
            else if(i == 3){
                g.drawImage(imageCard, (int) (840.0 * 6 / 10), (int) (32.0 * 6 / 10), (int) wCard, (int) hCard, null);
            }

        }
    }

    public static void drawShuriken(Graphics g, int numberOfShurikens , ArrayList<String> shurikensStatus){
        for (int i = 0; i < numberOfShurikens; i++) {
            BufferedImage imageCard = null;
            try {
                File file = new File(DefaultConfig.publicNameForPath + shurikensStatus.get(i));
                imageCard = ImageIO.read(file);
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            double start = 160.0 * 6 / 10;

            double wCard = 117.0 * 1 / 2;
            double hCard = 100.0 * 1 / 2;

            g.drawImage(imageCard, (int) (start + 640 * 6.0 / 10 - wCard) - i * 60, 440, (int) wCard, (int) hCard, null);
        }
    }

    public static void drawHearts(Graphics g, int heart, int numberOfAllPlayers){
        for (int i = 0; i < heart; i++) {
            BufferedImage imageCard = null;
            try {
                File file = new File(DefaultConfig.publicNameForPath + "Heart.png");
                imageCard = ImageIO.read(file);
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            double start = 160.0 * 6 / 10;

            double wCard = 60.0 * 6 / 10;
            double hCard = 50.0 * 6 / 10;

            g.drawImage(imageCard, (int) (start) + 40 + i * 40, 130, (int) wCard, (int) hCard, null);

        }

        for (int i = heart; i < numberOfAllPlayers; i++) {
            BufferedImage imageCard = null;
            try {
                File file = new File(DefaultConfig.publicNameForPath + "BrokenHeart.png");
                imageCard = ImageIO.read(file);
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            double start = 160.0 * 6 / 10;

            double wCard = 60.0 * 6 / 10;
            double hCard = 50.0 * 6 / 10;

            g.drawImage(imageCard, (int) (start) + 40 + i * 40, 130, (int) wCard, (int) hCard, null);

        }
    }

    public static void drawLastCardInGround(Graphics g, int lastCardInGround){
        if(!(lastCardInGround >= 1 & lastCardInGround <= 100)){
            return;
        }

        BufferedImage imageCard = null;
        try {
            File file = getFileOfNumber(lastCardInGround);
            imageCard = ImageIO.read(file);

        }
        catch (IOException e) {
            e.printStackTrace();
        }

        double start = 160.0 * 6 / 10;

        double wCard = 50.0 * 9 / 10;
        double hCard = 60.0 * 9 / 10;

        g.drawImage(imageCard, (int) (start) + 175, 275, (int) wCard, (int) hCard, null);

        g.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        g.setColor(getColorOfNumber(lastCardInGround));

        if(lastCardInGround == 100){
            g.drawString(lastCardInGround + "", (int) (5.0 * 1 / 3 + start + 180), (int) (310));
        }
        else if(lastCardInGround >= 10){
            g.drawString(lastCardInGround + "", (int) (20.0 * 1 / 3 + start + 180), (int) (310));
        }
        else{
            g.drawString(lastCardInGround + "", (int) (35.0 * 1 / 3 + start + 180), (int) (310));
        }


    }

    public static void drawBack(Graphics g, int heart){
        if(heart == 0){
            BufferedImage imageBack = null;
            try {
                File file1 = new File(DefaultConfig.publicNameForPath + "LostPage.png");
                imageBack = ImageIO.read(file1);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            int w = 600;
            int h = 720;
            g.drawImage(imageBack, 0, 0, (int) w, (int) h, null);

            return;
        }
        BufferedImage imageBack = null;
        try {
            File file1 = new File(DefaultConfig.publicNameForPath + "BackMainPage2.png");
            imageBack = ImageIO.read(file1);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        int w = 600;
        int h = 720;
        g.drawImage(imageBack, 0, 0, (int) w, (int) h, null);
    }

    public static void drawCardsForDown(ArrayList<NumberedCard> cardsForPlayer, Graphics g){
        if(cardsForPlayer == null){
            return;
        }
        else if(cardsForPlayer.size() == 0){
            return;
        }
        int counter;
        double distance = 636.0 * 600 / 1000;


        counter = (int) (distance /  ((int) (cardsForPlayer.size() / 2) + 1));

        int num = 0;

        double start = 160.0 * 6 / 10;
        int numHelp = 0;

        for (int j = 0; j < cardsForPlayer.size(); j++) {
            BufferedImage imageCard = null;
            try {
                File file = getFileOfNumber(cardsForPlayer.get(j).getCardNumber());
                imageCard = ImageIO.read(file);

            }
            catch (IOException e) {
                e.printStackTrace();
            }

            if(j == cardsForPlayer.size() / 2){
                counter = (int) (distance / (cardsForPlayer.size() - (cardsForPlayer.size() / 2) + 1));
                numHelp = 0;
                if(cardsForPlayer.size() != 1){
                    num++;
                }
            }

            double wCard = 50.0 * 2 / 3;
            double hCard = 60.0 * 2 / 3;

            g.drawImage(imageCard, (int) (start + counter  + (numHelp) * counter), 500 + 50 * num, (int) wCard, (int) hCard, null);
            g.setFont(new Font(Font.SERIF, Font.BOLD, 20));
            g.setColor(getColorOfNumber(cardsForPlayer.get(j).getCardNumber()));

            if(cardsForPlayer.get(j).getCardNumber() == 100){
                g.drawString(cardsForPlayer.get(j).getCardNumber() + "", (int) (5.0 * 1 / 3 + start + counter  + (numHelp) * counter), (int) (500 + 50 * num + 40.0 * 2 / 3));
            }
            else if(cardsForPlayer.get(j).getCardNumber() >= 10){
                g.drawString(cardsForPlayer.get(j).getCardNumber() + "", (int) (20.0 * 1 / 3 + start + counter  + (numHelp) * counter), (int) (500 + 50 * num + 40.0 * 2 / 3));
            }
            else{
                g.drawString(cardsForPlayer.get(j).getCardNumber() + "", (int) (35.0 * 1 / 3 + start + counter  + (numHelp) * counter), (int) (500 + 50 * num + 40.0 * 2 / 3));
            }

            numHelp++;

        }
    }

    public static void drawCardsOtherPlayer(ArrayList<Integer> numberOfCardForOtherPlayers, int index, Graphics g){
        int counter;
        double distance = 636.0 * 600 / 1000;

        counter = (int) (distance /  ((int) (numberOfCardForOtherPlayers.get(index) / 2) + 1));

        int num = 0;
        if(index == 2){
            num = 0;
        }
        else{
            num = 1;
        }

        double start = 160.0 * 6 / 10;
        int numHelp = 0;

        for (int j = 0; j < numberOfCardForOtherPlayers.get(index); j++) {
            BufferedImage imageCard = null;
            try {
                if(index == 1){
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

            if(j == numberOfCardForOtherPlayers.get(index) / 2){
                counter = (int) (distance / (numberOfCardForOtherPlayers.get(index) - (numberOfCardForOtherPlayers.get(index) / 2) + 1));
                numHelp = 0;
                if(index == 2){
                    if(numberOfCardForOtherPlayers.get(index) != 1){
                        num++;
                    }
                }
                else{
                    if(numberOfCardForOtherPlayers.get(index) != 1){
                        num--;
                    }
                }
            }

            double wCard = 50.0 * 2 / 3;
            double hCard = 60.0 * 2 / 3;

            if(index == 2){
                g.drawImage(imageCard, 500 + 50 * num, (int) (start + counter  + (numHelp) * counter), (int) hCard, (int) wCard, null);
            }
            else if(index == 0){
                g.drawImage(imageCard, 10 + 50 * num, (int) (start + counter  + (numHelp) * counter), (int) hCard, (int) wCard, null);
            }
            else if(index == 1){
                g.drawImage(imageCard, (int) (start + counter  + (numHelp) * counter), 10 + 50 * num, (int) wCard, (int) hCard, null);
            }
            numHelp++;
        }
    }

    public static File getFileOfNumber(int num){
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

    public static Color getColorOfNumber(int num){
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

    public static void drawGameLevel(Graphics g, int gameLevel)  {
        g.setFont(new Font(Font.SERIF, Font.BOLD, 30));
        g.setColor(Color.BLACK);
        g.drawString("Level: " + gameLevel, (int) 140, (int) 480);
    }
}
