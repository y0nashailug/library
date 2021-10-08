package ui.panels;

import business.Book;
import business.Exceptions.BookException;
import business.SystemController;
import ui.BtnEventListener;
import ui.Util;

import javax.swing.*;
import java.awt.*;

public class AddBookCopyPanel extends JPanel implements MessageableWindow, BtnEventListener {
    private JComponent[] jComponents = {
            new JTextField(15),
    };

    private Component[] components;

    public AddBookCopyPanel() {

        JComponent form = new JPanel(new BorderLayout(5,5));
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 4));

        JButton addBookCopy = new JButton("Add book copy (+1)");
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
                    if (c.getClass().equals(JTextField.class)) {
                        values[i++] = ((JTextField) c).getText();
                    }
                }

                if (values[0] == null || values[0].equals("")) {
                    displayError("Please enter isbn of the book.");
                    return;
                }

                SystemController systemController = new SystemController();
                Book book = systemController.getBook(values[0].trim());
                book.addCopy();
                systemController.updateBook(book);
                displayInfo("Book copy added successfully. Book copy now " + book.getNumCopies());
            } catch(BookException e) {
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
