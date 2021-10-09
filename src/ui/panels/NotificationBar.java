package ui.panels;

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

        statusBar.setBackground(Color.white);
        upper.add(statusBar);

        JScrollPane scroll = new JScrollPane(statusBar);
        statusBar.setEditable(false);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        scroll.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        upper.add(statusBar);

        container.add(upper, BorderLayout.NORTH);

        this.add(container);
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
