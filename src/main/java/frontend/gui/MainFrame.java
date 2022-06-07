package frontend.gui;

import config.ConfigClass;
import frontend.gui.firstMenuPage.FirstMenuPage;
import javax.swing.*;
import java.awt.*;


public class MainFrame extends JFrame {
    private JPanel currentPanel;

    public MainFrame() {
        FirstMenuPage firstMenuPage = new FirstMenuPage(this);
        setCurrentPanel(firstMenuPage);
        configureFrame();
        repaintFrame();
    }

    public void setCurrentPanel(JPanel currentPanel) {
        this.currentPanel = currentPanel;
        getContentPane().removeAll();
        getContentPane().add(this.currentPanel);
        repaintFrame();
    }

    private void configureFrame() {
        setSize(new Dimension(ConfigClass.FRAME_WIDTH, ConfigClass.FRAME_HEIGHT));
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void repaintFrame() {
        repaint();
        revalidate();
    }
}
