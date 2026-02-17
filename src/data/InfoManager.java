package data;

import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * The {@code InfoManager} class is responsible for loading and providing
 * educational information strings (called "infos") used in the game.
 * 
 * Infos are categorized by level and stored internally. These are typically
 * used as "helpful" drops from enemies and reward the player with points.
 */
public class InfoManager {

    private final List<String> level1Infos = new ArrayList<>();
    private final List<String> level2Infos = new ArrayList<>();
    private final List<String> level3Infos = new ArrayList<>();
    
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Loads information entries from the {@code info.txt} file and distributes
     * them into level-specific lists.
     * 
     * The expected file format should have lines starting with:
     * 
     * [Level 1] Some basic info...
     * [Level 2] Intermediate concept...
     * [Level 3] Advanced theory...
     * 
     * 
     * Lines that do not follow this format are ignored.
     * 
     * @throws Exception if the file cannot be found
     */
    public void loadInfos() throws Exception {
    	Scanner scanner = new Scanner(new File("data/info.txt"));
    	while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.startsWith("[Level 1]")) {
                level1Infos.add(line.substring(10).trim());
            } 
            else if (line.startsWith("[Level 2]")) {
                level2Infos.add(line.substring(10).trim());
            } 
            else if (line.startsWith("[Level 3]")) {
                level3Infos.add(line.substring(10).trim());
            }
        }
    }

    /**
     * Returns a random info string based on the given level.
     * 
     * @param level The level (1, 2, or 3) for which to retrieve info.
     * @return A randomly selected info string, or "Unknown level." if level is invalid.
     */
    public String getRandomInfo(int level) {
        List<String> list;

        switch (level) {
            case 1:
                list = level1Infos;
                break;
            case 2:
                list = level2Infos;
                break;
            case 3:
                list = level3Infos;
                break;
            default:
                return "Unknown level.";
        }

        return list.get(RANDOM.nextInt(list.size()));
    }

}
