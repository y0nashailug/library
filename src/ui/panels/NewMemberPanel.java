package ui.panels;

import business.Address;
import business.LibrarySystemException;
import business.SystemController;
import ui.BtnEventListener;
import ui.Util;

import javax.swing.*;
import java.awt.*;

public class NewMemberPanel extends JPanel implements MessageableWindow, BtnEventListener {

    private JComponent[] jComponents = {
            new JTextField(15),
            new JTextField(15),
            new JTextField(15),
            new JTextField(15),
            new JTextField(15),
            new JTextField(15),
            new JTextField(15),
            new JTextField(15),
    };
    private Component[] components;

    public NewMemberPanel() {
        JPanel container = new JPanel(new BorderLayout());
        JComponent form = new JPanel(new BorderLayout(5,5));
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 4));

        JButton addBookBtn = new JButton("Add member");
        addEventListener(addBookBtn);

        String[] labels = {
                "MemberId",
                "First name",
                "Last name",
                "Cell",
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
                    if (c.getClass().equals(JTextField.class)) {
                        values[i++] = ((JTextField) c).getText();
                    }
                }

                systemController.addMember(values[0],
                        values[1],
                        values[2],
                        values[3],
                        new Address(values[4],
                                values[5],
                                values[6],
                                values[7]));
                displayInfo("Member added successfully");
                ViewMemberIds.INSTANCE.revalidateTable();
            } catch(LibrarySystemException e) {
                displayError(e.getMessage());
            }
        });
    }

    public Component[] getComponents() {
        return components;
    }

    @Override
    public void updateData() {

    }
}
