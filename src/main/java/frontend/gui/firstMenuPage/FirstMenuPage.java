package frontend.gui.firstMenuPage;

import frontend.client.ClientNetwork;
import utils.config.ConfigFetcher;
import utils.config.ConfigIdentifier;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FirstMenuPage extends JPanel{
    JFrame frame;
    ClientNetwork clientNetwork;
    final int playerId;


    String fileName = "FirstPage.png";

    public FirstMenuPage (ClientNetwork clientNetwork, int playerId){

        this.clientNetwork = clientNetwork;
        this.playerId = playerId;
        initializeFrame();


        addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {

            }

            public void mousePressed(MouseEvent e){
                int x = e.getX();
                int y = e.getY();

                if(x >= 90 & x <= 320 & y >= 180 & y <= 369){
                    frame.dispose();
                    new ChooseNumOfBot(clientNetwork, playerId);
                }

                else if(x >= 90 & x <= 320 & y >= 370 & y <= 579){
                    frame.dispose();
                    new JoinGamePage(clientNetwork, playerId);
                }
                else if((x - 200) * (x - 200) + (y - 650) * (y - 650) <= 35 * 35){
                    frame.dispose();
//                    TODO -> stop network
                }
                else{

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

                if(x >= 90 & x <= 320 & y >= 180 & y <= 369){
                    fileName = "FirstPageNewGameHigh.png";
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                    repaint();
                }
                else if(x >= 90 & x <= 320 & y >= 370 & y <= 579){
                    fileName = "FirstPageJoinGameHigh.png";
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                    repaint();
                }
                else if((x - 200) * (x - 200) + (y - 650) * (y - 650) <= 35 * 35){
                    fileName = "FirstPage.png";
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                    repaint();
                }
                else{
                    fileName = "FirstPage.png";
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
        BufferedImage imageBack = null;
        try {
            File file1 = new File(ConfigFetcher.fetch(ConfigIdentifier.PRIVATE_NAME_FOR_PATH) + fileName);
            imageBack = ImageIO.read(file1);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        int w = 400;
        int h = 700;
        g.drawImage(imageBack, 0, 0, (int) w, (int) h, null);
    }


}
