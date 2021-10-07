package ui.panels;

import ui.Util;

import javax.swing.*;
import java.awt.*;

public class AddBookPanel extends JPanel implements MessageableWindow {

    private JComponent[] components = {
            new JTextField(15),
            new JTextField(10),
            new JTextField(8),
    };
    private Component[] component;

    public AddBookPanel() {

        JComponent form = new JPanel(new BorderLayout(5,5));
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 4));

        JButton addBookBtn = new JButton("Add book");
        //addBookBtn.addActionListener(Control.INSTANCE.getAddBookListener());

        String[] labels = {
                "Author first name",
                "Author last name",
                "Book title",
        };

        JComponent labelsAndFields = Util.getTwoColumnLayout(labels, components);
        component = labelsAndFields.getComponents();
        form.add(labelsAndFields, BorderLayout.CENTER);

        bottom.add(addBookBtn);

        this.add(form);
        this.add(bottom);
    }

    public Component[] getComponents() {
        return component;
    }

    @Override
    public void updateData() {

    }
}
