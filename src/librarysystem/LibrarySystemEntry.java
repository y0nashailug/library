package librarysystem;

import ui.ListItem;
import ui.panels.AddBookPanel;
import ui.panels.LoginPanel;
import ui.panels.NewMemberPanel;
import ui.panels.NotificationBar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LibrarySystemEntry extends JFrame {
    String[] links;
    public JList<ListItem> linkList;
    JPanel cards;
    String[] items = { "Login", "View titles", "Add book" };
    ListItem item1 = new ListItem(items[0], true);
    ListItem item2 = new ListItem(items[1], false);
    ListItem item3 = new ListItem(items[2], false);

    int height = 420;
    int width = 500;

    public LibrarySystemEntry() {

        setSize(width, height);

        linkList = new JList<ListItem>();

        setSideBarList();
        createPanels();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, linkList, cards);
        splitPane.setDividerLocation(100);

        JSplitPane bottomSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane, createNotificationBarPanel());
        bottomSplitPane.setBackground(Color.WHITE);
        bottomSplitPane.setDividerLocation(height - 100);

        add(bottomSplitPane, BorderLayout.CENTER);
    }

    public void createPanels() {

        cards = new JPanel(new CardLayout());

        cards.add(createLoginPanel(), items[0]);
        cards.add(createNewMemberPanel(), items[1]);
        cards.add(createAddBookPanel(), items[2]);

        createListenersToSideBar();
    }

    private JPanel createLoginPanel() {

        JPanel loginPanel = new JPanel(new BorderLayout());
        JPanel loginLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 4));

        JLabel loginLabel = new JLabel("Login");
        loginLabel.setForeground(Color.BLUE);
        loginLabelPanel.add(loginLabel);

        loginPanel.add(loginLabelPanel, BorderLayout.PAGE_START);
        LoginPanel login = new LoginPanel();
        //Control.INSTANCE.login = login;
        loginPanel.add(login);

        return loginPanel;
    }

    private JPanel createNewMemberPanel() {

        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("View titles");
        titleLabel.setForeground(Color.BLUE);

        JPanel titleLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 4));
        titleLabelPanel.add(titleLabel);

        titlePanel.add(titleLabelPanel, BorderLayout.PAGE_START);
        NewMemberPanel newMemberPanel = new NewMemberPanel();
        //Control.INSTANCE.bookView = bookView;
        titlePanel.add(newMemberPanel);

        return titlePanel;
    }

    private JPanel createAddBookPanel() {

        JPanel bookPanel = new JPanel(new BorderLayout());

        JLabel bookLabel = new JLabel("Add book");
        bookLabel.setForeground(Color.BLUE);

        JPanel addBookPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 4));
        addBookPanel.add(bookLabel);

        bookPanel.add(addBookPanel, BorderLayout.PAGE_START);
        AddBookPanel addNewBookPanel = new AddBookPanel();
        //Control.INSTANCE.addBook = addbook;
        bookPanel.add(addNewBookPanel);

        return bookPanel;
    }

    private NotificationBar createNotificationBarPanel() {

        NotificationBar nBar = new NotificationBar();

        //Control.INSTANCE.nBar = nBar;
        //Control.INSTANCE.nBar.setMessage("Welcome to the Book Club!");
        //Control.INSTANCE.nBar.getMessage().setForeground(Color.BLUE);

        return nBar;
    }

    public void updateList(ListItem[] items) {
        DefaultListModel<ListItem> model = (DefaultListModel) linkList.getModel();
        int size = model.getSize();
        if (items != null) {
            java.util.List<Integer> indices = new ArrayList<>();
            for (ListItem item : items) {
                int index = model.indexOf(item);
                indices.add(index);
                ListItem next = (ListItem) model.get(index);
                next.setHighlight(true);
                setForeground(Color.GRAY);
            }
            for (int i = 0; i < size; ++i) {
                if (!indices.contains(i)) {
                    ListItem next = (ListItem) model.get(i);
                    next.setHighlight(false);
                    setForeground(Color.GRAY);
                }
            }
        } else {
            for (int i = 0; i < size; ++i) {
                ListItem next = (ListItem) model.get(i);
                next.setHighlight(true);
                setForeground(Color.GRAY);
            }
        }
    }

    public void setSideBarList(){

        DefaultListModel<ListItem> model = new DefaultListModel<>();
        model.addElement(item1);
        model.addElement(item2);
        model.addElement(item3);

        linkList = new JList<ListItem>(model);

        linkList.setCellRenderer(new DefaultListCellRenderer() {

            @SuppressWarnings("rawtypes")
            @Override
            public Component getListCellRendererComponent(JList list,
                                                          Object value,
                                                          int index,
                                                          boolean isSelected,
                                                          boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list,
                        value, index, isSelected, cellHasFocus);
                if (value instanceof ListItem) {
                    ListItem nextItem = (ListItem) value;
                    setText(nextItem.getItemName());
                    if (nextItem.highlight()) {
                        setForeground(Util.LINK_AVAILABLE);
                    } else {
                        setForeground(Util.LINK_NOT_AVAILABLE);
                    }
                    if (isSelected) {
                        setForeground(Color.BLACK);
                        setBackground(new Color(236,243,245));
                    }
                } else {
                    setText("illegal item");
                }
                return c;
            }

        });

    }

    public void createListenersToSideBar() {

        linkList.addListSelectionListener(event -> {

            String value = linkList.getSelectedValue().getItemName();
            boolean allowed = linkList.getSelectedValue().highlight();

            CardLayout cl = (CardLayout) (cards.getLayout());

            if (!allowed) {
                value = item1.getItemName();
                linkList.setSelectedIndex(0);
            }
            cl.show(cards, value);
        });

        //Control.INSTANCE.mainFrame = this;
    }
}
