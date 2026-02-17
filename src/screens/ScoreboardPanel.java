package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import user.User;
import user.UserManager;
import game.ScoreManager;

/**
 * The {@code ScoreboardPanel} class displays a ranked list of all game sessions from all users.
 * 
 * It uses a {@link JTextArea} inside a {@link JScrollPane} to present the scoreboard, and includes a button to return to the main menu.
 *
 * Key Features:
 * -Displays scores in a scrollable text area.
 * -Retrieves and formats user scores via {@code ScoreManager}.
 * -Includes a navigation button to return to the menu.
 * -Styled UI components for a clean and consistent look.
 *     
 */
public class ScoreboardPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextArea scoresArea;
    private JPanel parentPanel;
    private CardLayout cardLayout;
    private UserManager userManager;

    /**
     * Constructs the scoreboard panel.
     *
     * @param parent        the container panel using CardLayout
     * @param layout        the layout manager for switching views
     * @param userManager   the user manager instance to access stored user scores
     */
    public ScoreboardPanel(JPanel parent, CardLayout layout, UserManager userManager) {
        this.parentPanel = parent;
        this.cardLayout = layout;
        this.userManager = userManager;

        setLayout(null);
        setBackground(new Color(240, 242, 245));      

        // Scoreboard title
        JLabel scoreboardLBL = new JLabel("SCOREBOARD");
        scoreboardLBL.setFont(new Font("Arial", Font.BOLD, 30));
        scoreboardLBL.setForeground(new Color(33, 47, 61));
        scoreboardLBL.setBounds(450, 40, 300, 40);
        add(scoreboardLBL);

        // Scrollable Text Area
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(220, 100, 660, 340);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 2));
        add(scrollPane);

        scoresArea = new JTextArea();
        scoresArea.setEditable(false);
        scoresArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        scoresArea.setMargin(new Insets(10, 10, 10, 10));
        scrollPane.setViewportView(scoresArea);

        // Back to Menu button
        JButton menuBTN = new JButton("Main Menu");
        menuBTN.setFont(new Font("Arial", Font.BOLD, 20));
        menuBTN.setBackground(Color.LIGHT_GRAY);
        menuBTN.setForeground(Color.BLACK);
        menuBTN.setFocusPainted(false);
        menuBTN.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        menuBTN.setBounds(450, 470, 200, 50);
        menuBTN.setOpaque(true);
        add(menuBTN);

        // Button action
        menuBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(parentPanel, "menu");
            }
        });
    }

    /**
     * Loads and displays the sorted scoreboard from all users.
     * This method should be called whenever the scoreboard panel becomes visible,
     * typically after a game ends or from the menu.
     */
    public void loadScores() {
        List<User> users = userManager.getAllUsers();
        String scoreboardText = ScoreManager.getSortedScoreboard(users);
        scoresArea.setText(scoreboardText);
    }
}
