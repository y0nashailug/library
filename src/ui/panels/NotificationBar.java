package ui.panels;

import javax.swing.*;
import java.awt.*;

public class NotificationBar extends JPanel implements MessageableWindow {

    public static JTextArea statusBar = new JTextArea(4, 45);

    public NotificationBar() {
        init();
    }

    public void init() {

        JPanel panel = new JPanel(new BorderLayout());
        JPanel upper = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 4));

        statusBar.setBackground(Color.white);
        upper.add(statusBar);

        JScrollPane scroll = new JScrollPane(statusBar);
        statusBar.setEditable(false);
        statusBar.setBorder(null);
        scroll.setBorder(null);

        upper.add(scroll);
        panel.add(upper, BorderLayout.NORTH);

        this.add(panel);
        this.setForeground(Color.WHITE);
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
