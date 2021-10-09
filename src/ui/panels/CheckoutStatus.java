package ui.panels;

import business.*;
import business.Exceptions.CheckoutRecordException;
import librarysystem.Config;
import ui.BtnEventListener;
import ui.Util;
import ui.elements.LJButton;
import ui.elements.LJTextField;
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
            new LJTextField(),
    };
    private Component[] components;
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel model;
    private String isbn;

    private final String[] DEFAULT_COLUMN_HEADERS = { "Member Id", "Title", "ISBN", "Checkout date", "Due date", "Cpy no.", "Member" };
    private static final int SCREEN_WIDTH = Config.APP_WIDTH - Config.DIVIDER;
    private static final int SCREEN_HEIGHT = Config.APP_HEIGHT;
    private static final int TABLE_WIDTH = (int) (0.75 * SCREEN_WIDTH);
    private static final int DEFAULT_TABLE_HEIGHT = (int) (0.75 * SCREEN_HEIGHT);

    private CheckoutStatus() {}

    public void init() {
        uiInit();
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable();
        createHeaders();
        createTableAndTablePane();
        this.add(scrollPane);
    }

    public void uiInit() {
        JComponent container = new JPanel(new BorderLayout(5, 5));
        JComponent form = new JPanel(new BorderLayout());
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 4));

        LJButton searchBookBtn = new LJButton("Search");
        LJButton showAllBtn = new LJButton("Show all");
        showAllBtn.setWidth(64);
        showAllBtn.setBackground(Color.LIGHT_GRAY);
        searchBookBtn.setWidth(82);

        showALlEventListener(showAllBtn);
        addEventListener(searchBookBtn);

        String[] labels = { "Book isbn" };

        JComponent labelsAndFields = Util.getTwoColumnLayout(labels, jComponents);
        components = labelsAndFields.getComponents();

        form.add(labelsAndFields, BorderLayout.WEST);
        form.add(searchBookBtn, BorderLayout.CENTER);
        form.add(showAllBtn, BorderLayout.EAST);

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

                isbn = values[0].trim();
                RuleSet checkoutStatus = RuleSetFactory.getRuleSet(this);
                checkoutStatus.applyRules(this);

                List<CheckoutRecord> checkoutRecordList = ci.allCheckoutRecordsByIsbn(isbn);
                if (checkoutRecordList.isEmpty()) throw new CheckoutRecordException("Data not found.");
                loadData(checkoutRecordList);
                displayInfo("Data retrieved successfully");
            } catch(Exception e) {
                displayError(e.getMessage());
            }
        });
    }

    public void showALlEventListener(JButton btn) {
        btn.addActionListener(evt -> {
            try {
                loadData(ci.allCheckoutRecords(), ci.allCheckoutRecordIds());
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

        model.setRowCount(0);

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
                checkoutData[index][5] = String.valueOf(checkoutRecord.getCheckoutRecordEntries().get(j).getBookCopy().getCopyNum());
                checkoutData[index][6] = checkoutRecord.getLibraryMember().getFirstName();
                model.addRow(checkoutData[i]);
                index++;
            }
        }

        table.setModel(model);
    }

    private void loadData(HashMap<String, CheckoutRecord> checkoutRecords, List<String> checkoutRecordIds) {

        model.setRowCount(0);
        String[][] checkoutData = new String[checkoutRecords.size()][DEFAULT_COLUMN_HEADERS.length];

        int index = 0;
        for(int i = 0 ; i < checkoutRecords.size(); i++) {
            CheckoutRecord checkoutRecord = checkoutRecords.get(checkoutRecordIds.get(i));
            for (int j = 0; j < checkoutRecord.getCheckoutRecordEntries().size(); j++) {
                checkoutData[index][0] = checkoutRecord.getLibraryMember().getMemberId();
                checkoutData[index][1] = checkoutRecord.getCheckoutRecordEntries().get(j).getBookCopy().getBook().getTitle();
                checkoutData[index][2] = checkoutRecord.getCheckoutRecordEntries().get(j).getBookCopy().getBook().getIsbn();
                checkoutData[index][3] = checkoutRecord.getCheckoutRecordEntries().get(j).getCheckoutDate().toString();
                checkoutData[index][4] = checkoutRecord.getCheckoutRecordEntries().get(j).getDueDate().toString();
                checkoutData[index][5] = "-";
                checkoutData[index][6] = checkoutRecord.getLibraryMember().getFirstName();
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
