package frontend.gui.game;

import api.dataeggs.MakingMoveEgg;
import api.dataeggs.gamestate.EmojiEgg;
import api.dataeggs.gamestate.GameStateEgg;
import api.dataeggs.gamestate.HandEgg;
import api.dataeggs.gamestate.NinjaRequestEgg;
import api.dataeggs.ninjarequest.NinjaRequestStatus;
import backend.logic.games.components.Hand;
import backend.logic.models.cards.NumberedCard;
import com.google.gson.Gson;
import frontend.gui.firstMenuPage.FirstMenuPage;
import utils.config.ConfigFetcher;
import utils.config.ConfigIdentifier;
import frontend.client.ClientNetwork;

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
    ArrayList<Integer> numberCardForOtherPlayers;
    ArrayList<NumberedCard> cardsForPlayer;
    int numberOfAllPlayers;
    boolean hasThereAnyNinjaRequest;
    boolean isCauseLooseOfHeartBecauseOfOtherPlayer;
    int numCardCauseLooseOfHeartBecauseOfOtherPlayer;
    int numberOfShurikens;
    ArrayList<String> shurikensStatus;
    ArrayList<EmojiEgg> lastStatusOfPlayers;
    EmojiEgg myEmoji;
    boolean isOnNinjaRequest;


    public GamePage(ClientNetwork clientNetwork, int gameId, int playerId) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.clientNetwork = clientNetwork;

        hasThereAnyNinjaRequest = false;
        isCauseLooseOfHeartBecauseOfOtherPlayer = false;
        numberOfShurikens = 2;
        heart = 1;
        shurikensStatus = new ArrayList<>();
        lastStatusOfPlayers = new ArrayList<>();
        isOnNinjaRequest = false;

        for (int i = 0; i < numberOfShurikens; i++) {
            shurikensStatus.add("shuriken.png");
        }

        numberCardForOtherPlayers = new ArrayList<>();
        cardsForPlayer = new ArrayList<>();
        isGameStarted = false;
        isOnGameStartButton = false;

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


                    else if((x - 145) * (x - 145) + (y - 665) * (y - 665) <= 45 * 45){
                        clientNetwork.setEmoji(gameId, playerId, api.dataeggs.gamestate.Emoji.CRYING);
                    }
                    else if((x - 245) * (x - 245) + (y - 665) * (y - 665) <= 45 * 45){
                        clientNetwork.setEmoji(gameId, playerId, api.dataeggs.gamestate.Emoji.ANGRY);
                    }
                    else if((x - 350) * (x - 350) + (y - 665) * (y - 665) <= 45 * 45){
                        clientNetwork.setEmoji(gameId, playerId, api.dataeggs.gamestate.Emoji.LAUGHING);
                    }
                    else if((x - 445) * (x - 445) + (y - 665) * (y - 665) <= 45 * 45){
                        clientNetwork.setEmoji(gameId, playerId, api.dataeggs.gamestate.Emoji.LOVING);
                    }



                    if(cardsForPlayer.size() == 1){
                        counter = (int) (distance / (2));
                    }



                    if(x >= (start + counter) & x <= (start + counter) + wCard & y >= 500 & y <= 500 + hCard){

                        String s = clientNetwork.makeMove(gameId, playerId, 0);
                        Gson gson = new Gson();
                        MakingMoveEgg makingMoveEgg = gson.fromJson(s, MakingMoveEgg.class);
                        if(!makingMoveEgg.moveWasValid()){
                            JOptionPane.showMessageDialog(null, "Move wasn't valid", "Error!!" , JOptionPane.ERROR_MESSAGE);
                        }
                        else if(makingMoveEgg.moveCausesLossOfHealth()){
                            System.out.println(makingMoveEgg.getNumberOfSmallestCardThatHasCausedLoss());
                            JOptionPane.showMessageDialog(null, "Cause lost of heart because card of " + makingMoveEgg.getNumberOfSmallestCardThatHasCausedLoss() , "Lose heart" , JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    if(y >= 440 & y <= 485 & x >= 240 & x < 300){
                        if(numberOfShurikens >= 4){
                            clientNetwork.castNinjaVote(true, playerId, gameId);
                        }
                    }
                    else if(y >= 440 & y <= 485 & x >= 300 & x < 360){
                        if(numberOfShurikens >= 3){
                            clientNetwork.castNinjaVote(true, playerId, gameId);
                        }
                    }
                    else if(y >= 440 & y <= 485 & x >= 360 & x < 420){
                        if(numberOfShurikens >= 2){
                            clientNetwork.castNinjaVote(true, playerId, gameId);
                        }
                    }
                    else if(y >= 440 & y <= 485 & x >= 420 & x < 480){
                        if(numberOfShurikens >= 1){
                            clientNetwork.castNinjaVote(true, playerId, gameId);
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



                    if(heart == 0){
                        if(x >= 235 & x <= 370 & y >= 335 & y <= 393){
                            setCursor(new Cursor(Cursor.HAND_CURSOR));
                        }
                        else{
                            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }

                        return;
                    }
                    else if(cardsForPlayer.size() == 1){
                        counter = (int) (distance / (2));
                    }


                    if(x >= (start + counter) & x <= (start + counter) + wCard & y >= 500 & y <= 500 + hCard){
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                    else if((x - 145) * (x - 145) + (y - 665) * (y - 665) <= 45 * 45){
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                    else if((x - 245) * (x - 245) + (y - 665) * (y - 665) <= 45 * 45){
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                    else if((x - 350) * (x - 350) + (y - 665) * (y - 665) <= 45 * 45){
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                    else if((x - 445) * (x - 445) + (y - 665) * (y - 665) <= 45 * 45){
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
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


        Draw.drawBack(g, heart);

        if(heart == 0){

            return;
        }

        if(!isGameStarted){
            if(hostId == playerId){
                BufferedImage imageStartButton = null;
                try {

                    if(isOnGameStartButton){
                        File file1 = new File(ConfigFetcher.fetch(ConfigIdentifier.PRIVATE_NAME_FOR_PATH) + "StartHigh.png");
                        imageStartButton = ImageIO.read(file1);
                    }
                    else{
                        File file1 = new File(ConfigFetcher.fetch(ConfigIdentifier.PRIVATE_NAME_FOR_PATH) + "Start.png");
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
                File file1 = new File(ConfigFetcher.fetch(ConfigIdentifier.PRIVATE_NAME_FOR_PATH) + "Waiting.png");
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


            Draw.drawCardsForDown(cardsForPlayer, g);

            if(numberCardForOtherPlayers != null){
                for (int i = 0; i < numberCardForOtherPlayers.size(); i++) {
                    Draw.drawCardsOtherPlayer(numberCardForOtherPlayers, i, g);
                }
            }

            Draw.drawHearts(g, heart, numberOfAllPlayers);
            Draw.drawGameLevel(g, gameLevel);
            Draw.drawLastCardInGround(g, lastCardInGround);
            Draw.drawShuriken(g, numberOfShurikens, shurikensStatus);
            Draw.drawEmojis(g, lastStatusOfPlayers);
        }

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
                    Gson gson = new Gson();
                    GameStateEgg gameStateEgg = gson.fromJson(s , GameStateEgg.class);
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

                    numberCardForOtherPlayers.clear();

                    for (int i = 0; i < numberOfOtherPlayer; i++) {
                        numberCardForOtherPlayers.add(handDataEggs.get(i).getPlayerHand().getNumberedCardsList().size());
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
                    myEmoji = gameStateEgg.getEmojiEggOfCurrentPlayer();

                    lastStatusOfPlayers.add(myEmoji);
                    for (int i = 0; i < gameStateEgg.getPlayerEmojisList().size(); i++) {
                        lastStatusOfPlayers.add(gameStateEgg.getPlayerEmojisList().get(i));
                    }

                    numberOfShurikens = gameStateEgg.getNumberOfNinjaCards();

                    myEmoji = gameStateEgg.getEmojiEggOfCurrentPlayer();

                    if(gameStateEgg.thereHasBeenANinjaRequest() | gameStateEgg.shouldShowSmallestCards()){
                        ArrayList<NinjaRequestEgg> requestStates = (ArrayList) gameStateEgg.getNinjaRequestsList();
                        for (int i = 0; i < requestStates.size(); i++) {
                            if(requestStates.get(i).getPlayerId() == playerId & requestStates.get(i).getStatus() == NinjaRequestStatus.WAITING){
                                int result = JOptionPane.showConfirmDialog(null,"Ninja card request" , "ninja card", JOptionPane.YES_NO_OPTION);;
                                if(result == JOptionPane.YES_OPTION){
                                    clientNetwork.castNinjaVote(true, playerId, gameId);
                                }
                                else{
                                    clientNetwork.castNinjaVote(false, playerId, gameId);
                                }
                            }
                        }

                        if(gameStateEgg.shouldShowSmallestCards()){
                            String show = "";

                            for (int i = 0; i < gameStateEgg.getSmallestCardsList().size(); i++) {
                                if(i == gameStateEgg.getSmallestCardsList().size() - 1){
                                    show = show + gameStateEgg.getSmallestCardsList().get(i).getCard().getCardNumber();
                                    continue;
                                }

                                show = show + gameStateEgg.getSmallestCardsList().get(i).getCard().getCardNumber() + " - ";


                            }

                            JOptionPane.showMessageDialog(null, show, "Use ninja card" , JOptionPane.INFORMATION_MESSAGE);

                            Timer timer = new Timer(1000, e -> {});
                            timer.setRepeats(false);

                            clientNetwork.showedSmallestCards(gameId);

                        }
                    }


                    repaint();

                }

            }
        }).start();
    }

}