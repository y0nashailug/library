package ui.panels;

import business.Book;
import business.CheckoutRecord;
import business.Exceptions.BookException;
import business.Exceptions.CheckoutRecordException;
import business.Exceptions.LibrarySystemException;
import business.LibraryMember;
import business.SystemController;
import librarysystem.Config;
import ui.BtnEventListener;
import ui.Util;
import ui.elements.LJButton;
import ui.elements.LJTextField;
import ui.rulesets.RuleException;
import ui.rulesets.RuleSet;
import ui.rulesets.RuleSetFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class CheckoutBook extends JPanel implements MessageableWindow, BtnEventListener {

    private JComponent[] jComponents = {
            new LJTextField(),
            new LJTextField(),
    };
    private Component[] components;
    private String memberId;
    private String isbn;

    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel model;

    private final String[] DEFAULT_COLUMN_HEADERS = { "Title", "ISBN", "Checkout date", "Due date" };
    private static final int SCREEN_WIDTH = Config.APP_WIDTH - Config.DIVIDER;
    private static final int SCREEN_HEIGHT = Config.APP_HEIGHT;
    private static final int TABLE_WIDTH = (int) (0.75 * SCREEN_WIDTH);
    private static final int DEFAULT_TABLE_HEIGHT = (int) (0.75 * SCREEN_HEIGHT);


    public CheckoutBook() {
        init();
    }

    public void init() {
        uiInit();
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable();
        table.setModel(model);
        createHeaders();
        createTableAndTablePane();
        this.add(scrollPane);
    }

    public void uiInit() {
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

    private void createTableAndTablePane() {
        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(TABLE_WIDTH, DEFAULT_TABLE_HEIGHT));
        scrollPane.getViewport().add(table);
    }

    public void createHeaders() {
        model.setColumnIdentifiers(DEFAULT_COLUMN_HEADERS);
    }
    public void loadData(CheckoutRecord checkoutRecord) {
        model.addRow(new String[] {
                checkoutRecord.getCheckoutRecordEntries().get(0).getBookCopy().getBook().getTitle(),
                checkoutRecord.getCheckoutRecordEntries().get(0).getBookCopy().getBook().getIsbn(),
                checkoutRecord.getCheckoutRecordEntries().get(0).getCheckoutDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                checkoutRecord.getCheckoutRecordEntries().get(0).getDueDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
        });

        model.fireTableDataChanged();
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

                CheckoutRecord checkoutRecord  = systemController.addCheckoutRecord(member, book);
                if (checkoutRecord == null) throw new CheckoutRecordException("Checkout record was not found.");

                loadData(checkoutRecord);
                //CheckoutStatus.INSTANCE.revalidateTable(checkoutRecord);
                displayInfo("Checkout record saved successfully");
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
