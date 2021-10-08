package ui.panels;

import business.Exceptions.BookException;
import business.SystemController;
import ui.BtnEventListener;
import ui.Util;

import javax.swing.*;
import java.awt.*;

public class AddBookPanel extends JPanel implements MessageableWindow, BtnEventListener {

    private JComponent[] jComponents = {
            new JTextField(15),
            new JTextField(15),
            new JTextField(15),
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

    public AddBookPanel() {

        JComponent form = new JPanel(new BorderLayout(5,5));
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 4));

        JButton addBookBtn = new JButton("Add book");
        addEventListener(addBookBtn);

        String[] labels = {
                "Isbn",
                "Title",
                "Max checkout length",
                "First name",
                "Last name",
                "Cell",
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

        this.add(form);
        this.add(bottom);
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

                String[] address = { values[7], values[8], values[9], values[10] };
                String[] author = { values[3], values[4], values[5], values[6] };

                systemController.addBook(values[0], values[1], Integer.parseInt(values[2]), author, address);

                ViewBookIds.INSTANCE.revalidateTable(values[0]);
                displayInfo("Book added successfully");
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
