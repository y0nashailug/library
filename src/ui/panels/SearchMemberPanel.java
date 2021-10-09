package ui.panels;

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

public class SearchMemberPanel extends JPanel implements MessageableWindow, BtnEventListener {

    public static SearchMemberPanel INSTANCE = new SearchMemberPanel();
    private JComponent[] jComponents = {
            new LJTextField(),
    };
    private String memberId;
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel model;
    private Component[] components;
    private final String[] DEFAULT_COLUMN_HEADERS = { "Member Id", "First name", "Last name" };
    private static final int SCREEN_WIDTH = Config.APP_WIDTH - Config.DIVIDER;
    private static final int SCREEN_HEIGHT = Config.APP_HEIGHT;
    private static final int TABLE_WIDTH = (int) (0.75 * SCREEN_WIDTH);
    private static final int DEFAULT_TABLE_HEIGHT = (int) (0.75 * SCREEN_HEIGHT);

    private SearchMemberPanel() {}

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

        JButton addBookCopy = new LJButton("Search");
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

    private void loadData(LibraryMember member) {
        model.setRowCount(0);
        model.addRow(new String[] { member.getMemberId(), member.getFirstName(), member.getLastName() });
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
                RuleSet searchMemberPanel = RuleSetFactory.getRuleSet(this);
                searchMemberPanel.applyRules(this);

                SystemController systemController = new SystemController();
                LibraryMember libraryMember = systemController.getMember(memberId);
                if (libraryMember == null) throw new LibrarySystemException("Member was not found with id: " + memberId);
                loadData(libraryMember);

                displayInfo("Member found with id: " + memberId);
            } catch(LibrarySystemException | RuleException e) {
                displayError(e.getMessage());
            }
        });
    }

    public Component[] getComponents() {
        return components;
    }

    public String getMemberId() { return memberId; }

    @Override
    public void updateData() {

    }
}
