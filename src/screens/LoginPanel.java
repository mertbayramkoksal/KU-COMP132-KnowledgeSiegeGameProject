package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import user.InvalidUserException;
import user.User;
import user.UserManager;

/**
 * Represents the login screen of the game.
 * Allows users to enter their username and password to authenticate.
 * Also provides a transition button to go to the registration panel.
 *
 * Visually styled with custom button and label stylings for consistency.
 * 
 * Dependencies:
 * - {@link user.UserManager}
 * - {@link user.User}
 * - {@link user.InvalidUserException}
 * 
 * Transitions:
 * - On successful login: navigates to the "menu" panel
 * - On register button click: navigates to the "register" panel
 * 
 * UI Components:
 * - Username and password fields
 * - Login and Register buttons
 * - Feedback label for error/success messages
 * 
 */
public class LoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextField usernameTxtField;
	private JPasswordField passwordTxtField;
	private JLabel feedbackLabel;

	private MainFrame mainFrame;
	private JPanel parentPanel;
	private CardLayout cardLayout;
	private UserManager userManager;

	/**
	 * Constructs the login panel and initializes UI components.
	 *
	 * @param parent the container panel used for card layout switching
	 * @param layout the CardLayout managing the screen flow
	 * @param userManager the user manager handling authentication
	 * @param mainFrame the main frame where the game runs
	 */
	public LoginPanel(JPanel parent, CardLayout layout, UserManager userManager, MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.parentPanel = parent;
		this.cardLayout = layout;
		this.userManager = userManager;

		setLayout(null);
		setBackground(new Color(240, 242, 245));

		// Title label
		JLabel loginLBL = new JLabel("LOG IN");
		styleLabel(loginLBL, 28, true, new Color(45, 60, 80));
		loginLBL.setBounds(500, 150, 100, 35);
		add(loginLBL);

		int fieldX = 400;
		int fieldWidth = 300;
		int fieldHeight = 40;
		int labelX = 260;
		int labelWidth = 120;
		int labelHeight = 30;
		

		// Username label
		JLabel usernameLBL = new JLabel("Username:");
		styleLabel(usernameLBL, 18, false, Color.BLACK);
		usernameLBL.setBounds(labelX, 250, labelWidth, labelHeight);
		add(usernameLBL);

		// Username input
		usernameTxtField = new JTextField();
		styleTextComponent(usernameTxtField);
		usernameTxtField.setBounds(fieldX, 247, fieldWidth, fieldHeight);
		add(usernameTxtField);

		// Password label
		JLabel passwordLBL = new JLabel("Password:");
		styleLabel(passwordLBL, 18, false, Color.BLACK);
		passwordLBL.setBounds(labelX, 320, labelWidth, labelHeight);
		add(passwordLBL);

		// Password input
		passwordTxtField = new JPasswordField();
		styleTextComponent(passwordTxtField);
		passwordTxtField.setBounds(fieldX, 317, fieldWidth, fieldHeight);
		add(passwordTxtField);
		
		//Show Password Button
		JButton showPasswordBtn = new JButton("👁");
		styleShowPasswordButton(showPasswordBtn);
		showPasswordBtn.setBounds(710, 317, 35, fieldHeight);
		add(showPasswordBtn);

		int buttonWidth = 220;
		int buttonHeight = 50;

		// Login button
		JButton loginBTN = new JButton("Log In");
		styleButton(loginBTN, new Color(189, 195, 199));
		loginBTN.setBounds(440, 400, buttonWidth, buttonHeight);
		add(loginBTN);

		// Register button
		JButton registerBTN = new JButton("Register");
		styleButton(registerBTN, new Color(189, 195, 199));
		registerBTN.setBounds(440, 470, buttonWidth, buttonHeight);
		add(registerBTN);

		// Feedback message label
		feedbackLabel = new JLabel("");
		feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
		feedbackLabel.setBounds(350, 550, 400, 30);
		add(feedbackLabel);

		// Button actions
		loginBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleLogin();
			}
		});

		registerBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(parentPanel, "register");
			}
		});
		
		showPasswordBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				togglePasswordVisibility(passwordTxtField);
			}
		});

	}

	/**
	 * Attempts to authenticate the user with entered credentials.
	 * Displays error message if invalid or success message if authenticated.
	 */
	private void handleLogin() {
		String username = usernameTxtField.getText();
		String password = new String(passwordTxtField.getPassword());

		if (username.isEmpty() || password.isEmpty()) {
			feedbackLabel.setForeground(Color.RED);
			feedbackLabel.setText("Please fill in all fields!");
			return;
		}

		try {
			User user = userManager.login(username, password);
			mainFrame.setCurrentUser(user);
			cardLayout.show(parentPanel, "menu");
		} 
		catch (InvalidUserException e) {
			feedbackLabel.setForeground(Color.RED);
			feedbackLabel.setText(e.getMessage());
		}
	}
	
	/**
	 * Toggles password visibility for the given password field.
	 */
	private void togglePasswordVisibility(JPasswordField passwordField) {
		if (passwordField.getEchoChar() == 0) {
			passwordField.setEchoChar('•'); // Hide password
		} else {
			passwordField.setEchoChar((char) 0); // Show password
		}
	}

	/**
	 * Applies a consistent visual style to JButton components.
	 *
	 * @param button the JButton to style
	 * @param bgColor background color for the button
	 */
	private void styleButton(JButton button, Color bgColor) {
		button.setFont(new Font("Arial", Font.BOLD, 20));
		button.setForeground(Color.BLACK);
		button.setBackground(bgColor);
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		button.setOpaque(true);
	}
	
	/**
	 * Applies styling to show password buttons.
	 */
	private void styleShowPasswordButton(JButton button) {
		button.setFont(new Font("Arial", Font.PLAIN, 14));
		button.setBackground(new Color(220, 220, 220));
		button.setForeground(Color.BLACK);
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		button.setOpaque(true);
	}
	
	 /**
     * Applies consistent styling to text components (JTextField, JPasswordField).
     */
    private void styleTextComponent(JComponent component) {
        component.setFont(new Font("Arial", Font.PLAIN, 16));
        component.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
    }

	/**
	 * Applies consistent visual styling to JLabel components.
	 *
	 * @param label the JLabel to style
	 * @param fontSize the font size
	 * @param bold whether the font should be bold
	 * @param color the foreground text color
	 */
	private void styleLabel(JLabel label, int fontSize, boolean bold, Color color) {
		label.setFont(new Font("Arial", bold ? Font.BOLD : Font.PLAIN, fontSize));
		label.setForeground(color);
	}
}
