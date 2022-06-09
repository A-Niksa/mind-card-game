package frontend.gui.firstMenuPage;

import config.ConfigClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FirstMenuPage{
    JFrame frame;
    JButton creatNewGame;
    JButton joinGame;
    JButton exit;


    public FirstMenuPage() {
        frame = new JFrame();
        initializeFrame();

        creatNewGame = new JButton();
        joinGame = new JButton();
        exit = new JButton();

        addButtonToFrame(creatNewGame, "New game", ConfigClass.MenuPageFRAME_WIDTH / 2 , ConfigClass.MenuPageFRAME_HEIGHT / 2 - 100, new Color(0, 184, 42));
        addButtonToFrame(joinGame, "Join game", ConfigClass.MenuPageFRAME_WIDTH / 2, ConfigClass.MenuPageFRAME_HEIGHT / 2, Color.YELLOW);
        addButtonToFrame(exit, "Exit", ConfigClass.MenuPageFRAME_WIDTH / 2 , ConfigClass.MenuPageFRAME_HEIGHT / 2 + 100, Color.RED);

        creatNewGame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
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

        joinGame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
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

        exit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                JOptionPane jOptionPane = new JOptionPane();
                int result = JOptionPane.showConfirmDialog(frame,"Sure? You want to exit?", "mind card game",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if(result == 0){
                    frame.dispose();
                }
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
        ImageIcon icon = new ImageIcon(".\\src\\main\\resources\\FirstPageMenu.png");
        JLabel label = new JLabel();
        label.setIcon(icon);
        label.setBounds(0, 0, ConfigClass.MenuPageFRAME_WIDTH, ConfigClass.MenuPageFRAME_HEIGHT);
        frame.add(label);

    }


}
