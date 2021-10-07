package ui.panels;

import librarysystem.Util;

public interface MessageableWindow {
    public default void displayError(String msg) {
        NotificationBar.statusBar.setForeground(Util.ERROR_MESSAGE_COLOR);
        NotificationBar.statusBar.setText(msg);
    }

    public default void displayInfo(String msg) {
        NotificationBar.statusBar.setForeground(Util.INFO_MESSAGE_COLOR);
        NotificationBar.statusBar.setText(msg);
    }

    public void updateData();
}
