package ui.panels;

import business.Address;
import business.LibrarySystemException;
import business.SystemController;
import ui.BtnEventListener;
import ui.Util;

import javax.swing.*;
import java.awt.*;

public class NewMemberPanel extends JPanel implements MessageableWindow, BtnEventListener {

    private JComponent[] components = {
            new JTextField(15),
            new JTextField(15),
            new JTextField(15),
            new JTextField(15),
            new JTextField(15),
            new JTextField(15),
            new JTextField(15),
            new JTextField(15),
    };
    private Component[] component;

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

        JComponent labelsAndFields = Util.getTwoColumnLayout(labels, components);
        component = labelsAndFields.getComponents();
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
                systemController.addMember("10201", "Yonas", "Hailu", "4157125785", new Address("1000 4th N", "Fairfield", "IA", "57557"));
                displayInfo("Member added successfully");
                ViewMemberIds.INSTANCE.revalidateTable();
            } catch(LibrarySystemException e) {
                displayError(e.getMessage());
            }
        });
    }

    public Component[] getComponents() {
        return component;
    }

    @Override
    public void updateData() {

    }
}
