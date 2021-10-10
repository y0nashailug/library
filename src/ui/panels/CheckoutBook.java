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
import ui.elements.LJButton;
import ui.elements.LJTextField;
import ui.rulesets.RuleException;
import ui.rulesets.RuleSet;
import ui.rulesets.RuleSetFactory;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class CheckoutBook extends JPanel implements MessageableWindow, BtnEventListener {

    private JComponent[] jComponents = {
            new LJTextField(),
            new LJTextField(),
    };
    private Component[] components;
    private String memberId;
    private String isbn;

    public CheckoutBook() {

        JComponent container = new JPanel(new BorderLayout(5, 5));
        JComponent form = new JPanel(new BorderLayout(5,5));
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 4));

        JButton addBookCopy = new LJButton("Checkout");
        addEventListener(addBookCopy);

        String[] labels = { "Member id", "Book isbn" };

        JComponent labelsAndFields = Util.getTwoColumnLayout(labels, jComponents);
        components = labelsAndFields.getComponents();
        form.add(labelsAndFields, BorderLayout.CENTER);
        bottom.add(addBookCopy, BorderLayout.SOUTH);

        container.add(form, BorderLayout.NORTH);
        container.add(bottom, BorderLayout.SOUTH);

        this.add(container);
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

                memberId = values[0].trim();
                isbn = values[1].trim();

                RuleSet checkoutBook = RuleSetFactory.getRuleSet(this);
                checkoutBook.applyRules(this);

                SystemController systemController = new SystemController();
                LibraryMember member = systemController.getMember(memberId);
                Book book = systemController.getBook(isbn);

                //TODO:
                // 1. add record to CheckoutRecord collection
                // 2. add record to member and update Member collection
                // 3. update book copy and set available to [false] on Book collection

                CheckoutRecord checkoutRecord  = systemController.addCheckoutRecord(member, book);
                if (checkoutRecord == null) throw new CheckoutRecordException("Checkout record was not found.");

                //CheckoutStatus.INSTANCE.revalidateTable(checkoutRecord);
                displayInfo("Checkout successfully");
            } catch(BookException | LibrarySystemException | CheckoutRecordException | RuleException e) {
                displayError(e.getMessage());
            }
        });
    }

    public Component[] getComponents() {
        return components;
    }

    public String getIsbn() { return isbn; }
    public String getMemberId() { return memberId; }
    @Override
    public void updateData() {

    }
}
