package frontend.gui;

import backend.logic.games.components.Hand;
import backend.logic.models.cards.NumberedCard;
import frontend.gui.firstMenuPage.FirstMenuPage;
import utils.config.DefaultConfig;
import frontend.client.ClientNetwork;
import utils.jsonparsing.JsonParser;
import utils.jsonparsing.literals.dataeggs.DataEggType;
import utils.jsonparsing.literals.dataeggs.MakingMoveEgg;
import utils.jsonparsing.literals.dataeggs.gamestate.Emoji;
import utils.jsonparsing.literals.dataeggs.gamestate.EmojiEgg;
import utils.jsonparsing.literals.dataeggs.gamestate.GameStateEgg;
import utils.jsonparsing.literals.dataeggs.gamestate.HandEgg;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static utils.jsonparsing.literals.dataeggs.DataEggType.GAME_STATE_EGG;


public class GamePage extends JPanel {
    JFrame frame = new JFrame();
    ClientNetwork clientNetwork;
    int playerId;
    int gameId;
    int gameLevel;
    int hostId;
    int lastCardInGround;
    boolean isGameStarted;
    boolean isOnGameStartButton;
    int heart;
    int numberOfOtherPlayer;
    ArrayList<Integer> numberOfCardForOtherPlayers;
    ArrayList<NumberedCard> cardsForPlayer;
    int numberOfAllPlayers;
    boolean hasThereAnyNinjaRequest;

    boolean isCauseLooseOfHeartBecauseOfOtherPlayer;
    int numCardCauseLooseOfHeartBecauseOfOtherPlayer;
    int numberOfShurikens;
    ArrayList<String> shurikensStatus;
    ArrayList<EmojiEgg> lastStatusOfPlayers;


