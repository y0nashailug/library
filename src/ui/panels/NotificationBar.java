package ui.panels;

import librarysystem.Config;

import javax.swing.*;
import java.awt.*;

public class NotificationBar extends JPanel implements MessageableWindow {

    public static JTextArea statusBar = new JTextArea();

    public NotificationBar() {
        init();
    }

    public void init() {

        JComponent container = new JPanel(new BorderLayout());
        JPanel upper = new JPanel(new BorderLayout());
        JPanel bottom = new JPanel(new BorderLayout());

        statusBar.setBackground(Color.white);
        statusBar.setPreferredSize(new Dimension(Config.APP_WIDTH - 20, 24));
        upper.add(statusBar);

        statusBar.setEditable(false);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        upper.add(statusBar, BorderLayout.NORTH);

        container.add(upper, BorderLayout.NORTH);
        container.add(bottom, BorderLayout.SOUTH);

        this.add(container);
        this.setForeground(Color.WHITE);
        this.setBackground(Color.WHITE);
    }

    public JTextArea getStatusBar() {
        return statusBar;
    }

    public void setStatusBar(String s) {
        statusBar.setVisible(true);
        statusBar.setText(s);
    }

    @Override
    public void updateData() {

    }
}
