package ui.panels;

import javax.swing.*;
import java.awt.*;

public class NotificationBar extends JPanel {

    JTextArea message;

    public NotificationBar() {

        JPanel panel = new JPanel(new BorderLayout());
        JPanel upper = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 4));

        message = new JTextArea(4, 45);
        message.setBackground(Color.white);
        upper.add(message);

        JScrollPane scroll = new JScrollPane(message);
        message.setEditable(false);
        message.setBorder(null);
        scroll.setBorder(null);

        upper.add(scroll);


        panel.add(upper, BorderLayout.NORTH);

        this.add(panel);
        this.setForeground(Color.WHITE);
    }

    public JTextArea getMessage() {
        return message;
    }

    public void setMessage(String s) {
        message.setVisible(true);
        message.setText(s);
    }
}
