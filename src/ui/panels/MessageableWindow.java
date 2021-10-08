package ui.panels;

import librarysystem.Util;

public interface MessageableWindow {
    default void displayError(String msg) {
        NotificationBar.statusBar.setForeground(Util.ERROR_MESSAGE_COLOR);
        NotificationBar.statusBar.setText(msg);
    }

    default void displayInfo(String msg) {
        NotificationBar.statusBar.setForeground(Util.INFO_MESSAGE_COLOR);
        NotificationBar.statusBar.setText(msg);
    }

    void updateData();
}
