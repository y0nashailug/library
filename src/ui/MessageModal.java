package ui;

import javax.swing.*;

public class MessageModal {

    public static class InnerMessageModalFrame extends JFrame {

        public void showMessage(String message, String messageType) {

            int err = 1;
            if(messageType.equals("Error")){
                err = JOptionPane.ERROR_MESSAGE;
            }else if(messageType.equals("Info")){
                err = JOptionPane.INFORMATION_MESSAGE;
            } else {
                err = JOptionPane.WARNING_MESSAGE;
            }

            JOptionPane.showMessageDialog(this, message, messageType, err);
        }
    }
}
