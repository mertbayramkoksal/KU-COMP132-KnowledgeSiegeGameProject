package screens;

import javax.swing.*;
import java.awt.*;

import game.GameManager;
import data.QuestionManager;
import data.InfoManager;
import user.User;
import user.UserManager;

/**
 * The {@code MainFrame} class represents the main window of the Knowledge Siege game.
 * It handles initialization, navigation between panels, and management of game state and user data.
 *
 * This class uses {@code CardLayout} to switch between different screens like login, registration, menu,
 * game view, and scoreboard.
 *
 * Responsibilities:
 * -Initialize and load user, question, and info data.
 * -Manage screen transitions with CardLayout.
 * -Start a new game session with selected user.
 * -Provide current user context across panels.
 *
 * Screens managed:
 * -{@link LoginPanel}
 * -{@link RegisterPanel}
 * -{@link MenuPanel}
 * -{@link GamePanel}
 * -{@link ScoreboardPanel}
 * 
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private CardLayout cardLayout;
    private JPanel mainPanel;

    private UserManager userManager;
    private QuestionManager questionManager;
    private InfoManager infoManager;

    private User currentUser;

    /**
     * Constructs the main application frame for the Knowledge Siege game.
     * 
     * This constructor initializes the core components of the application, including:
     * -{@link UserManager} – handles user data loading and authentication
     * -{@link QuestionManager} – loads and manages quiz questions
     * -{@link InfoManager} – loads informative content for the game
     * -Various panels including login, registration, menu, and scoreboard
     * 
     * It also sets up the main window’s layout using {@code CardLayout} and
     * displays the login screen as the default view.
     * 
     * If an error occurs during initialization (e.g., file read error, corrupted data),
     * a critical error message is displayed and the application exits.
     */

    public MainFrame() {
        setTitle("Knowledge Siege");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 840);
        setResizable(false);
        setLocationRelativeTo(null);

        
        try {
        	userManager = new UserManager();
            userManager.loadUsers();

            questionManager = new QuestionManager();
            questionManager.loadQuestions();

            infoManager = new InfoManager();
            infoManager.loadInfos();
		} 
        catch (Exception e) {
        	JOptionPane.showMessageDialog(this,
        	        "An error occurred. The game cannot be started.",
        	        "Critical Error",
        	        JOptionPane.ERROR_MESSAGE);
        	System.exit(1);
		}

        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        
        LoginPanel loginPanel = new LoginPanel(mainPanel, cardLayout, userManager, this);
        RegisterPanel registerPanel = new RegisterPanel(mainPanel, cardLayout, userManager);
        MenuPanel menuPanel = new MenuPanel(mainPanel, cardLayout, this);
        ScoreboardPanel scoreboardPanel = new ScoreboardPanel(mainPanel, cardLayout, userManager);

        
        mainPanel.add(loginPanel, "login");
        mainPanel.add(registerPanel, "register");
        mainPanel.add(menuPanel, "menu");
        mainPanel.add(scoreboardPanel, "scoreboard");

        
        cardLayout.show(mainPanel, "login");

        setContentPane(mainPanel);
    }

    /**
     * Sets the currently logged-in user.
     *
     * @param user the user to be set as current
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * Returns the currently active user.
     *
     * @return the currently logged-in user
     */
    public User getCurrentUser() {
        return this.currentUser;
    }

    /**
     * Starts a new game session for the specified user.
     * 
     * This method initializes a {@link GameManager} instance for the user, calls its
     * {@code gameStart()} method to begin the game logic, and displays the {@link GamePanel}
     * in the main application window.
     * 
     * If an error occurs during initialization or display, an error message is shown
     * to the user and the application exits.
     *
     * @param user the player who is starting the game
     */
    public void startGame(User user) {
        try {
        	GameManager gameManager;
            gameManager = new GameManager(user);
            
            gameManager.gameStart();

            GamePanel gamePanel = new GamePanel(gameManager, questionManager, infoManager, userManager, this);
            mainPanel.add(gamePanel, "game");
            cardLayout.show(mainPanel, "game");
		} 
        catch (Exception e) {
        	JOptionPane.showMessageDialog(this,
        	        "An error occurred. The game cannot be started.",
        	        "Critical Error",
        	        JOptionPane.ERROR_MESSAGE);
        	System.exit(1);
		}
    }

    /**
     * Displays the scoreboard panel and triggers it to load latest scores.
     */
    public void showScoreboard() {
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof ScoreboardPanel) {
                ((ScoreboardPanel) comp).loadScores();
            }
        }
        cardLayout.show(mainPanel, "scoreboard");
    }

    /**
     * Switches the current screen to the main menu.
     */
    public void showMenu() {
        cardLayout.show(mainPanel, "menu");
    }

    /**
     * Switches the current screen to the login panel.
     */
    public void showLogin() {
        cardLayout.show(mainPanel, "login");
    }
}
