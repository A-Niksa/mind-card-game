package frontend.gui;

import config.ConfigClass;
import javax.swing.*;
import java.awt.*;


public abstract class TemplateForPanel extends JPanel {
    protected MainFrame mainFrame;

    public TemplateForPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        configurePanel();
    }

    protected void drawPanel() {
        initializeComponents();
        connectListeners();
        repaint();
        revalidate();
    }

    private void configurePanel() {
        setSize(new Dimension(ConfigClass.FRAME_WIDTH, ConfigClass.FRAME_HEIGHT));
        setLayout(null);
    }

    protected abstract void initializeComponents();

    protected abstract void connectListeners();
}
