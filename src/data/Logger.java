package data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



/**
 * The {@code Logger} class is responsible for logging important game events
 * such as game start, level transitions, score updates, player damage, and
 * final outcomes. All logs are appended to a file named {@code logs.txt}.
 * 
 * This class provides static utility methods and is not intended to be instantiated.
 */
public class Logger {

    /**
     * Appends a generic message to the {@code logs.txt} file.
     *
     * @param message The message to be logged.
     */
    public static void log(String message) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = "[" + LocalDateTime.now().format(formatter) + "]";
    	
        File logFile = new File("data/logs.txt");
        
        try {
            
            if (!logFile.exists()) {
                logFile.createNewFile();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
                writer.write(timestamp + " " + message);
                writer.newLine();
            }
        } 
        catch (Exception e) {
            System.err.println("Critical Error: Could not write to logs.txt: " + e.getMessage());
        }
    }

    /**
     * Logs the start of the game.
     */
    public static void logGameStart() {
        log("--------- Game started. ---------");
    }

    /**
     * Logs a hit event where the player receives damage.
     *
     * @param playerName The name of the player.
     * @param damage     The amount of health reduced.
     */
    public static void logHit(String playerName, int damage) {
        log(playerName + " was hit by a question. -" + damage + " HP.");
    }

    /**
     * Logs when the player collects an information box (gains points).
     *
     * @param playerName The name of the player.
     * @param points     The amount of score gained.
     */
    public static void logInfoCollected(String playerName, int points) {
        log(playerName + " collected information. +" + points + " points.");
    }

    /**
     * Logs when the player transitions to a new level.
     *
     * @param level The new level number.
     */
    public static void logLevelTransition(int level) {
        log("----- Level Transition -----");
        log("Level " + level);
        log("Knowledge Keepers has entered.");
    }
    

    /**
     * Logs when the game ends due to the player losing.
     *
     * @param playerName The name of the player.
     */
    public static void logGameOver(String playerName) {
        log("Game Over for " + playerName + ".");
    }

    /**
     * Logs when the player successfully finishes the game.
     *
     * @param playerName The name of the player.
     */
    public static void logVictory(String playerName) {
        log(playerName + " won the game!");
    }
}
