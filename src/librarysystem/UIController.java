package librarysystem;

import ui.ListItem;
import ui.panels.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UIController extends JFrame implements LibWindow {
    public final static UIController INSTANCE = new UIController();
    public JList<ListItem> linkList;
    JPanel cards;
    public static List<String> items = new ArrayList<>();
    DefaultListModel<ListItem> model;

    private boolean isInitialized = false;

    private UIController() {}

    public void init() {
        if (!isInitialized) {
            uiInit();
        }
    }

    public void uiInit() {
        setSize(Config.APP_WIDTH, Config.APP_HEIGHT);

        linkList = new JList<ListItem>();

        setSideBarList();
        createPanels();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, linkList, cards);
        splitPane.setDividerLocation(Config.DIVIDER);

        JSplitPane bottomSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPane, createNotificationBarPanel());
        bottomSplitPane.setBackground(Color.WHITE);
        bottomSplitPane.setDividerLocation(Config.APP_HEIGHT - (Config.DIVIDER - 20));

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
        for (String i : items) {
            if (i == Util.ALL_MENU[0]) {
                cards.add(createNewMemberPanel(), i);
            } else if (i == Util.ALL_MENU[1]) {
                cards.add(createAddBookPanel(), i);
            } else if (i == Util.ALL_MENU[2]) {
                cards.add(createAddBookCopyPanel(), i);
            } else if (i == Util.ALL_MENU[3]) {
                cards.add(createSearchPanel(), i);
            } else if (i == Util.ALL_MENU[4]) {
                cards.add(createViewBooksPanel(), i);
            } else if (i == Util.ALL_MENU[5]) {
                cards.add(createViewMemberIdsPanel(), i);
            } else if (i == Util.ALL_MENU[6]) {
                cards.add(createCheckoutBookPanel(), i);
            } else if (i == Util.ALL_MENU[7]) {
                cards.add(createCheckoutStatusPanel(), i);
            } else if (i == Util.ALL_MENU[8]) {
                cards.add(createSearchBookPanel(), i);
            } else if (i == Util.ALL_MENU[9]) {
                cards.add(createSearchMemberPanel(), i);
            } else if (i == Util.ALL_MENU[10]) {
                cards.add(createUpdateMemberPanel(), i);
            } else if (i == Util.ALL_MENU[11]) {
                cards.add(createLogoutPanel(), i);
            }
        }

        createListenersToSideBar();
    }

    private JPanel createLogoutPanel() {

        JPanel logoutPanel = new JPanel(new BorderLayout());
        JPanel loginLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 4));

        JLabel loginLabel = new JLabel("Logout");
        loginLabel.setForeground(Color.BLUE);
        loginLabelPanel.add(loginLabel);

        logoutPanel.add(loginLabelPanel, BorderLayout.PAGE_START);
        LogoutPanel logout = new LogoutPanel();
        logoutPanel.add(logout);

        return logoutPanel;
    }

    private JPanel createViewBooksPanel() {

        JPanel container = new JPanel(new BorderLayout());
        JLabel containerLabel = new JLabel("View books");
        containerLabel.setForeground(Color.BLUE);

        JPanel containerLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 4));
        containerLabelPanel.add(containerLabel);

        container.add(containerLabelPanel, BorderLayout.PAGE_START);
        ViewBookIds viewBooks = ViewBookIds.INSTANCE;
        viewBooks.init();
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

    private JPanel createCheckoutBookPanel() {

        JPanel container = new JPanel(new BorderLayout());
        JLabel containerLabel = new JLabel("Checkout book");
        containerLabel.setForeground(Color.BLUE);

        JPanel containerLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 4));
        containerLabelPanel.add(containerLabel);

        container.add(containerLabelPanel, BorderLayout.PAGE_START);
        CheckoutBook checkoutBook = new CheckoutBook();
        container.add(checkoutBook);

        return container;
    }

    private JPanel createCheckoutStatusPanel() {

        JPanel container = new JPanel(new BorderLayout());
        JLabel containerLabel = new JLabel("Overdue publications");
        containerLabel.setForeground(Color.BLUE);

        JPanel containerLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 4));
        containerLabelPanel.add(containerLabel);

        container.add(containerLabelPanel, BorderLayout.PAGE_START);
        CheckoutStatus checkoutStatus = CheckoutStatus.INSTANCE;
        checkoutStatus.init();
        container.add(checkoutStatus);

        return container;
    }

    private JPanel createSearchPanel() {

        JPanel container = new JPanel(new BorderLayout());
        JLabel containerLabel = new JLabel("Search member by member id");
        containerLabel.setForeground(Color.BLUE);

        JPanel containerLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 4));
        containerLabelPanel.add(containerLabel);

        container.add(containerLabelPanel, BorderLayout.PAGE_START);
        SearchMemberPanel searchPanel = SearchMemberPanel.INSTANCE;
        searchPanel.init();
        container.add(searchPanel);

        return container;
    }

    private JPanel createSearchBookPanel() {

        JPanel container = new JPanel(new BorderLayout());
        JLabel containerLabel = new JLabel("Search book by isbn");
        containerLabel.setForeground(Color.BLUE);

        JPanel containerLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 4));
        containerLabelPanel.add(containerLabel);

        container.add(containerLabelPanel, BorderLayout.PAGE_START);
        SearchBookPanel searchBookPanel = SearchBookPanel.INSTANCE;
        searchBookPanel.init();
        container.add(searchBookPanel);

        return container;
    }

    private JPanel createSearchMemberPanel() {

        JPanel container = new JPanel(new BorderLayout());
        JLabel containerLabel = new JLabel("Search checkouts by member id");
        containerLabel.setForeground(Color.BLUE);

        JPanel containerLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 4));
        containerLabelPanel.add(containerLabel);

        container.add(containerLabelPanel, BorderLayout.PAGE_START);
        SearchMemberForCheckoutPanel searchMemberPanel = SearchMemberForCheckoutPanel.INSTANCE;
        searchMemberPanel.init();
        container.add(searchMemberPanel);

        return container;
    }

    private JPanel createNewMemberPanel() {

        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Add new member");
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

        JLabel bookLabel = new JLabel("Add new book");
        bookLabel.setForeground(Color.BLUE);

        JPanel addBookPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 4));
        addBookPanel.add(bookLabel);

        bookPanel.add(addBookPanel, BorderLayout.PAGE_START);
        AddBookPanel addNewBookPanel = new AddBookPanel();
        bookPanel.add(addNewBookPanel);

        return bookPanel;
    }

    private JPanel createUpdateMemberPanel() {

        JPanel bookPanel = new JPanel(new BorderLayout());

        JLabel bookLabel = new JLabel("Update member");
        bookLabel.setForeground(Color.BLUE);

        JPanel addBookPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 4));
        addBookPanel.add(bookLabel);

        bookPanel.add(addBookPanel, BorderLayout.PAGE_START);
        UpdateMemberPanel updateMemberPanel = new UpdateMemberPanel();
        bookPanel.add(updateMemberPanel);

        return bookPanel;
    }

    private JPanel createAddBookCopyPanel() {

        JPanel bookPanel = new JPanel(new BorderLayout());

        JLabel bookLabel = new JLabel("Add copy to a book");
        bookLabel.setForeground(Color.BLUE);

        JPanel addBookPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 4));
        addBookPanel.add(bookLabel);

        bookPanel.add(addBookPanel, BorderLayout.PAGE_START);
        AddBookCopyPanel addBookCopyPanel = new AddBookCopyPanel();
        bookPanel.add(addBookCopyPanel);

        return bookPanel;
    }

    private NotificationBar createNotificationBarPanel() {

        NotificationBar nBar = new NotificationBar();
        nBar.setStatusBar(Config.APP_INTRO_TEXT);
        nBar.getStatusBar().setForeground(Color.BLUE);
        return nBar;
    }

    public void updateList(ListItem[] items) {
        DefaultListModel<ListItem> model = (DefaultListModel) linkList.getModel();
        int size = model.getSize();
        if (items != null) {
            List<Integer> indices = new ArrayList<>();
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
        for (String i : items) {
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
                        setBackground(new Color(236, 243, 245));
                    }

                } else {
                    setText("illegal item");
                }

                this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
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
