package librarysystem;

import ui.ListItem;
import ui.panels.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UIController extends JFrame implements LibWindow {
    public final static UIController INSTANCE = new UIController();
    String[] links;
    public JList<ListItem> linkList;
    JPanel cards;
    public static List<String> items = new ArrayList<>();
    DefaultListModel<ListItem> model;

    private boolean isInitialized = false;

    int height = 420;
    int width = 500;

    private UIController() {}

    public void init() {

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
        Util.centerFrameOnDesktop(this);

        isInitialized(true);
    }

    @Override
    public boolean isInitialized() {
        return false;
    }

    @Override
    public void isInitialized(boolean val) {
        isInitialized = val;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public void createPanels() {

        cards = new JPanel(new CardLayout());
        for (String i: items) {
            System.out.println(i);
            if (i == "Add member") {
                cards.add(createNewMemberPanel(), i);
            } else if (i == "Add book") {
                cards.add(createAddBookPanel(), i);
            } else if (i == "All book ids") {
                cards.add(createViewBooksPanel(), i);
            } else if (i == "All member ids") {
                cards.add(createViewMemberIdsPanel(), i);
            }
        }

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

    private JPanel createViewBooksPanel() {

        JPanel container = new JPanel(new BorderLayout());
        JLabel containerLabel = new JLabel("View books");
        containerLabel.setForeground(Color.BLUE);

        JPanel containerLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 4));
        containerLabelPanel.add(containerLabel);

        container.add(containerLabelPanel, BorderLayout.PAGE_START);
        ViewBookIds viewBooks = new ViewBookIds();
        container.add(viewBooks);

        return container;
    }

    private JPanel createViewMemberIdsPanel() {

        JPanel container = new JPanel(new BorderLayout());
        JLabel containerLabel = new JLabel("View member ids");
        containerLabel.setForeground(Color.BLUE);

        JPanel containerLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 4));
        containerLabelPanel.add(containerLabel);

        container.add(containerLabelPanel, BorderLayout.PAGE_START);
        ViewMemberIds viewMemberIds = ViewMemberIds.INSTANCE;
        viewMemberIds.init();
        container.add(viewMemberIds);

        return container;
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

        nBar.setStatusbar("Welcome!");
        nBar.getStatusbar().setForeground(Color.BLUE);

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

    public void addElementsToModel() {
        for(String i: items) {
            model.addElement(new ListItem(i, true));
        }
    }
    public void setSideBarList() {

        model = new DefaultListModel<>();
        addElementsToModel();
        linkList = new JList<ListItem>(model);

        linkList.setCellRenderer(new DefaultListCellRenderer() {
            @SuppressWarnings("rawtypes")
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
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
                value = model.get(0).getItemName();
                linkList.setSelectedIndex(0);
            }
            cl.show(cards, value);
        });
    }

    public static void main(String[] args) {
        UIController ls = new UIController();
    }
}