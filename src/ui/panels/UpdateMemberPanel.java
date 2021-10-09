package ui.panels;

import business.Exceptions.LibrarySystemException;
import business.LibraryMember;
import business.SystemController;
import ui.BtnEventListener;
import ui.Util;
import ui.elements.LJButton;
import ui.elements.LJTextField;

import javax.swing.*;
import java.awt.*;

public class UpdateMemberPanel extends JPanel implements MessageableWindow, BtnEventListener {

    private String memberId;
    private JComponent[] jComponent = {
            new LJTextField(),
    };

    public JComponent[] jComponents = {
            new LJTextField(),
            new LJTextField(),
            new LJTextField(),
    };

    private Component[] components;
    private Component[] component;

    public UpdateMemberPanel() {

        JComponent topNorthForm = new JPanel(new BorderLayout(5,5));
        JComponent updateForm = new JPanel(new BorderLayout(5,5));
        JComponent updateFormCenterNorth = new JPanel(new BorderLayout(5,5));
        JComponent updateFormCenterSouth = new JPanel(new BorderLayout(5,5));
        JComponent bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 4));

        JButton searchBtn = new LJButton("Search");
        addEventListener(searchBtn);

        JButton updateBtn = new LJButton("Update");
        addUpdateEventListener(updateBtn);

        JButton deleteBtn = new LJButton("Delete");
        deleteEventListener(deleteBtn);

        String[] labels = { "Member id" };
        String[] updateLabels = { "First name", "Last name", "Tel" };

        JComponent searchLabelsAndFields = Util.getTwoColumnLayout(labels, jComponent);
        component = searchLabelsAndFields.getComponents();
        topNorthForm.add(searchLabelsAndFields, BorderLayout.NORTH);
        topNorthForm.add(searchBtn, BorderLayout.EAST);

        JComponent updateLabelsAndFields = Util.getTwoColumnLayout(updateLabels, jComponents);
        components = updateLabelsAndFields.getComponents();
        updateFormCenterNorth.add(updateLabelsAndFields, BorderLayout.CENTER);
        updateFormCenterSouth.add(updateBtn, BorderLayout.WEST);
        updateFormCenterSouth.add(deleteBtn, BorderLayout.EAST);

        updateForm.add(updateFormCenterNorth, BorderLayout.NORTH);
        updateForm.add(updateFormCenterSouth, BorderLayout.SOUTH);

        this.add(topNorthForm);
        this.add(updateForm);
    }

    @Override
    public void addEventListener(JButton btn) {
        btn.addActionListener(evt -> {
            try {
                SystemController systemController = new SystemController();
                String[] values = new String[component.length / 2];
                int i = 0;
                for (Component c: component) {
                    if (c.getClass().equals(LJTextField.class)) {
                        values[i++] = ((LJTextField) c).getText();
                    }
                }

                memberId = values[0].trim();
                LibraryMember libraryMember = systemController.getMember(memberId);
                System.out.println(libraryMember);
                String[] list = { libraryMember.getFirstName(), libraryMember.getLastName(), libraryMember.getTelephone() };

                int j = 0;
                for (Component c: components) {
                    if (c.getClass().equals(LJTextField.class)) {
                        ((LJTextField) c).setText(list[j++]);
                    }
                }

                // TODO: - Populate information
                // String memberId, String fname, String lname, String tel,Address add

                displayInfo("Member found");
            } catch(LibrarySystemException e) {
                displayError(e.getMessage());
            }
        });
    }

    public void addUpdateEventListener(JButton btn) {
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

                systemController.getAndUpdateMember(memberId, values[0], values[1], values[2]);
                displayInfo("Member updated successfully");
            } catch(LibrarySystemException e) {
                displayError(e.getMessage());
            }
        });
    }

    public void deleteEventListener(JButton btn) {
        btn.addActionListener(evt -> {
            try {
                SystemController systemController = new SystemController();
                systemController.deleteMember(memberId);
                displayInfo("Member deleted successfully");
            } catch(LibrarySystemException e) {
                displayError(e.getMessage());
            }
        });
    }

    @Override
    public void updateData() {

    }
}
