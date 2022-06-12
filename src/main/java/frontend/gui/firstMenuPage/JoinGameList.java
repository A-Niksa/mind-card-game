package frontend.gui.firstMenuPage;

import utils.config.DefaultConfig;
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
import java.util.Map;
import java.util.TreeMap;

public class JoinGameList extends JPanel{
    JFrame frame;
    ClientNetwork clientNetwork;
    final int playerId;

    TreeMap<Integer, Integer> map;
    TreeMap<Integer, String> mapForString;


    public JoinGameList (int playerId){

        map = new TreeMap<>();
        mapForString = new TreeMap<>();
        for (int i = 0; i < 5 ; i++) {
            map.put(i, i);
            mapForString.put(i, "simple");
        }

        this.clientNetwork = clientNetwork;
        this.playerId = playerId;
        initializeFrame();


        addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {

            }

            public void mousePressed(MouseEvent e){
                int x = e.getX();
                int y = e.getY();

                System.out.println(x + "  " + y);
                if(x >= 90 & x <= 320 & y >= 180 & y <= 369){

                }

                else if(x >= 90 & x <= 320 & y >= 370 & y <= 579){

                }
                else if((x - 200) * (x - 200) + (y - 650) * (y - 650) <= 35 * 35){

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
                System.out.println(x + "   " + y);
                if(x >= 90 & x <= 320 & y >= 180 & y <= 369){

                }
                else if(x >= 90 & x <= 320 & y >= 370 & y <= 579){

                }
                else if((x - 200) * (x - 200) + (y - 650) * (y - 650) <= 35 * 35){

                }
                else{

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
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            String fileNameId = mapForString.get(key);
            if(fileNameId.toLowerCase().equals("Simple".toLowerCase())){
                fileNameId = "IdText.png";
            }
            else{
                fileNameId = "IdTextHigh.png";
            }
            BufferedImage imageID = null;
            try {
                File file1 = new File(DefaultConfig.publicNameForPath + fileNameId);
                imageID = ImageIO.read(file1);
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            int w = 27 * 3 / 4;
            int h = 77 * 3 / 4;
            g.drawImage(imageID, 0, counter * 75, (int) w, (int) h, null);



            String fileNameNumberOfBots = mapForString.get(key);
            if(fileNameNumberOfBots.toLowerCase().equals("Simple".toLowerCase())){
                fileNameNumberOfBots = "NumberOfBotsText.png";
            }
            else{
                fileNameNumberOfBots = "NumberOfBotsTextHigh.png";
            }
            BufferedImage imageNumberOfBots = null;
            try {
                File file1 = new File(DefaultConfig.publicNameForPath + fileNameNumberOfBots);
                imageNumberOfBots = ImageIO.read(file1);
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            w = 179 * 3 / 4;
            h = 77 * 3 / 4;
            g.drawImage(imageNumberOfBots, 100, counter * 75, (int) w, (int) h, null);


            g.setFont(new Font(Font.SERIF, Font.BOLD, 20));
            g.setColor(Color.GRAY);
            g.drawString(key + "", 50, counter * 75 - 36);

            g.setFont(new Font(Font.SERIF, Font.BOLD, 20));
            g.setColor(Color.GRAY);
            g.drawString(value + "", 250, counter * 75 - 36);

            counter++;
        }

    }

    public static void main(String[] args) {
        new JoinGameList(0);
    }

}
