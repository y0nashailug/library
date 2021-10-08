package ui.panels;

import business.Book;
import business.CheckoutRecord;
import business.Exceptions.BookException;
import business.Exceptions.CheckoutRecordException;
import business.Exceptions.LibrarySystemException;
import business.LibraryMember;
import business.SystemController;
import ui.BtnEventListener;
import ui.Util;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class CheckoutBook extends JPanel implements MessageableWindow, BtnEventListener {

    private JComponent[] jComponents = {
            new JTextField(15),
            new JTextField(15),
    };

    private Component[] components;

    public CheckoutBook() {

        JComponent form = new JPanel(new BorderLayout(5,5));
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 4));

        JButton addBookCopy = new JButton("Checkout");
        addEventListener(addBookCopy);

        String[] labels = { "Member id", "Book isbn" };

        JComponent labelsAndFields = Util.getTwoColumnLayout(labels, jComponents);
        components = labelsAndFields.getComponents();
        form.add(labelsAndFields, BorderLayout.CENTER);

        bottom.add(addBookCopy, BorderLayout.SOUTH);

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
                    displayError("Please enter member id.");
                    return;
                }
                if (values[1] == null || values[1].equals("")) {
                    displayError("Please enter isbn of the book.");
                    return;
                }

                SystemController systemController = new SystemController();
                Book book = systemController.getBook(values[1].trim());
                LibraryMember member = systemController.getMember(values[0].trim());

                //TODO:
                // 1. add record on Checkout collection
                // 2. add record to member and update Member collection
                // 3. update book copy and set available to [false] on Book collection

                systemController.addCheckoutRecord(member.getMemberId(), book.getNextAvailableCopy(), LocalDate.now(), LocalDate.now().plusDays(1));
                CheckoutRecord checkoutRecord = systemController.getCheckoutRecord(member.getMemberId());
                member.setCheckoutRecord(checkoutRecord);

                System.out.println(member);

                systemController.updateMember(member);
                book.getNextAvailableCopy().changeAvailability();
                systemController.updateBook(book);

                CheckoutStatus.INSTANCE.revalidateTable(checkoutRecord);

                displayInfo("Checkout successfully");
            } catch(BookException | LibrarySystemException | CheckoutRecordException e) {
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
