package data;

import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import user.InvalidUserException;

/**
 * The {@code QuestionManager} class is responsible for loading and supplying
 * level-specific quiz questions used by enemies in the game.
 * 
 * Questions are categorized into 3 difficulty levels and stored internally.
 * These are typically used by enemies to challenge the player and inflict
 * damage if answered incorrectly or collided with.
 */
public class QuestionManager {

    private final List<String> level1Questions = new ArrayList<>();
    private final List<String> level2Questions = new ArrayList<>();
    private final List<String> level3Questions = new ArrayList<>();
    
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Loads quiz questions from a text file named {@code questions.txt}.
     * 
     * The expected format is:
     * 
     * [Level 1] question text...
     * [Level 2] question text...
     * [Level 3] question text...
     * 
     * 
     * Each line must start with the level indicator. The rest of the line is stored as the actual question content for that level.
     * 
     * @throws Exception if the file cannot be found
     */
    public void loadQuestions() throws Exception {
    	 Scanner scanner = new Scanner(new File("data/questions.txt"));
    	 while (scanner.hasNextLine()) {
             String line = scanner.nextLine();
             
             if (line.startsWith("[Level 1]")) {
                 level1Questions.add(line.substring(10).trim());
             } 
             else if (line.startsWith("[Level 2]")) {
                 level2Questions.add(line.substring(10).trim());
             } 
             else if (line.startsWith("[Level 3]")) {
                 level3Questions.add(line.substring(10).trim());
             }
         }
    }

    /**
     * Returns a random question string from the specified difficulty level.
     * 
     * @param level The level of difficulty (1 for easy, 2 for medium, 3 for hard)
     * @return A randomly selected question string from that level,
     *         or "Unknown level." if the level is invalid.
     */
    public String getRandomQuestion(int level) {
        List<String> list;
        switch (level) {
            case 1:
            	list = level1Questions;
            	break;
            case 2:
            	list = level2Questions;
            	break;
            case 3:
            	list = level3Questions;
            	break;
            default: 
                return "Unknown level.";
            
        }

        return list.get(RANDOM.nextInt(list.size()));
    }
}
