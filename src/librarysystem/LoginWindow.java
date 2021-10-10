package librarysystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;

import business.ControllerInterface;

import business.SystemController;
import ui.panels.LoginPanel;


public class LoginWindow extends JFrame implements LibWindow {
    public static final LoginWindow INSTANCE = new LoginWindow();
	
	private boolean isInitialized = false;

	public boolean isInitialized() {
		return isInitialized;
	}
	public void isInitialized(boolean val) {
		isInitialized = val;
	}
	private JTextField messageBar = new JTextField();
	public void clear() {
		messageBar.setText("");
	}
	
	/* This class is a singleton */
    private LoginWindow () {}

	public void init() {
		setSize(320, 260);

		JPanel mainPanel = new JPanel(new BorderLayout(5, 5));

		JPanel container = new JPanel(new BorderLayout(5, 5));
		JPanel top = new JPanel(new FlowLayout());

		mainPanel.add(new LoginPanel());

		container.add(top, BorderLayout.PAGE_START);
		container.add(mainPanel, BorderLayout.CENTER);

		isInitialized(true);
		setResizable(false);

		getContentPane().add(container);
		add(container);
		Util.centerFrameOnDesktop(this);
		//pack();
	}
}
