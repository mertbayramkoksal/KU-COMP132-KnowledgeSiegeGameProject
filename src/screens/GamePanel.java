package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import characters.*;
import data.*;
import game.*;
import shotbox.*;
import user.*;

/**
 * {@code GamePanel} is the main game screen where gameplay takes place.
 * 
 * It includes the rendering of the player, enemies, projectiles (shotboxes), game logic (collision, level transitions), and GUI elements such as health bar,
 * score display, and text areas for questions/info.
 *
 * This panel is initialized with game management and user data, and includes:
 * -Top panel with health and score indicators.
 * -Bottom panel with current question and info messages.
 * -Keyboard controls to move the player.
 * -Game loop running via a Swing Timer.
 * -Dynamic level setup and transitions.
 *
 */
public class GamePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private GameManager gameManager;
    private MainFrame mainFrame;
    private UserManager userManager;
    private QuestionManager qManager;
    private InfoManager iManager;

    private Player player;
    private List<KnowledgeKeeper> enemies;
    private List<ShotBox> activeShots = new ArrayList<>();
    private Timer timer;

    private JTextArea questionTXT;
    private JTextArea infoTXT;
    private JProgressBar healthBar;
    private JLabel scoreValueLBL;
    private JLabel levelLBL;

    /**
     * Constructs the {@code GamePanel}, initializes UI, sets up game elements, and starts the game loop.
     *
     * @param gameManager  game logic controller
     * @param qManager     question manager for enemies
     * @param iManager     info manager for enemies
     * @param userManager  manages users and score saving
     * @param mainFrame    reference to main application frame
     */
    public GamePanel(GameManager gameManager, QuestionManager qManager, InfoManager iManager, UserManager userManager, MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.gameManager = gameManager;
        this.userManager = userManager;
        this.qManager = qManager;
        this.iManager = iManager;

        setLayout(new BorderLayout());
        setBackground(new Color(230, 240, 250));

        player = new Player(500, 640, gameManager.getCurrentUser().getAvatarPath());

        setupTopPanel();
        setupBottomPanel();
        setupKeyboard();
        setupLevel();
        startGameLoop();

        setFocusable(true);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                requestFocusInWindow();
            }
        });

    }

    /**
     * Creates and adds the top UI panel with health bar, current level, and score display.
     */
    private void setupTopPanel() {
        JPanel topPanel = new JPanel(null);
        topPanel.setPreferredSize(new Dimension(960, 64));
        topPanel.setBackground(new Color(240, 255, 240));

        JLabel healthLBL = new JLabel("Health: ");
        healthLBL.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
        healthLBL.setBounds(10, 20, 80, 20);
        topPanel.add(healthLBL);

        healthBar = new JProgressBar();
        healthBar.setValue(100);
        healthBar.setBounds(80, 15, 150, 30);
        topPanel.add(healthBar);
        
        levelLBL = new JLabel("LEVEL: " + gameManager.getCurrentLevel());
        levelLBL.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
        levelLBL.setBounds(500, 20, 120, 20);
        topPanel.add(levelLBL);

        JLabel scoreLBL = new JLabel("Score:");
        scoreLBL.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
        scoreLBL.setBounds(950, 20, 90, 20);
        topPanel.add(scoreLBL);

        scoreValueLBL = new JLabel("0");
        scoreValueLBL.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
        scoreValueLBL.setBounds(1050, 20, 60, 20);
        topPanel.add(scoreValueLBL);

        add(topPanel, BorderLayout.NORTH);
    }

    /**
     * Creates and adds the bottom UI panel with question and info display.
     */
    private void setupBottomPanel() {
        JPanel bottomPanel = new JPanel(null);
        bottomPanel.setPreferredSize(new Dimension(960, 100));
        bottomPanel.setBackground(new Color(210, 210, 235)); 

        JLabel questionLBL = new JLabel("QUESTION:");
        questionLBL.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
        questionLBL.setBounds(25, 12, 133, 25);
        bottomPanel.add(questionLBL);

        questionTXT = new JTextArea();
        questionTXT.setBounds(183, 12, 680, 25);
        questionTXT.setEditable(false);
        questionTXT.setLineWrap(true);
        bottomPanel.add(questionTXT);

        JLabel infoLBL = new JLabel("INFO:");
        infoLBL.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
        infoLBL.setBounds(25, 52, 133, 25);
        bottomPanel.add(infoLBL);

        infoTXT = new JTextArea();
        infoTXT.setBounds(183, 52, 680, 25);
        infoTXT.setEditable(false);
        infoTXT.setLineWrap(true);
        bottomPanel.add(infoTXT);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Attaches keyboard listener for left and right movement of the player.
     */
    private void setupKeyboard() {
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    player.moveLeft();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    player.moveRight(getWidth());
                }
            }
        });
    }

    /**
     * Initializes a level by clearing the previous enemies and generating new ones.
     */
    private void setupLevel() {
        clearPreviousLevel();
        enemies = gameManager.generateEnemies(qManager, iManager, activeShots);
    }

    /**
     * Clears enemies and projectiles from the previous level.
     */
    private void clearPreviousLevel() {
        if (enemies != null) {
            for (KnowledgeKeeper enemy : enemies) {
                enemy.stopShooting();
            }
            enemies.clear();
        }
        activeShots.clear();
    }

    /**
     * Starts the game loop using a Swing Timer.
     * Runs game logic such as movement, collisions, level check, and repainting.
     */
    private void startGameLoop() {
        timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (ShotBox shotBox : activeShots) {
                    shotBox.moveDown();
                }
                for (KnowledgeKeeper enemy : enemies) {
                    enemy.move(player, getWidth());
                }
                checkCollisions();
                checkGameState();
                repaint();
            }
        });
        timer.start();
    }

    /**
     * Checks for level-up or game-over conditions.
     * Displays messages and triggers level transitions or end game.
     */
    private void checkGameState() {
        if (!gameManager.isGameOver() && gameManager.checkLevelUp(player.getScore())) {
            if (!gameManager.isGameOver()) {
                JOptionPane.showMessageDialog(this, "Transition to Level " + 
                gameManager.getCurrentLevel(), "Level Up!", JOptionPane.INFORMATION_MESSAGE);
                setupLevel();
                levelLBL.setText("LEVEL: " + gameManager.getCurrentLevel());
            } 
            else {
                endGame(true);
            }
        }

        if (!gameManager.isGameOver() && player.getHealth() <= 0) {
            gameManager.gameEnd(false);
            endGame(false);
        }
    }

    /**
     * Ends the game, stops timers, logs results, and switches to scoreboard.
     *
     * @param won {@code true} if the player won, otherwise {@code false}
     */
    private void endGame(boolean won) {
        if (timer != null) {
        	timer.stop();
        }
        for (KnowledgeKeeper enemy : enemies) {
            enemy.stopShooting();
        }

        gameManager.getCurrentUser().addScore(player.getScore());
        userManager.saveUsers();

        JOptionPane.showMessageDialog(this, 
        won ? "You won the game!" : "You lost the game!", "Game End", 
        JOptionPane.INFORMATION_MESSAGE);
        mainFrame.showScoreboard();
    }

    /**
     * Paints the game panel, including player, enemies, and shot boxes.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (KnowledgeKeeper enemy : enemies) {
            enemy.draw(g);
        }
        player.draw(g);
        for (ShotBox shotBox : activeShots) {
            shotBox.draw(g);
        }
    }

    /**
     * Checks for collisions between the player and shot boxes.
     * Applies score or damage depending on shot type.
     */
    private void checkCollisions() {
        Rectangle playerBoundary = player.getBounds();

        for (int i = 0; i < activeShots.size(); i++) {
            ShotBox shotBox = activeShots.get(i);

            if (shotBox.getBounds().intersects(playerBoundary)) {
                String type = shotBox.getType();
                String text = shotBox.getText();

                if (type.equals("info")) {
                    int score = shotBox.getEnemy().getInfoScore();
                    
                    player.addScore(score);
                    scoreValueLBL.setText(String.valueOf(player.getScore()));
                    infoTXT.setText(text);
                    questionTXT.setText("");
                    
                    Logger.logInfoCollected(gameManager.getCurrentUser().getName(), score);
                    Logger.log("Score: " + player.getScore());
                } 
                else {
                    int damage = shotBox.getEnemy().getQuestionDamage();
                    
                    player.takeDamage(damage);
                    healthBar.setValue(player.getHealth());
                    questionTXT.setText(text);
                    infoTXT.setText("");
                    
                    Logger.logHit(gameManager.getCurrentUser().getName(), damage);
                    Logger.log("Health: " + player.getHealth());
                }

                activeShots.remove(i);
                i--;
            }
        }
    }
}
