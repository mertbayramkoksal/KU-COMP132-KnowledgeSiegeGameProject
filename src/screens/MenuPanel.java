package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The {@code MenuPanel} class represents the main menu screen of the Knowledge Siege game.
 * 
 * This panel provides players with navigation options to:
 * -Start a new game
 * -View the scoreboard
 * -Exit the application
 * 
 * All components are manually positioned using absolute layout and styled consistently
 * using helper methods.
 *
 * Usage:
 * -This panel is added to a {@code CardLayout}-based container and shown after successful login.
 *
 */
public class MenuPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private MainFrame mainFrame;

    /**
     * Constructs the menu panel and initializes its components.
     *
     * @param parentPanel the panel container using CardLayout (not directly used here)
     * @param cardLayout  the CardLayout for switching screens
     * @param mainFrame   reference to the {@code MainFrame} that handles transitions
     */
    public MenuPanel(JPanel parentPanel, CardLayout cardLayout, MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        setLayout(null);
        setBackground(new Color(245, 248, 255));

        
        int buttonWidth = 300;
        int buttonHeight = 60;
        int buttonX = 400;

        // Title label
        JLabel titleLBL = new JLabel("KNOWLEDGE SIEGE");
        titleLBL.setFont(new Font("Arial", Font.BOLD, 36));
        titleLBL.setForeground(new Color(33, 47, 61));
        titleLBL.setBounds(370, 80, 400, 50);
        add(titleLBL);

        // Start Game button
        JButton startBTN = new JButton("Start Game");
        styleButton(startBTN);
        startBTN.setBounds(buttonX, 180, buttonWidth, buttonHeight);
        add(startBTN);

        // Scoreboard button
        JButton scoreBoardBTN = new JButton("Scoreboard");
        styleButton(scoreBoardBTN);
        scoreBoardBTN.setBounds(buttonX, 260, buttonWidth, buttonHeight);
        add(scoreBoardBTN);

        // Exit button
        JButton exitBTN = new JButton("Exit");
        styleButton(exitBTN);
        exitBTN.setBounds(buttonX, 340, buttonWidth, buttonHeight);
        add(exitBTN);

        // Button actions
        startBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.startGame(mainFrame.getCurrentUser());
			}
		});
        
        scoreBoardBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.showScoreboard();
			}
		});
        
        exitBTN.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
    }

    /**
     * Applies consistent visual styling to menu buttons.
     *
     * @param button the {@code JButton} to style
     */
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.BLACK);
        button.setBackground(Color.LIGHT_GRAY);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        button.setOpaque(true);
        button.setFocusPainted(false);
    }
}
