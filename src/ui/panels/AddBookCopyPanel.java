package ui.panels;

import business.Book;
import business.Exceptions.BookException;
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

public class AddBookCopyPanel extends JPanel implements MessageableWindow, BtnEventListener {

    private JComponent[] jComponents = {
            new LJTextField(),
    };

    private Component[] components;
    private String isbn;

    public AddBookCopyPanel() {

        JComponent form = new JPanel(new BorderLayout(5,5));
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 4));

        JButton addBookCopy = new LJButton("Add book copy (+1)");
        addEventListener(addBookCopy);

        String[] labels = { "Look up by Isbn" };

        JComponent labelsAndFields = Util.getTwoColumnLayout(labels, jComponents);
        components = labelsAndFields.getComponents();
        form.add(labelsAndFields, BorderLayout.CENTER);

        bottom.add(addBookCopy);

        this.add(form);
        this.add(bottom);
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
                    }
                }

                isbn = values[0].trim();
                RuleSet addBookCopyPanel = RuleSetFactory.getRuleSet(this);
                addBookCopyPanel.applyRules(this);

                SystemController systemController = new SystemController();
                Book book = systemController.getBook(isbn);
                systemController.addBookCopy(book);
                displayInfo("Book copy added successfully. Book copy now " + book.getNumCopies());
            } catch(BookException | RuleException e) {
                displayError(e.getMessage());
            }
        });
    }

    public Component[] getComponents() {
        return components;
    }

    public String getIsbn() { return isbn; }
    @Override
    public void updateData() {

    }
}
