package ui.panels;

import business.CheckoutRecord;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class CheckoutStatus extends JPanel {

    ControllerInterface ci = new SystemController();
    public static CheckoutStatus INSTANCE = new CheckoutStatus();
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel model;

    private final String[] DEFAULT_COLUMN_HEADERS = { "Member Id", "Title", "ISBN", "Checkout date", "Due date", "Librarian" };
    private static final int SCREEN_WIDTH = 420;
    private static final int SCREEN_HEIGHT = 420;
    private static final int TABLE_WIDTH = (int) (0.75 * SCREEN_WIDTH);
    private static final int DEFAULT_TABLE_HEIGHT = (int) (0.75 * SCREEN_HEIGHT);

    private CheckoutStatus() {}

    public void init() {
        model = new DefaultTableModel();
        table = new JTable();
        createHeaders();
        loadData();
        createTableAndTablePane();
        this.add(scrollPane);
    }

    private void createTableAndTablePane() {
        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(TABLE_WIDTH, DEFAULT_TABLE_HEIGHT));
        scrollPane.getViewport().add(table);
    }

    public void createHeaders() {
        model.setColumnIdentifiers(DEFAULT_COLUMN_HEADERS);
    }

    private void loadData() {
        HashMap<String, CheckoutRecord> checkoutRecords = ci.allCheckoutRecords();
        HashMap<String, LibraryMember> members = ci.getMembers();
        String[][] checkoutData = new String[checkoutRecords.size()][DEFAULT_COLUMN_HEADERS.length];
        List<String> checkoutRecordIds = ci.allCheckoutRecordIds();

        for(int i = 0 ; i < checkoutRecords.size(); i++) {
            CheckoutRecord checkoutRecord = checkoutRecords.get(checkoutRecordIds.get(i));
            checkoutData[i][0] = checkoutRecord.getMemberId();
            checkoutData[i][1] = checkoutRecord.getBookCopy().getBook().getTitle();
            checkoutData[i][2] = checkoutRecord.getBookCopy().getBook().getIsbn();
            checkoutData[i][3] = checkoutRecord.getCheckoutDate().toString();
            checkoutData[i][4] = checkoutRecord.getDueDate().toString();
            checkoutData[i][5] = members.get(checkoutRecord.getMemberId()).getFirstName();
            model.addRow(checkoutData[i]);
        }

        table.setModel(model);
    }

    public void revalidateTable(CheckoutRecord checkoutRecord) {
        model.addRow(new String[] {
                checkoutRecord.getMemberId(),
                checkoutRecord.getBookCopy().getBook().getTitle(),
                checkoutRecord.getBookCopy().getBook().getIsbn(),
                checkoutRecord.getCheckoutDate().toString(),
                checkoutRecord.getDueDate().toString()
        });

        model.fireTableDataChanged();
    }
}
