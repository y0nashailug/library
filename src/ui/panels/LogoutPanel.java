package ui.panels;

import business.ControllerInterface;
import business.Exceptions.LogoutException;
import business.LoginException;
import business.SystemController;
import ui.BtnEventListener;
import ui.elements.LJButton;

import javax.swing.*;
import java.awt.*;

public class LogoutPanel extends JPanel implements MessageableWindow, BtnEventListener {

    public LogoutPanel() {

        JPanel panel = new JPanel(new BorderLayout());
        JPanel upper = new JPanel();


        upper.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 4));

        JButton logout = new LJButton("Logout");

        addEventListener(logout);
        upper.add(logout);

        panel.add(upper, BorderLayout.NORTH);

        this.add(panel);
    }

    @Override
    public void addEventListener(JButton btn) {
        btn.addActionListener(evt -> {
            try {
                ControllerInterface ci = new SystemController();
                ci.logout();
            } catch(LogoutException e) {
                displayError(e.getMessage());
            }
        });
    }

    @Override
    public void updateData() {

    }
}
