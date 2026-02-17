package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import user.InvalidUserException;
import user.UserManager;

/**
 * The {@code RegisterPanel} class represents the registration screen of the application.
 * It allows users to create a new account by entering a username, password, and selecting an avatar.
 *
 * This panel includes form validation, visual feedback for input errors, and styled UI components.
 *
 * Key Features:
 * -Username and password input fields
 * -Confirm password validation
 * -Avatar selection using image-based radio buttons
 * -Styled buttons and labels via helper methods
 * -Navigation back to the login screen
 *
 * Usage:
 * -This panel is typically used within a CardLayout-driven main frame that switches between login, register, and menu screens.
 *
 */
public class RegisterPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextField usernameTxtField;
    private JPasswordField passwordTxtField;
    private JPasswordField confirmPasswordTxtField;
    private JLabel feedbackLabel;

    private JPanel parentPanel;
    private CardLayout cardLayout;
    private UserManager userManager;

    private String selectedAvatarPath = null;

    /**
     * Constructs the registration panel with all necessary UI components and layout.
     *
     * @param parent the container panel that holds this and other screens
     * @param layout the CardLayout that manages screen transitions
     * @param userManager the UserManager used for registering new users
     */
    public RegisterPanel(JPanel parent, CardLayout layout, UserManager userManager) {
        this.parentPanel = parent;
        this.cardLayout = layout;
        this.userManager = userManager;

        setLayout(null);
        setBackground(new Color(245, 245, 250));

        int labelWidth = 150;
        int fieldWidth = 300;
        
        
        // Title
        JLabel registerLBL = new JLabel("REGISTER");
        styleLabel(registerLBL, 32, true, new Color(33, 47, 61));
        registerLBL.setBounds(460, 10, fieldWidth, 40);
        add(registerLBL);


        // Username label
        JLabel usernameLBL = new JLabel("Username:");
        styleLabel(usernameLBL, 18, false, Color.BLACK);
        usernameLBL.setBounds(250, 110, labelWidth, 30);
        add(usernameLBL);

        // Username input
        usernameTxtField = new JTextField();
        styleTextComponent(usernameTxtField);
        usernameTxtField.setBounds(400, 110, fieldWidth, 35);
        add(usernameTxtField);


        // Password label
        JLabel passwordLBL = new JLabel("Password:");
        styleLabel(passwordLBL, 18, false, Color.BLACK);
        passwordLBL.setBounds(250, 160, labelWidth, 30);
        add(passwordLBL);

        // Password input
        passwordTxtField = new JPasswordField();
        styleTextComponent(passwordTxtField);
        passwordTxtField.setBounds(400, 160, fieldWidth, 35);
        add(passwordTxtField);
        
        // Show Password button
        JButton showPasswordBtn = new JButton("👁");
        styleShowPasswordButton(showPasswordBtn);
        showPasswordBtn.setBounds(710, 160, 35, 35);
        add(showPasswordBtn);

        // Confirm Password
        JLabel confirmPasswordLBL = new JLabel("Confirm Password:");
        styleLabel(confirmPasswordLBL, 18, false, Color.BLACK);
        confirmPasswordLBL.setBounds(230, 210, labelWidth+20, 30);
        add(confirmPasswordLBL);

        // Confirm Password input
        confirmPasswordTxtField = new JPasswordField();
        styleTextComponent(confirmPasswordTxtField);
        confirmPasswordTxtField.setBounds(400, 210, fieldWidth, 35);
        add(confirmPasswordTxtField);
        
        // Show Confirm Password button
        JButton showConfirmPasswordBtn = new JButton("👁");
        styleShowPasswordButton(showConfirmPasswordBtn);
        showConfirmPasswordBtn.setBounds(710, 210, 35, 35);
        add(showConfirmPasswordBtn);

        // Register Button
        JButton registerBtn = new JButton("Register");
        styleButton(registerBtn, new Color(189, 195, 199));
        registerBtn.setBounds(400, 270, fieldWidth, 40);
        add(registerBtn);

        // Back Button
        JButton backBtn = new JButton("Back to Log In");
        styleButton(backBtn, new Color(189, 195, 199));
        backBtn.setBounds(400, 330, fieldWidth, 40);
        add(backBtn);

        // Feedback Label
        feedbackLabel = new JLabel("");
        feedbackLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        feedbackLabel.setHorizontalAlignment(SwingConstants.CENTER);
        feedbackLabel.setBounds(300, 385, 500, 25);
        add(feedbackLabel);

        // Avatar Section
        JLabel avatarLBL = new JLabel("Choose Your Avatar:");
        styleLabel(avatarLBL, 18, true, new Color(33, 47, 61));
        avatarLBL.setBounds(460, 430, fieldWidth, 25);
        add(avatarLBL);

        ButtonGroup avatarGroup = new ButtonGroup();
        JRadioButton avatar1 = createAvatarRadioButton("assets/players/player1.png", 460);
        JRadioButton avatar2 = createAvatarRadioButton("assets/players/player2.png", 550);
        avatarGroup.add(avatar1);
        avatarGroup.add(avatar2);
        add(avatar1);
        add(avatar2);

        // Button actions
        showPasswordBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				togglePasswordVisibility(passwordTxtField);
			}
		});
        
        showConfirmPasswordBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				togglePasswordVisibility(confirmPasswordTxtField);
			}
		});
        
        registerBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleRegister();
			}
		});
        
        backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(parentPanel, "login");
			}
		});
    }

    /**
     * Styles a JLabel with specified font settings.
     */
    private void styleLabel(JLabel label, int fontSize, boolean bold, Color color) {
        label.setFont(new Font("Arial", bold ? Font.BOLD : Font.PLAIN, fontSize));
        label.setForeground(color);
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
     * Applies consistent styling to JButton components.
     */
    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
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
     * Creates a styled JRadioButton with an image icon for avatar selection.
     *
     * @param imagePath path to the avatar image
     * @param xPosition horizontal placement on the panel
     * @return configured JRadioButton
     */
    private JRadioButton createAvatarRadioButton(String imagePath, int xPosition) {
        ImageIcon icon = new ImageIcon(new ImageIcon(imagePath).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        JRadioButton radioButton = new JRadioButton(icon);
        radioButton.setBounds(xPosition, 500, 80, 80);
        radioButton.setOpaque(false);
        radioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedAvatarPath = imagePath;
            }
        });
        return radioButton;
    }
    
    /**
     * Toggles password visibility for the given password field.
     */
    private void togglePasswordVisibility(JPasswordField passwordField) {
        if (passwordField.getEchoChar() == 0) {
            passwordField.setEchoChar('•');
        } else {
            passwordField.setEchoChar((char) 0);
        }
    }

    /**
     * Validates the registration form and attempts to register the user.
     * Provides error feedback if fields are empty, passwords mismatch, or avatar not selected.
     */
    private void handleRegister() {
        String username = usernameTxtField.getText();
        String password = new String(passwordTxtField.getPassword());
        String confirmPassword = new String(confirmPasswordTxtField.getPassword());

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            feedbackLabel.setForeground(Color.RED);
            feedbackLabel.setText("Please fill in all fields!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            feedbackLabel.setForeground(Color.RED);
            feedbackLabel.setText("Passwords do not match!");
            return;
        }

        if (selectedAvatarPath == null) {
            feedbackLabel.setForeground(Color.RED);
            feedbackLabel.setText("Please choose an avatar!");
            return;
        }

        try {
            userManager.registerUser(username, password, selectedAvatarPath);
            feedbackLabel.setForeground(new Color(35, 140, 35));
            feedbackLabel.setText("Registration successful! You can return to the login screen.");
        } 
        catch (InvalidUserException e) {
            feedbackLabel.setForeground(Color.RED);
            feedbackLabel.setText(e.getMessage());
        }
    }
}
