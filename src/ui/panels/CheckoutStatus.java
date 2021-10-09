package ui.panels;

import business.*;
import business.Exceptions.CheckoutRecordException;
import ui.BtnEventListener;
import ui.Util;
import ui.rulesets.RuleSet;
import ui.rulesets.RuleSetFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class CheckoutStatus extends JPanel implements MessageableWindow, BtnEventListener {

    ControllerInterface ci = new SystemController();
    public final static CheckoutStatus INSTANCE = new CheckoutStatus();
    private JComponent[] jComponents = {
            new JTextField(15),
    };
    private Component[] components;
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel model;
    private String isbn;

    private final String[] DEFAULT_COLUMN_HEADERS = { "Member Id", "Title", "ISBN", "Checkout date", "Due date", "Member" };
    private static final int SCREEN_WIDTH = 420;
    private static final int SCREEN_HEIGHT = 420;
    private static final int TABLE_WIDTH = (int) (0.75 * SCREEN_WIDTH);
    private static final int DEFAULT_TABLE_HEIGHT = (int) (0.75 * SCREEN_HEIGHT);

    private CheckoutStatus() {}

    public void init() {
        uiInit();
        model = new DefaultTableModel();
        table = new JTable();
        createHeaders();
        //loadData();
        createTableAndTablePane();
        this.add(scrollPane);
    }

    public void uiInit() {
        JComponent form = new JPanel(new BorderLayout(5,5));
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 4));

        JButton addBookCopy = new JButton("Search");
        addEventListener(addBookCopy);

        String[] labels = { "Book isbn" };

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

                isbn = values[0].trim();
                RuleSet checkoutStatus = RuleSetFactory.getRuleSet(this);
                checkoutStatus.applyRules(this);

                String isbn = values[0].trim();
                List<CheckoutRecord> checkoutRecordList = ci.allCheckoutRecordsByIsbn(isbn);
                System.out.println(checkoutRecordList);
                if (checkoutRecordList.isEmpty()) throw new CheckoutRecordException("Data not found.");
                loadData(checkoutRecordList);
                displayInfo("Data retrieved successfully");
            } catch(Exception e) {
                displayError(e.getMessage());
            }
        });
    }

    private void createTableAndTablePane() {
        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(TABLE_WIDTH, DEFAULT_TABLE_HEIGHT));
        scrollPane.getViewport().add(table);
    }

    public void createHeaders() {
        model.setColumnIdentifiers(DEFAULT_COLUMN_HEADERS);
    }

    private void loadData(List<CheckoutRecord> checkoutRecords) {

        String[][] checkoutData = new String[checkoutRecords.size()][DEFAULT_COLUMN_HEADERS.length];
        int index = 0;
        for(int i = 0 ; i < checkoutRecords.size(); i++) {
            CheckoutRecord checkoutRecord = checkoutRecords.get(i);
            for (int j = 0; j < checkoutRecord.getCheckoutRecordEntries().size(); j++) {
                checkoutData[index][0] = checkoutRecord.getLibraryMember().getMemberId();
                checkoutData[index][1] = checkoutRecord.getCheckoutRecordEntries().get(j).getBookCopy().getBook().getTitle();
                checkoutData[index][2] = checkoutRecord.getCheckoutRecordEntries().get(j).getBookCopy().getBook().getIsbn();
                checkoutData[index][3] = checkoutRecord.getCheckoutRecordEntries().get(j).getCheckoutDate().toString();
                checkoutData[index][4] = checkoutRecord.getCheckoutRecordEntries().get(j).getDueDate().toString();
                checkoutData[index][5] = checkoutRecord.getLibraryMember().getFirstName();
                model.addRow(checkoutData[i]);
                index++;
            }
        }

        table.setModel(model);
    }

    private void loadData() {

        HashMap<String, CheckoutRecord> checkoutRecords = ci.allCheckoutRecords();
        String[][] checkoutData = new String[checkoutRecords.size()][DEFAULT_COLUMN_HEADERS.length];
        List<String> checkoutRecordIds = ci.allCheckoutRecordIds();

        int index = 0;
        for(int i = 0 ; i < checkoutRecords.size(); i++) {
            CheckoutRecord checkoutRecord = checkoutRecords.get(checkoutRecordIds.get(i));
            for (int j = 0; j < checkoutRecord.getCheckoutRecordEntries().size(); j++) {
                checkoutData[index][0] = checkoutRecord.getLibraryMember().getMemberId();
                checkoutData[index][1] = checkoutRecord.getCheckoutRecordEntries().get(j).getBookCopy().getBook().getTitle();
                checkoutData[index][2] = checkoutRecord.getCheckoutRecordEntries().get(j).getBookCopy().getBook().getIsbn();
                checkoutData[index][3] = checkoutRecord.getCheckoutRecordEntries().get(j).getCheckoutDate().toString();
                checkoutData[index][4] = checkoutRecord.getCheckoutRecordEntries().get(j).getDueDate().toString();
                checkoutData[index][5] = checkoutRecord.getLibraryMember().getFirstName();
                model.addRow(checkoutData[i]);
                index++;
            }
        }

        table.setModel(model);
    }

    public void revalidateTable(CheckoutRecord checkoutRecord) {
        model.addRow(new String[] {
                checkoutRecord.getLibraryMember().getMemberId(),
                checkoutRecord.getCheckoutRecordEntries().get(0).getBookCopy().getBook().getTitle(),
                checkoutRecord.getCheckoutRecordEntries().get(0).getBookCopy().getBook().getIsbn(),
                checkoutRecord.getCheckoutRecordEntries().get(0).getCheckoutDate().toString(),
                checkoutRecord.getCheckoutRecordEntries().get(0).getDueDate().toString(),
                checkoutRecord.getLibraryMember().getFirstName()
        });

        model.fireTableDataChanged();
    }

    public String getIsbn() { return isbn; }

    @Override
    public void updateData() {

    }
}
