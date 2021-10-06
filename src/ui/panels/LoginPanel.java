package ui.panels;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel implements MessageableWindow {
    private JTextField username;
    private JPasswordField password;

    public LoginPanel() {

        JPanel panel = new JPanel(new BorderLayout());
        JPanel upper = new JPanel();
        JPanel lower = new JPanel();
        JPanel bottom = new JPanel();

        upper.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 4));
        lower.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 4));
        bottom.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 4));

        JLabel usernameLabel = new JLabel("Username");
        JLabel passwordLabel = new JLabel("Password");

        usernameLabel.setPreferredSize(passwordLabel.getPreferredSize());

        username = new JTextField(11);
        password = new JPasswordField(11);

        JButton signin = new JButton("Login");

        upper.add(usernameLabel);
        upper.add(username);

        lower.add(passwordLabel);
        lower.add(password);

        //Add event listener
        bottom.add(signin);

        panel.add(upper, BorderLayout.NORTH);
        panel.add(lower, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        this.add(panel);
    }

    public String getUserName() {
        return username.getText().trim();
    }

    public String getPassword() {
        return String.valueOf(password.getPassword());
    }

    @Override
    public void updateData() {

    }
}
