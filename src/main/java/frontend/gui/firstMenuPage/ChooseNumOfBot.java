package frontend.gui.firstMenuPage;


import config.ConfigClass;
import frontend.client.ClientNetwork;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ChooseNumOfBot{
    JFrame frame;
    ClientNetwork clientNetwork;
    JButton back;
    JButton oneBot;
    JButton twoBot;
    JButton threeBot;
    JSlider jSlider;
    int idPlayer;

    public ChooseNumOfBot (int playerId){
        this.clientNetwork = clientNetwork;
        this.idPlayer = playerId;

        frame = new JFrame();
        initializeFrame();

        back = new JButton();
        oneBot = new JButton();
        twoBot = new JButton();
        threeBot = new JButton();

        addButtonToFrame(oneBot, "OneBot1", 150 , 100, Color.WHITE);
        addButtonToFrame(twoBot, "TwoBot1", 150 , 200, Color.WHITE);
        addButtonToFrame(threeBot, "ThreeBot1", 150 , 300, Color.WHITE);
        addButtonToFrame(back, "Back", 150, 400, Color.RED);



        back.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                frame.dispose();
                new FirstMenuPage(clientNetwork, playerId);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {
                frame.setCursor(new Cursor(Cursor.HAND_CURSOR));;
            }

            public void mouseExited(MouseEvent e) {
                frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));;
            }
        });
        oneBot.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {
                frame.setCursor(new Cursor(Cursor.HAND_CURSOR));;
            }

            public void mouseExited(MouseEvent e) {
                frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));;
            }
        });
        twoBot.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {
                frame.setCursor(new Cursor(Cursor.HAND_CURSOR));;
            }

            public void mouseExited(MouseEvent e) {
                frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));;
            }
        });
        threeBot.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {
                frame.setCursor(new Cursor(Cursor.HAND_CURSOR));;
            }

            public void mouseExited(MouseEvent e) {
                frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));;
            }
        });

        frame.setVisible(true);

    }

    private void initializeFrame(){
        frame.setLayout(null);
        frame.setTitle("Number of bot");
        frame.setSize(new Dimension(300, 500));
        frame.setLocation(550, 150);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addButtonToFrame(JButton jButton, String text, int x, int y, Color color){
        if(text.toLowerCase().equals(("OneBot1").toLowerCase()) || text.toLowerCase().equals(("TwoBot1").toLowerCase()) || text.toLowerCase().equals(("ThreeBot1").toLowerCase())){
            try {
                File file = new File(ConfigClass.publicNameForPath + text + ".png");
                Image image = ImageIO.read(file);
                jButton.setIcon(new ImageIcon(image));
                jButton.setBounds(x - 50, y, 100, 40);
                jButton.setFont(new Font("SERIF", Font.ITALIC, 20));
                jButton.setForeground(Color.BLACK);
                frame.add(jButton);
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        jButton.setText(text);
        jButton.setBounds(x - 50, y, 100, 40);
        jButton.setFont(new Font("SERIF", Font.ITALIC, 20));
        jButton.setForeground(Color.BLACK);
        jButton.setBackground(color);
        frame.add(jButton);
    }

    public static void main(String[] args) {
        new ChooseNumOfBot(0);
    }


}
