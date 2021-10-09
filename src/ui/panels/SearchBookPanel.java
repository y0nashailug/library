package ui.panels;

import business.Book;
import business.Exceptions.BookException;
import business.SystemController;
import ui.BtnEventListener;
import ui.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SearchBookPanel extends JPanel implements MessageableWindow, BtnEventListener {

    public static SearchBookPanel INSTANCE = new SearchBookPanel();
    private JComponent[] jComponents = {
            new JTextField(15),
    };
    private String isbn;
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel model;
    private Component[] components;
    private final String[] DEFAULT_COLUMN_HEADERS = { "Isbn", "Title", "Avail", "Next avail" };
    private static final int SCREEN_WIDTH = 420;
    private static final int SCREEN_HEIGHT = 420;
    private static final int TABLE_WIDTH = (int) (0.75 * SCREEN_WIDTH);
    private static final int DEFAULT_TABLE_HEIGHT = (int) (0.75 * SCREEN_HEIGHT);

    private SearchBookPanel() {}

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

        String[] labels = { "ISBN" };

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

    private void loadData(Book book) {
        if (model.getRowCount() >= 1) model.removeRow(0);
        model.addRow(new String[] {
                book.getIsbn(),
                book.getTitle(),
                book.isAvailable() ? "Yes": "No",
                String.valueOf(book.getNextAvailableCopy().getCopyNum()),
        });
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

                SystemController systemController = new SystemController();
                Book book = systemController.getBook(isbn);
                if (book == null) throw new BookException("Book was not found.");

                loadData(book);

                displayInfo("Book found with isbn: " + isbn);
            } catch(BookException e) {
                displayError(e.getMessage());
            }
        });
    }

    public Component[] getComponents() {
        return components;
    }

    public String getIsbn() { return isbn; }
    @Override
    public void updateData() {

    }
}
