package ui.panels;

import business.LoginException;
import business.SystemController;
import ui.BtnEventListener;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel implements MessageableWindow, BtnEventListener {

    private JTextField username;
    private JPasswordField password;

    public LoginPanel() {

        JPanel panel = new JPanel(new BorderLayout());
        JPanel upper = new JPanel();
        JPanel lower = new JPanel();
        JPanel bottom = new JPanel();

        upper.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 4));
        lower.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 4));
        bottom.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 4));

        JLabel usernameLabel = new JLabel("Username");
        JLabel passwordLabel = new JLabel("Password");

        usernameLabel.setPreferredSize(passwordLabel.getPreferredSize());

        username = new JTextField(11);
        password = new JPasswordField(11);

        JButton signIn = new JButton("Login");

        upper.add(usernameLabel);
        upper.add(username);

        lower.add(passwordLabel);
        lower.add(password);

        addEventListener(signIn);
        bottom.add(signIn);

        panel.add(upper, BorderLayout.NORTH);
        panel.add(lower, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        this.add(panel);
    }

    @Override
    public void addEventListener(JButton btn) {
        btn.addActionListener(evt -> {
            try {
                SystemController systemController = new SystemController();

                systemController.login(getUserName(), getPassword());
            } catch(LoginException e) {
                displayError(e.getMessage());
            }
        });
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
