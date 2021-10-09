package ui.panels;

import business.Exceptions.BookException;
import business.SystemController;
import ui.BtnEventListener;
import ui.Util;
import ui.elements.LJButton;
import ui.elements.LJPasswordField;
import ui.elements.LJTextField;
import ui.rulesets.RuleException;
import ui.rulesets.RuleSet;
import ui.rulesets.RuleSetFactory;

import javax.swing.*;
import java.awt.*;

public class AddBookPanel extends JPanel implements MessageableWindow, BtnEventListener {

    private JComponent[] jComponents = {
            new LJTextField(),
            new LJTextField(),
            new LJTextField(),
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
    private String isbn;
    private String maxCheckoutLength;

    public AddBookPanel() {

        JComponent container = new JPanel(new BorderLayout(5, 5));
        JComponent form = new JPanel(new BorderLayout(5,5));
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 4));

        JButton addBookBtn = new LJButton("Add book");
        addEventListener(addBookBtn);

        String[] labels = {
                "Isbn",
                "Title",
                "Max checkout length",
                "First name",
                "Last name",
                "Phone",
                "Bio",
                "Street",
                "City",
                "State",
                "Zip",
        };

        JComponent labelsAndFields = Util.getTwoColumnLayout(labels, jComponents);
        components = labelsAndFields.getComponents();
        form.add(labelsAndFields, BorderLayout.CENTER);

        bottom.add(addBookBtn);

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

                isbn = values[0];
                maxCheckoutLength = values[2];

                RuleSet addBookPanel = RuleSetFactory.getRuleSet(this);
                addBookPanel.applyRules(this);

                String[] address = { values[7], values[8], values[9], values[10] };
                String[] author = { values[3], values[4], values[5], values[6] };

                systemController.addBook(isbn, values[1], Integer.parseInt(maxCheckoutLength), author, address);

                ViewBookIds.INSTANCE.revalidateTable(isbn);
                displayInfo("Book added successfully");
                clearForm();
            } catch(BookException | RuleException e) {
                displayError(e.getMessage());
            }
        });
    }

    public Component[] getComponents() {
        return components;
    }

    public String getIsbn() { return isbn; }
    public String getMaxCheckoutLength() { return maxCheckoutLength; }

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
