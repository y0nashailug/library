package ui.panels;

import business.Address;
import business.Exceptions.LibrarySystemException;
import business.SystemController;
import ui.BtnEventListener;
import ui.Util;
import ui.elements.LJButton;
import ui.elements.LJTextField;
import ui.rulesets.RuleException;
import ui.rulesets.RuleSet;
import ui.rulesets.RuleSetFactory;

import javax.swing.*;
import java.awt.*;

public class NewMemberPanel extends JPanel implements MessageableWindow, BtnEventListener {

    private JComponent[] jComponents = {
            new LJTextField(),
            new LJTextField(),
            new LJTextField(),
            new LJTextField(),
            new LJTextField(),
            new LJTextField(),
            new LJTextField(),
            new LJTextField(),
    };
    private Component[] components;
    private String memberId;

    public NewMemberPanel() {
        JPanel container = new JPanel(new BorderLayout());
        JComponent form = new JPanel(new BorderLayout(5,5));
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 4));

        JButton addBookBtn = new LJButton("Add member");
        addEventListener(addBookBtn);

        String[] labels = {
                "MemberId",
                "First name",
                "Last name",
                "Phone",
                "Street",
                "City",
                "State",
                "Zip",
        };

        JComponent labelsAndFields = Util.getTwoColumnLayout(labels, jComponents);
        components = labelsAndFields.getComponents();
        form.add(labelsAndFields, BorderLayout.CENTER);

        bottom.add(addBookBtn, BorderLayout.CENTER);

        container.add(form, BorderLayout.NORTH);
        container.add(bottom, BorderLayout.SOUTH);

        this.add(container);
    }

    @Override
    public void addEventListener(JButton btn) {
        btn.addActionListener(evt -> {
            try {
                SystemController systemController = new SystemController();
                String[] values = new String[components.length / 2];
                int i = 0;
                for (Component c: components) {
                    if (c.getClass().equals(LJTextField.class)) {
                        values[i++] = ((LJTextField) c).getText();
                    }
                }

                memberId = values[0];
                RuleSet memberPanel = RuleSetFactory.getRuleSet(this);
                memberPanel.applyRules(this);

                systemController.addMember(values[0],
                        values[1],
                        values[2],
                        values[3],
                        values[4],
                        values[5],
                        values[6],
                        values[7]);
                displayInfo("Member added successfully");
                ViewMemberIds.INSTANCE.revalidateTable(values[0]);
                clearForm();
            } catch(LibrarySystemException | RuleException e) {
                displayError(e.getMessage());
            }
        });
    }

    public Component[] getComponents() {
        return components;
    }

    public String getMemberId() { return memberId; }
    public void clearForm() {
        for (Component c: components) {
            if (c.getClass().equals(LJTextField.class)) {
                ((LJTextField) c).setText(null);
            }
        }
    }

    @Override
    public void updateData() {

    }
}
