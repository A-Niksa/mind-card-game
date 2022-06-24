package frontend.gui.firstMenuPage;

import api.dataeggs.DataEggType;
import api.dataeggs.joinablegames.JoinableGame;
import api.dataeggs.joinablegames.JoinableGamesEgg;
import utils.config.ConfigFetcher;
import utils.config.ConfigIdentifier;
import utils.jsonparsing.JsonParser;
import com.google.gson.Gson;
import frontend.gui.game.GamePage;
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

import static javax.swing.SwingUtilities.isRightMouseButton;

public class JoinGamePage extends JPanel{
    JFrame frame;
    ClientNetwork clientNetwork;
    final int playerId;

    boolean notClicked;

    ArrayList<Integer> arrayListIds;
    ArrayList<Integer> arrayListNumOfBot;
    ArrayList<Integer> arrayListFreePlayer;
    ArrayList<String> arrayListStringType;



    public JoinGamePage(ClientNetwork clientNetwork, int playerId){

        notClicked = true;
        arrayListIds = new ArrayList<>();
        arrayListNumOfBot = new ArrayList<>();
        arrayListFreePlayer = new ArrayList<>();
        arrayListStringType = new ArrayList<>();


        this.clientNetwork = clientNetwork;
        this.playerId = playerId;

        startThreads();

        initializeFrame();

        addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {

            }

            public void mousePressed(MouseEvent e){
                int x = e.getX();
                int y = e.getY();

                if(isRightMouseButton(e)){
                    frame.dispose();
                    new FirstMenuPage(clientNetwork, playerId);
                    return;
                }
                if(y < arrayListIds.size() * 80){
                    int index = y / 80;
                    boolean can = clientNetwork.joinGame(arrayListIds.get(index), playerId);
                    if(can){
                        notClicked = false;
                        int gameId = arrayListIds.get(index);
                        System.out.println("gameId is : " + gameId);
                        new GamePage(clientNetwork, gameId, playerId);
                        frame.dispose();
                    }
                    else{
                        JOptionPane.showMessageDialog(frame, "Error!!!",
                                "ypu can't join this game", JOptionPane.ERROR_MESSAGE);
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
                arrayListStringType.clear();
                for (int i = 0; i < arrayListIds.size(); i++) {
                    arrayListStringType.add("JoinGamePageText.png");
                }
                if(y < arrayListIds.size() * 80){
                    int index = y / 80;
                    arrayListStringType.set(index, "JoinGamePageTextHigh.png");
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                    repaint();
                }

                else{
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    repaint();

                }



            }
        });
    }

    public void initializeFrame(){
        frame = new JFrame();
        frame.setTitle("Game");
        frame.setSize(414, 738);
        frame.setLocation(0,0);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.WHITE);
        this.setBackground(Color.WHITE);
        frame.setResizable(false);
        frame.getContentPane().add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        printBack(g);
    }

    public void printBack(Graphics g){
        int counter = 0;
        for (int i = 0; i < arrayListIds.size(); i++) {
            BufferedImage imageID = null;
            try {
                if(arrayListStringType.size() == 0){
                    continue;
                }
                File file1 = new File(ConfigFetcher.fetch(ConfigIdentifier.PRIVATE_NAME_FOR_PATH) +
                        arrayListStringType.get(i));
                imageID = ImageIO.read(file1);
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            int w = 1200 * 1 / 3;
            int h = 211 * 1 / 3;
            g.drawImage(imageID, 0, counter * 80 + 30, (int) w, (int) h, null);


            g.setFont(new Font(Font.SERIF, Font.BOLD, 20));
            g.setColor(Color.GRAY);
            g.drawString(arrayListIds.get(i) + "", 30, counter * 80 + 60);

            g.setFont(new Font(Font.SERIF, Font.BOLD, 20));
            g.setColor(Color.GRAY);
            g.drawString(arrayListNumOfBot.get(i) + "", 210, counter * 80 + 60);

            g.setFont(new Font(Font.SERIF, Font.BOLD, 20));
            g.setColor(Color.GRAY);
            g.drawString(arrayListFreePlayer.get(i) + "", 370, counter * 80 + 60);


            counter++;
        }



    }

    public void startThreads(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (notClicked){
                    String output = clientNetwork.allJoinableGames();
                    JoinableGamesEgg joinableGamesEgg = (JoinableGamesEgg) JsonParser.parseToDataEgg(output, DataEggType.JOINABLE_GAMES_EGG);

                    ArrayList<JoinableGame> joinableGamesList = (ArrayList<JoinableGame>) joinableGamesEgg.getJoinableGamesList();

                    arrayListIds.clear();
                    arrayListFreePlayer.clear();
                    arrayListNumOfBot.clear();

                    for (int i = 0; i < joinableGamesList.size() ; i++) {
                        arrayListIds.add(joinableGamesList.get(i).getGameId());
                        arrayListFreePlayer.add(joinableGamesList.get(i).getNumberOfFreePlayers());
                        arrayListNumOfBot.add(joinableGamesList.get(i).getNumberOfBots());
                    }

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

}
