package frontend.gui;

import backend.logic.games.components.Hand;
import backend.logic.models.cards.NumberedCard;
import com.google.gson.Gson;
import frontend.gui.firstMenuPage.ChooseNumOfBot;
import frontend.gui.firstMenuPage.JoinGamePage;
import utils.config.DefaultConfig;
import frontend.client.ClientNetwork;
import utils.jsonparsing.literals.dataeggs.MakingMoveEgg;
import utils.jsonparsing.literals.dataeggs.gamestate.GameStateEgg;
import utils.jsonparsing.literals.dataeggs.gamestate.HandEgg;
import utils.jsonparsing.literals.dataeggs.joinablegames.JoinableGame;

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


    public GamePage(ClientNetwork clientNetwork, int gameId, int playerId) {
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

                    if(cardsForPlayer == null){
                        return;
                    }
                    else if(cardsForPlayer.size() == 0){
                        return;
                    }
                    else if(cardsForPlayer.size() == 1){
                        counter = (int) (distance / (numberOfCardForOtherPlayers.get(0) - (numberOfCardForOtherPlayers.get(0) / 2) + 1));
                    }

                    if(x >= (start + counter) & x <= (start + counter) + wCard & y >= 500 & y <= 500 + hCard){
                        Gson gson = new Gson();
                        String s = clientNetwork.makeMove(gameId, playerId, cardsForPlayer.get(0).getCardNumber());
                        MakingMoveEgg makingMoveEgg = gson.fromJson(s, MakingMoveEgg.class);
                        if(!makingMoveEgg.moveWasValid()){
                            JOptionPane.showMessageDialog(null, "Move wasn't valid", "Error!!" , JOptionPane.ERROR_MESSAGE);
                        }
                        else if(makingMoveEgg.moveCausesLossOfHealth()){
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

//                System.out.println(x + "  " + y);
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


                    if(cardsForPlayer == null){
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        return;
                    }
                    else if(cardsForPlayer.size() == 0){
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        return;
                    }
                    else if(cardsForPlayer.size() == 1){
                        counter = (int) (distance / (numberOfCardForOtherPlayers.get(0) - (numberOfCardForOtherPlayers.get(0) / 2) + 1));
                    }

                    if(x >= (start + counter) & x <= (start + counter) + wCard & y >= 500 & y <= 500 + hCard){
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
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

            printHeats(g);
            printGameLevel(g);
            printLastCardInGround(g);



        }



    }

    public void printHeats(Graphics g){
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

        for (int i = heart; i < numberOfOtherPlayer; i++) {
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

        new Thread(new Runnable() {
            @Override
            public void run() {

                while(true){

                    try {
                        Thread.sleep(100);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(!isGameStarted){
                        continue;
                    }


                    Gson gson = new Gson();
                    String s = clientNetwork.updateGame(gameId, playerId);
                    if(s == null){
                        continue;
                    }
                    else if(s.equals("")){
                        continue;
                    }

                    GameStateEgg gameStateEgg = gson.fromJson(s, GameStateEgg.class);
                    isGameStarted = gameStateEgg.isGameHasStarted();
                    gameLevel = gameStateEgg.getCurrentRound();
                    Hand hand = gameStateEgg.getHandOfCurrentPlayer();
                    cardsForPlayer = (ArrayList<NumberedCard>) hand.getNumberedCardsList();
                    heart = gameStateEgg.getNumberOfHealthCards();
                    lastCardInGround = gameStateEgg.getLastCardNumberOnGround();
                    numberOfOtherPlayer = gameStateEgg.getNumberOfPlayers() - 1;
                    ArrayList<HandEgg> handDataEggs = (ArrayList<HandEgg>) gameStateEgg.getHandsOfOtherPlayersList();
                    numberOfCardForOtherPlayers.clear();

                    for (int i = 0; i < numberOfOtherPlayer; i++) {
                        numberOfCardForOtherPlayers.add(handDataEggs.get(i).getPlayerHand().getNumberedCardsList().size());
                    }

                    repaint();




                }

            }
        }).start();
    }


}