package game;

import user.User;
import data.QuestionManager;
import shotbox.ShotBox;
import data.InfoManager;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import characters.*;
import data.EnemyAvatar;
import data.Logger;

/**
 * Manages the core game logic such as level progression, enemy generation, score tracking, and win/lose conditions.
 * 
 * This class is responsible for coordinating the player’s progress, triggering game events, and communicating with auxiliary components
 * like {@link Logger}, {@link QuestionManager}, and {@link InfoManager}.
 */
public class GameManager {

    private User currentUser;
    private int currentLevel;
    private int requiredScore;
    private boolean gameOver;

    private EnemyAvatar slPool;
    private EnemyAvatar taPool;
    private EnemyAvatar professorPool;

    /**
     * Constructs a GameManager for the given user and initializes game state.
     *
     * @param user The player for the current game session.
     * @throws Exception 
     */
    public GameManager(User user) throws Exception {
        this.currentUser = user;
        this.currentLevel = 1;
        updateLevelConfig();
        this.gameOver = false;
        this.slPool = new EnemyAvatar("assets/SLs");
        this.taPool = new EnemyAvatar("assets/TAs");
        this.professorPool = new EnemyAvatar("assets/Professors");
    }


    /**
     * @return The current level number.
     */
    public int getCurrentLevel() {
        return currentLevel;
    }

    /**
     * @return The required score to progress to the next level.
     */
    public int getRequiredScore() {
        return requiredScore;
    }

    /**
     * @return True if the game has ended, false otherwise.
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * @return The current user playing the game.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Starts the game and logs the beginning of the session.
     */
    public void gameStart() {
        Logger.logGameStart();
        Logger.log("Player: " + currentUser.getName() + " has entered.");
        Logger.log("Knowledge Keepers has entered.");
    }

    /**
     * Sets the required score based on the current level.
     */
    private void updateLevelConfig() {
        switch (currentLevel) {
            case 1:
            	requiredScore = 50;
            	break;
            case 2:
            	requiredScore = 150;
            	break;
            case 3:
            	requiredScore = 300;
            	break;
        }
    }

    /**
     * Generates enemies for the current level, starts their shooting behavior,
     * and returns them.
     *
     * @param qm         The {@link QuestionManager} for question generation.
     * @param im         The {@link InfoManager} for info generation.
     * @param shotBoxes  The shared list where enemies add their {@link ShotBox} instances.
     * @return A list of {@link KnowledgeKeeper} enemies.
     */
    public List<KnowledgeKeeper> generateEnemies(QuestionManager qm, InfoManager im, List<ShotBox> shotBoxes) {
        List<KnowledgeKeeper> enemyList = new ArrayList<>();
        int startX = 100;

        if (currentLevel == 1) {
            int spacing = 300;
            
            for (int i = 0; i < 4; i++) {
                Image avatar = slPool.getAvatarPath();
                SectionLeader sl = new SectionLeader(startX + i * spacing, 60, avatar, qm, im);
                sl.startShooting(shotBoxes);
                enemyList.add(sl);
            }
        } else if (currentLevel == 2) {
            int spacing = 200;
            
            for (int i = 0; i < 4; i++) {
                Image avatar = slPool.getAvatarPath();
                SectionLeader sl = new SectionLeader(startX + i * spacing, 60, avatar, qm, im);
                sl.startShooting(shotBoxes);
                enemyList.add(sl);
            }
            for (int i = 0; i < 2; i++) {
                Image avatar = taPool.getAvatarPath();
                TeachingAssistants ta = new TeachingAssistants(startX + i * spacing, 60, avatar, qm, im);
                ta.startShooting(shotBoxes);
                enemyList.add(ta);
            }
        } else if (currentLevel == 3) {
            int spacing = 250;
            
            for (int i = 0; i < 3; i++) {
                Image avatar = taPool.getAvatarPath();
                TeachingAssistants ta = new TeachingAssistants(startX + i * spacing, 60, avatar, qm, im);
                ta.startShooting(shotBoxes);
                enemyList.add(ta);
            }
            for (int i = 0; i < 2; i++) {
                Image avatar = professorPool.getAvatarPath();
                Professors prof = new Professors(startX + i * spacing, 60, avatar, qm, im);
                prof.startShooting(shotBoxes);
                enemyList.add(prof);
            }
        }

        return enemyList;
    }

    /**
     * Increments the level if not at the maximum. Otherwise ends the game with a win.
     */
    public void levelUp() {
        if (currentLevel < 3) {
            currentLevel++;
            updateLevelConfig();
            Logger.logLevelTransition(currentLevel);
        } else {
            gameEnd(true);
        }
    }

    /**
     * Checks if the player's score qualifies for a level up.
     *
     * @param currentScore The current score of the player.
     * @return true if a level up should occur, false otherwise.
     */
    public boolean checkLevelUp(int currentScore) {
        if (currentScore >= requiredScore && !gameOver) {
            levelUp();
            return true;
        }
        return false;
    }

    /**
     * Ends the game and logs the result.
     *
     * @param won True if the player won, false if they lost.
     */
    public void gameEnd(boolean won) {
        if (won) {
            Logger.logVictory(currentUser.getName());
        } else {
            Logger.logGameOver(currentUser.getName());
        }
        Logger.log("------------------------------------------------------");
        gameOver = true;
    }
}
