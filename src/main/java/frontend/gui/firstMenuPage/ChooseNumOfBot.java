package frontend.gui.firstMenuPage;

import config.ConfigClass;
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
import java.util.zip.CheckedOutputStream;

public class ChooseNumOfBot extends JPanel{
    JFrame frame;
    ClientNetwork clientNetwork;
    final int playerId;

    String fileName = "ChooseBotPage.png";

    public ChooseNumOfBot (int playerId){

        this.clientNetwork = clientNetwork;
        initializeFrame();

        this.playerId = playerId;



        addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {

            }

            public void mousePressed(MouseEvent e){
                int x = e.getX();
                int y = e.getY();

                if (x >= 90 & x <= 320 & y >= 0 & y <= 174) {
//                    ToDO
                }
                else if(x >= 90 & x <= 320 & y >= 175 & y <= 374){
//                    ToDO

                }
                else if(x >= 90 & x <= 320 & y >= 375 & y <= 584){
//                    ToDO

                }
                else if((x - 200) * (x - 200) + (y - 650) * (y - 650) <= 38 * 38){
                    frame.dispose();
                    new FirstMenuPage(playerId);
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

                if (x >= 90 & x <= 320 & y >= 0 & y <= 174) {
                    fileName = "ChooseBotPageOneRobot.png";
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                    repaint();
                }
                else if(x >= 90 & x <= 320 & y >= 175 & y <= 374){
                    fileName = "ChooseBotPageTwoRobot.png";
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                    repaint();
                }
                else if(x >= 90 & x <= 320 & y >= 375 & y <= 584){
                    fileName = "ChooseBotPageThreeRobot.png";
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                    repaint();
                }
                else if((x - 200) * (x - 200) + (y - 650) * (y - 650) <= 37 * 37){
                    fileName = "ChooseBotPage.png";
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                    repaint();
                }
                else{
                    fileName = "ChooseBotPage.png";
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    repaint();
                }

            }
        });
    }



    @Override
    public void paint(Graphics g) {
        super.paint(g);

        printBack(g);
    }

    public void printBack(Graphics g){
        BufferedImage imageBack = null;
        try {
            File file1 = new File(ConfigClass.publicNameForPath + fileName);
            imageBack = ImageIO.read(file1);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        int w = 400;
        int h = 700;
        g.drawImage(imageBack, 0, 0, (int) w, (int) h, null);
    }

    public void initializeFrame(){
        frame = new JFrame();
        frame.setTitle("Game");
        frame.setSize(414, 738);
        frame.setLocation(0,0);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.WHITE);
        frame.getContentPane().add(this);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new ChooseNumOfBot(0);
    }



}
