package ui.panels;

import business.*;
import business.Exceptions.CheckoutRecordException;
import ui.BtnEventListener;
import ui.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.print.PrinterException;

public class SearchMemberForCheckoutPanel extends JPanel implements MessageableWindow, BtnEventListener {

    public static SearchMemberForCheckoutPanel INSTANCE = new SearchMemberForCheckoutPanel();
    private JComponent[] jComponents = {
            new JTextField(15),
    };
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel model;
    private Component[] components;
    private final String[] DEFAULT_COLUMN_HEADERS = { "Member Id", "Title", "ISBN", "Checkout date", "Due date", "Librarian" };
    private static final int SCREEN_WIDTH = 420;
    private static final int SCREEN_HEIGHT = 420;
    private static final int TABLE_WIDTH = (int) (0.75 * SCREEN_WIDTH);
    private static final int DEFAULT_TABLE_HEIGHT = (int) (0.75 * SCREEN_HEIGHT);

    private SearchMemberForCheckoutPanel() {}

    public void init() {
        paintScreen();
        displayTable();
    }

    public void displayTable() {
        model = new DefaultTableModel();
        table = new JTable();
        createHeaders();
        table.setModel(model);
        createTableAndTablePane();
        this.add(scrollPane);
        this.repaint();
        table.repaint();
    }

    public void paintScreen() {
        JComponent form = new JPanel(new BorderLayout(5,5));
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 4));

        JButton addBookCopy = new JButton("Search");
        addEventListener(addBookCopy);

        String[] labels = { "Member id" };

        JComponent labelsAndFields = Util.getTwoColumnLayout(labels, jComponents);
        components = labelsAndFields.getComponents();
        form.add(labelsAndFields, BorderLayout.CENTER);

        bottom.add(addBookCopy, BorderLayout.SOUTH);

        this.add(form);
        this.add(bottom);
    }

    private void createTableAndTablePane() {
        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(TABLE_WIDTH, DEFAULT_TABLE_HEIGHT));
        scrollPane.getViewport().add(table);
    }

    public void createHeaders() {
        model.setColumnIdentifiers(DEFAULT_COLUMN_HEADERS);
    }

    private void loadData(CheckoutRecord checkoutRecord) {

        for (int j = 0; j < checkoutRecord.getCheckoutRecordEntries().size(); j++) {
            String memberId = checkoutRecord.getLibraryMember().getMemberId();
            String title = checkoutRecord.getCheckoutRecordEntries().get(j).getBookCopy().getBook().getTitle();
            String isbn = checkoutRecord.getCheckoutRecordEntries().get(j).getBookCopy().getBook().getIsbn();
            String checkoutDate = checkoutRecord.getCheckoutRecordEntries().get(j).getCheckoutDate().toString();
            String dueDate = checkoutRecord.getCheckoutRecordEntries().get(j).getDueDate().toString();
            String firstName = checkoutRecord.getLibraryMember().getFirstName();
            model.addRow(new String[] { memberId, title, isbn, checkoutDate, dueDate, firstName });
        }

        model.fireTableDataChanged();
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

                SystemController systemController = new SystemController();
                String memberId = values[0].trim();
                CheckoutRecord checkoutRecord = systemController.getCheckoutRecord(memberId);
                loadData(checkoutRecord);
                table.print();

                displayInfo("Member found.");
            } catch(CheckoutRecordException | PrinterException e) {
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