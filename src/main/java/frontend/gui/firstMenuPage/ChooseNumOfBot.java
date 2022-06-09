package frontend.gui.firstMenuPage;

import com.google.gson.Gson;
import config.ConfigClass;
import frontend.gui.ClientNetwork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class ChooseNumOfBot{
    JFrame frame;
    JButton creatNewGame;
    ClientNetwork clientNetwork;
    JButton back;
    JSlider jSlider;
    int idPlayer;

    public ChooseNumOfBot (ClientNetwork clientNetwork, int playerId){
        this.clientNetwork = clientNetwork;
        this.idPlayer = playerId;

        frame = new JFrame();
        initializeFrame();

        creatNewGame = new JButton();
        back = new JButton();

        addButtonToFrame(creatNewGame, "Creat", ConfigClass.MenuPageFRAME_WIDTH / 2 , ConfigClass.MenuPageFRAME_HEIGHT / 2 + 100, new Color(0, 184, 42));
        addButtonToFrame(back, "Back", ConfigClass.MenuPageFRAME_WIDTH / 2 , ConfigClass.MenuPageFRAME_HEIGHT / 2 + 200, Color.RED);

        addSlider();

        creatNewGame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                String json = clientNetwork.creatNewGame(jSlider.getValue(), playerId);
                // TODO
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


        setBackImage();
        frame.setVisible(true);

    }

    private void initializeFrame(){
        frame.setLayout(null);
        frame.setTitle("Menu page");
        frame.setLocation(new Point((1920 - ConfigClass.MenuPageFRAME_WIDTH) / 2 - 150, (1080 - ConfigClass.MenuPageFRAME_HEIGHT) / 2 - 100));
        frame.setSize(new Dimension(ConfigClass.MenuPageFRAME_WIDTH, ConfigClass.MenuPageFRAME_HEIGHT));
        frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void addButtonToFrame(JButton jButton, String text, int x, int y, Color color){
        jButton.setText(text);
        jButton.setBounds(x - 50, y, 150, 50);
        jButton.setFont(new Font("SERIF", Font.ITALIC, 20));
        jButton.setForeground(Color.BLACK);
        jButton.setBackground(color);
        frame.add(jButton);
    }

    public void setBackImage(){
        ImageIcon icon = new ImageIcon(".\\src\\main\\resources\\ChooseNumOfBot.png");
        JLabel label = new JLabel();
        label.setIcon(icon);
        label.setBounds(0, 0, ConfigClass.MenuPageFRAME_WIDTH, ConfigClass.MenuPageFRAME_HEIGHT);
        frame.add(label);

    }



    public void addSlider(){
        JSlider slider = new JSlider(SwingConstants.HORIZONTAL, 0, 3, 0);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setPreferredSize(new Dimension(100, 50));
        slider.setBounds(ConfigClass.MenuPageFRAME_WIDTH / 2  - 130 , ConfigClass.MenuPageFRAME_HEIGHT / 2 , 300 , 50);
        frame.getContentPane().add(slider);
    }


}
