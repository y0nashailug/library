package ui.panels;

import business.*;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

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

    private final String[] DEFAULT_COLUMN_HEADERS = { "Title", "ISBN", "Checkout date", "Due date", "Cpy", "Member", "Status" };
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
            @Override
            public Class<?> getColumnClass(int column) {
                if (getRowCount() > 0) {
                    Object value = getValueAt(0, column);
                    if (value != null) {
                        return getValueAt(0, column).getClass();
                    }
                }

                return super.getColumnClass(column);
            }
        };

        table = new JTable();
        table.setRowHeight(24);
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

                if (loadData() == 0) throw new Exception("No data to show.");
                displayInfo("Data retrieved successfully");
            } catch(Exception e) {
                displayError(e.getMessage());
            }
        });
    }

    public void showALlEventListener(JButton btn) {
        btn.addActionListener(evt -> {
            try {
                if (loadAllData() == 0) throw new Exception("No data to show.");
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

    private int loadData() {
        model.setRowCount(0);
        HashMap<String, LibraryMember> members = ci.getMembers();
        String currDirectory = System.getProperty("user.dir");
        ImageIcon icon = new ImageIcon(currDirectory+"\\src\\ui\\panels\\rec.png");

        for (String key: members.keySet()) {
            LibraryMember libraryMember = members.get(key);
            for (CheckoutRecordEntry checkoutRecordEntry: libraryMember.getCheckoutRecord().getCheckoutRecordEntries()) {
                Book book = checkoutRecordEntry.getBookCopy().getBook();
                if (book.getIsbn().equals(isbn) && checkoutRecordEntry.getDueDate().isBefore(LocalDate.now())) {
                    model.addRow(new Object[] {
                            checkoutRecordEntry.getBookCopy().getBook().getTitle(),
                            checkoutRecordEntry.getBookCopy().getBook().getIsbn(),
                            checkoutRecordEntry.getCheckoutDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                            checkoutRecordEntry.getDueDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                            String.valueOf(checkoutRecordEntry.getBookCopy().getCopyNum()),
                            libraryMember.getFirstName(),
                            icon,
                    });
                }
            }
        }

        table.setModel(model);

        return table.getRowCount();
    }

    private int loadAllData() {
        model.setRowCount(0);
        HashMap<String, LibraryMember> members = ci.getMembers();

        for (String key: members.keySet()) {
            LibraryMember libraryMember = members.get(key);
            for (CheckoutRecordEntry checkoutRecordEntry: libraryMember.getCheckoutRecord().getCheckoutRecordEntries()) {
                model.addRow(new String[] {
                    checkoutRecordEntry.getBookCopy().getBook().getTitle(),
                    checkoutRecordEntry.getBookCopy().getBook().getIsbn(),
                    checkoutRecordEntry.getCheckoutDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                    checkoutRecordEntry.getDueDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                    String.valueOf(checkoutRecordEntry.getBookCopy().getCopyNum()),
                    libraryMember.getFirstName()
                });
            }
        }

        table.setModel(model);

        return table.getRowCount();
    }

    public void revalidateTable(CheckoutRecord checkoutRecord) {
        model.addRow(new String[] {
                checkoutRecord.getCheckoutRecordEntries().get(0).getBookCopy().getBook().getTitle(),
                checkoutRecord.getCheckoutRecordEntries().get(0).getBookCopy().getBook().getIsbn(),
                checkoutRecord.getCheckoutRecordEntries().get(0).getCheckoutDate().toString(),
                checkoutRecord.getCheckoutRecordEntries().get(0).getDueDate().toString(),
                "-"
        });

        model.fireTableDataChanged();
    }

    public String getIsbn() { return isbn; }

    @Override
    public void updateData() {

    }
}
