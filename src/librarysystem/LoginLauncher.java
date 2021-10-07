package librarysystem;

import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;

public class LoginLauncher extends JFrame implements LibWindow {
    ControllerInterface ci = new SystemController();
    public final static LoginLauncher INSTANCE = new LoginLauncher();
    private boolean isInitialized = false;

    private static LibWindow[] allWindows = {
            LoginWindow.INSTANCE,
            UIController.INSTANCE,
    };

    public static void hideAllWindows() {

        for(LibWindow frame: allWindows) {
            frame.setVisible(false);
        }
    }


    private LoginLauncher() {}

    public void init() {
        setSize(220,300);
        isInitialized(true);
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        isInitialized = val;

    }
}