    public GamePage(ClientNetwork clientNetwork, int gameId, int playerId) {
        hasThereAnyNinjaRequest = false;
        isCauseLooseOfHeartBecauseOfOtherPlayer = false;
        numberOfShurikens = 0;
        heart = 1;
        shurikensStatus = new ArrayList<>();
        lastStatusOfPlayers = new ArrayList<>();

        for (int i = 0; i < numberOfShurikens; i++) {
            shurikensStatus.add("shuriken.png");

        }

        numberOfCardForOtherPlayers = new ArrayList<>();
        cardsForPlayer = new ArrayList<>();

        this.gameId = gameId;
        this.playerId = playerId;
        isGameStarted = false;
        isOnGameStartButton = false;

        this.clientNetwork = clientNetwork;

        threadsForRepaint();

        initializeFrame();

        addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {

            }

            public void mousePressed(MouseEvent e){
                int x = e.getX();
                int y = e.getY();

                if(!isGameStarted){
                    if(hostId == playerId){
                        if(isOnGameStartButtonBeforeGameStared(x, y)){
                            isGameStarted = clientNetwork.makeGameUnjoinable(gameId);
                            if(isGameStarted){
                                JOptionPane.showMessageDialog(null, "Game started", "Game started" , JOptionPane.INFORMATION_MESSAGE);
                            }

                            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

                        }

                        repaint();
                    }
                    else{
                        // Nothing to do
                    }
                }
                else{

                    double start = 160.0 * 6 / 10;
                    double distance = 636.0 * 600 / 1000;
                    double wCard = 50.0 * 2 / 3;
                    double hCard = 60.0 * 2 / 3;



                    int counter = (int) (distance /  ((int) (cardsForPlayer.size() / 2) + 1));

                    if(heart == 0){

                        if(x >= 235 & x <= 370 & y >= 335 & y <= 393){
                            new FirstMenuPage(clientNetwork, playerId);
                            frame.dispose();
                        }

                        return;

                    }

                    else if(cardsForPlayer == null){
                        return;
                    }
                    else if((x - 145) * (x - 145) + (y - 665) + (y - 665) <= 45 * 45){
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                    else if((x - 245) * (x - 245) + (y - 665) + (y - 665) <= 45 * 45){
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                    else if((x - 350) * (x - 350) + (y - 665) + (y - 665) <= 45 * 45){
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                    else if((x - 445) * (x - 445) + (y - 665) + (y - 665) <= 45 * 45){
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                    
                    else if(cardsForPlayer.size() == 0){
                        return;
                    }
                    if(cardsForPlayer.size() == 1){
                        counter = (int) (distance / (2));
                    }

                    if(x >= (start + counter) & x <= (start + counter) + wCard & y >= 500 & y <= 500 + hCard){

                        String s = clientNetwork.makeMove(gameId, playerId, 0);
                        MakingMoveEgg makingMoveEgg = (MakingMoveEgg) JsonParser.parseToDataEgg(s, DataEggType.MAKING_MOVE_EGG);
                        if(!makingMoveEgg.moveWasValid()){
                            JOptionPane.showMessageDialog(null, "Move wasn't valid", "Error!!" , JOptionPane.ERROR_MESSAGE);
                        }
                        else if(makingMoveEgg.moveCausesLossOfHealth()){
                            System.out.println(makingMoveEgg.getNumberOfSmallestCardThatHasCausedLoss());
                            JOptionPane.showMessageDialog(null, "Cause lost of heart because card of " + makingMoveEgg.getNumberOfSmallestCardThatHasCausedLoss() , "Lose heart" , JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }

            }

            public void mouseReleased(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {

            }
        });

        addMouseMotionListener(new MouseAdapter() {



            public void mouseMoved(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                if(!isGameStarted){
                    if(hostId == playerId){
                        if(isOnGameStartButtonBeforeGameStared(x, y)){
                            isOnGameStartButton = true;
                            setCursor(new Cursor(Cursor.HAND_CURSOR));
                        }
                        else{
                            isOnGameStartButton = false;
                            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }


                        repaint();
                    }
                }
                else{

                    double start = 160.0 * 6 / 10;
                    double distance = 636.0 * 600 / 1000;
                    double wCard = 50.0 * 2 / 3;
                    double hCard = 60.0 * 2 / 3;

                    int counter = (int) (distance /  ((int) (cardsForPlayer.size() / 2) + 1));


                    shurikensStatus.clear();
                    for (int i = 0; i < numberOfShurikens; i++) {
                        shurikensStatus.add("shuriken.png");

                    }

//                    System.out.println(x + " " + (start + counter));


                    if(heart == 0){
                        if(x >= 235 & x <= 370 & y >= 335 & y <= 393){
                            setCursor(new Cursor(Cursor.HAND_CURSOR));
                        }
                        else{
                            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }

                        return;
                    }
                    else if(cardsForPlayer == null){
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        return;
                    }
                    else if(cardsForPlayer.size() == 0){
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        return;
                    }
                    else if(cardsForPlayer.size() == 1){
                        counter = (int) (distance / (2));
                    }


                    if(x >= (start + counter) & x <= (start + counter) + wCard & y >= 500 & y <= 500 + hCard){
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                    else if((x - 145) * (x - 145) + (y - 665) + (y - 665) <= 45 * 45){
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                    else if((x - 245) * (x - 245) + (y - 665) + (y - 665) <= 45 * 45){
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                    else if((x - 350) * (x - 350) + (y - 665) + (y - 665) <= 45 * 45){
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                    else if((x - 445) * (x - 445) + (y - 665) + (y - 665) <= 45 * 45){
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                    else if(shurikensStatus.size() == 4){
                        if(y >= 440 & y <= 485 & x >= 240 & x < 300){
                            setCursor(new Cursor(Cursor.HAND_CURSOR));

                            shurikensStatus.set(3, "shurikenHigh.png");
                        }
                        else if(y >= 440 & y <= 485 & x >= 300 & x < 360){
                            setCursor(new Cursor(Cursor.HAND_CURSOR));
                            shurikensStatus.set(2, "shurikenHigh.png");
                        }
                        else if(y >= 440 & y <= 485 & x >= 360 & x < 420){
                            setCursor(new Cursor(Cursor.HAND_CURSOR));

                            shurikensStatus.set(1, "shurikenHigh.png");
                        }
                        else if(y >= 440 & y <= 485 & x >= 420 & x < 480){
                            setCursor(new Cursor(Cursor.HAND_CURSOR));
                            shurikensStatus.set(0, "shurikenHigh.png");
                        }
                        else{
                            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }
                    }
                    else if(shurikensStatus.size() == 3){
                        if(y >= 440 & y <= 485 & x >= 300 & x < 360){
                            setCursor(new Cursor(Cursor.HAND_CURSOR));
                            shurikensStatus.set(2, "shurikenHigh.png");
                        }
                        else if(y >= 440 & y <= 485 & x >= 360 & x < 420){
                            setCursor(new Cursor(Cursor.HAND_CURSOR));

                            shurikensStatus.set(1, "shurikenHigh.png");
                        }
                        else if(y >= 440 & y <= 485 & x >= 420 & x < 480){
                            setCursor(new Cursor(Cursor.HAND_CURSOR));
                            shurikensStatus.set(0, "shurikenHigh.png");
                        }
                        else{
                            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }
                    }
                    else if(shurikensStatus.size() == 2){
                        if(y >= 440 & y <= 485 & x >= 360 & x < 420){
                            setCursor(new Cursor(Cursor.HAND_CURSOR));

                            shurikensStatus.set(1, "shurikenHigh.png");
                        }
                        else if(y >= 440 & y <= 485 & x >= 420 & x < 480){
                            setCursor(new Cursor(Cursor.HAND_CURSOR));
                            shurikensStatus.set(0, "shurikenHigh.png");
                        }
                        else{
                            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }
                    }
                    else if(shurikensStatus.size() == 1){
                        if(y >= 440 & y <= 485 & x >= 420 & x < 480){
                            setCursor(new Cursor(Cursor.HAND_CURSOR));
                            shurikensStatus.set(0, "shurikenHigh.png");
                        }
                        else{
                            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }
                    }
                    else{
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }

                }
            }
        });


    }

    public boolean isOnGameStartButtonBeforeGameStared(int x, int y){
        if(x >= 200 & x <= 400 & y >= 265 & y <= 335){
            return true;
        }
        return false;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);


        printBack(g);

        if(heart == 0){

            return;
        }

        if(!isGameStarted){
            if(hostId == playerId){
                BufferedImage imageStartButton = null;
                try {

                    if(isOnGameStartButton){
                        File file1 = new File(DefaultConfig.publicNameForPath + "StartHigh.png");
                        imageStartButton = ImageIO.read(file1);
                    }
                    else{
                        File file1 = new File(DefaultConfig.publicNameForPath + "Start.png");
                        imageStartButton = ImageIO.read(file1);
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                int w = 300 * 2 / 3;
                int h = 102 * 2 / 3;
                g.drawImage(imageStartButton, 200, 270, (int) w, (int) h, null);
            }
            else{
                BufferedImage imageWaitingText = null;
                File file1 = new File(DefaultConfig.publicNameForPath + "Waiting.png");
                try {
                    imageWaitingText = ImageIO.read(file1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                int w = 300 * 2 / 3;
                int h = 102 * 2 / 3;
                g.drawImage(imageWaitingText, 200, 270, (int) w, (int) h, null);
            }
        }
        else{


            printCardsForDown(cardsForPlayer, g);

            if(numberOfCardForOtherPlayers != null){
                for (int i = 0; i < numberOfCardForOtherPlayers.size(); i++) {
                    printCardsOtherPlayer(numberOfCardForOtherPlayers, i, g);
                }
            }

            printHearts(g);
            printGameLevel(g);
            printLastCardInGround(g);
            printShuriken(g);
            printEmojis(g);
        }

    }

    public void printEmojis(Graphics g){
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
                g.drawImage(imageCard, (int) (21.0 * 6 / 10), (int) (850.0 * 6 / 10), (int) wCard, (int) hCard, null);
            }
            else if(i == 1){
                g.drawImage(imageCard, (int) (21.0 * 6 / 10), (int) (32.0 * 6 / 10), (int) wCard, (int) hCard, null);
            }
            else if(i == 2){
                g.drawImage(imageCard, (int) (840.0 * 6 / 10), (int) (32.0 * 6 / 10), (int) wCard, (int) hCard, null);
            }
            else if(i == 3){
                g.drawImage(imageCard, (int) (840.0 * 6 / 10), (int) (850.0 * 6 / 10), (int) wCard, (int) hCard, null);
            }
        }
    }

    public void printShuriken(Graphics g){
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

    public void printHearts(Graphics g){
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

    public void printLastCardInGround(Graphics g){
        if(!(lastCardInGround >= 1 & lastCardInGround <= 100)){
            return;
        }

        BufferedImage imageCard = null;
        try {
            File file = getNumOfCard(lastCardInGround);
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

    public void printBack(Graphics g){
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

    public void printCardsForDown(ArrayList<NumberedCard> cardsForPlayer, Graphics g){
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
                File file = getNumOfCard(cardsForPlayer.get(j).getCardNumber());
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

    public void printCardsOtherPlayer(ArrayList<Integer> numberOfCardForOtherPlayers, int index, Graphics g){
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
                    if(cardsForPlayer.size() != 1){
                        num++;
                    }
                }
                else{
                    if(cardsForPlayer.size() != 1){
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
        frame.setSize(614, 758);
        this.setSize(614, 758);
        frame.setLocation(0,0);
        frame.getContentPane().add(this);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.WHITE);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void threadsForRepaint(){

        hostId = clientNetwork.getHostId(gameId);

        new Thread(new Runnable() {
            @Override
            public void run() {

                while(heart != 0){

                    try {
                        Thread.sleep(100);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    isGameStarted = clientNetwork.isGameStarted(gameId);


                    if(!isGameStarted){
                        continue;
                    }


                    String s = clientNetwork.updateGame(gameId, playerId);
                    GameStateEgg gameStateEgg = (GameStateEgg) JsonParser.parseToDataEgg(s, GAME_STATE_EGG);
                    isGameStarted = gameStateEgg.gameHasStarted();

                    if(s == null){
                        continue;
                    }
                    else if(s.equals("")){
                        continue;
                    }

                    gameLevel = gameStateEgg.getCurrentRound();
                    Hand hand = gameStateEgg.getHandOfCurrentPlayer();
                    cardsForPlayer = (ArrayList<NumberedCard>) hand.getNumberedCardsList();
                    heart = gameStateEgg.getNumberOfHealthCards();
                    lastCardInGround = gameStateEgg.getLastCardNumberOnGround();
                    numberOfAllPlayers = gameStateEgg.getNumberOfPlayers();
                    numberOfOtherPlayer = numberOfAllPlayers - 1;
                    hasThereAnyNinjaRequest = gameStateEgg.thereHasBeenANinjaRequest();
                    ArrayList<HandEgg> handDataEggs = (ArrayList<HandEgg>) gameStateEgg.getHandsOfOtherPlayersList();
                    numberOfCardForOtherPlayers.clear();

                    for (int i = 0; i < numberOfOtherPlayer; i++) {
                        numberOfCardForOtherPlayers.add(handDataEggs.get(i).getPlayerHand().getNumberedCardsList().size());
                    }

                    isCauseLooseOfHeartBecauseOfOtherPlayer = gameStateEgg.latestActionHasCausedLoss();


                    if(isCauseLooseOfHeartBecauseOfOtherPlayer){
                        if(gameStateEgg.getPlayerIdOfLatestAction() != playerId){
                            JOptionPane.showMessageDialog(null, "Cause lost of heart because card of "
                                    + gameStateEgg.getSmallestCardNumberThatHasCausedLoss(), "Lose heart" , JOptionPane.INFORMATION_MESSAGE);
                            numCardCauseLooseOfHeartBecauseOfOtherPlayer = -1;
                            isCauseLooseOfHeartBecauseOfOtherPlayer = false;

                        }
                    }

                    lastStatusOfPlayers.clear();
                    for (int i = 0; i < gameStateEgg.getPlayerEmojisList().size(); i++) {
                        lastStatusOfPlayers.add(gameStateEgg.getPlayerEmojisList().get(i));
                    }





                    if(hasThereAnyNinjaRequest){
//                        TODO
                    }

                    repaint();

                }

            }
        }).start();
    }


}