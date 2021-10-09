package ui.panels;

import business.LoginException;
import business.SystemController;
import ui.BtnEventListener;
import ui.MessageModal;
import ui.Util;
import ui.elements.LJButton;
import ui.elements.LJPasswordField;
import ui.elements.LJTextField;
import ui.rulesets.RuleException;
import ui.rulesets.RuleSet;
import ui.rulesets.RuleSetFactory;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel implements BtnEventListener {

    private String username;
    private String password;
    private JComponent[] jComponents = {
            new LJTextField(),
            new LJPasswordField()
    };
    private Component[] components;

    public LoginPanel() {
        JPanel container = new JPanel(new BorderLayout());
        JPanel upper = new JPanel();
        JPanel bottom = new JPanel();

        upper.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 4));
        bottom.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 4));

        String[] labels = {
                "Username",
                "Password",
        };

        JComponent labelsAndFields = Util.getTwoColumnLayout(labels, jComponents);
        components = labelsAndFields.getComponents();
        upper.add(labelsAndFields, BorderLayout.CENTER);

        JButton signIn = new LJButton("Login");
        addEventListener(signIn);
        bottom.add(signIn);

        container.add(upper, BorderLayout.NORTH);
        container.add(bottom, BorderLayout.SOUTH);
        this.add(container);
    }

    @Override
    public void addEventListener(JButton btn) {
        btn.addActionListener(evt -> {

            try {

                String[] values = new String[components.length / 2];
                int i = 0;
                for (Component c: components) {
                    if (c.getClass().equals(LJTextField.class)) {
                        values[i++] = ((LJTextField) c).getText();
                    } else if (c.getClass().equals(LJPasswordField.class)) {
                        values[i++] = String.valueOf(((LJPasswordField) c).getPassword());
                    }
                }

                username = values[0].trim();
                password = values[1].trim();

                RuleSet loginRules = RuleSetFactory.getRuleSet(this);
                loginRules.applyRules(this);

                SystemController systemController = new SystemController();
                systemController.login(username, password);
                clearForm();
            } catch(LoginException | RuleException e) {
                new MessageModal.InnerMessageModalFrame().showMessage(e.getMessage(), "Error");
            }
        });
    }

    public void clearForm() {
        for (Component c: components) {
            if (c.getClass().equals(LJTextField.class)) {
                ((LJTextField) c).setText(null);
            } else if (c.getClass().equals(LJPasswordField.class)) {
                ((LJPasswordField) c).setText(null);
            }
        }
    }

    public String getUserName() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
