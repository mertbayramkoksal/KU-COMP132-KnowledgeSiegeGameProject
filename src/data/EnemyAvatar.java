package data;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * The {@code EnemyAvatar} class is responsible for loading and providing 
 * shuffled avatar images from a specified directory. Each avatar image can 
 * only be used once until the internal list is exhausted.
 * 
 * This is typically used to assign unique avatars to enemy characters 
 * in the game, ensuring visual variety and thematic consistency.
 */
public class EnemyAvatar {

    private List<String> avatarPaths;

    /**
     * Constructs a new {@code EnemyAvatar} loader for the specified folder path.
     * 
     * @param folderPath The path to the folder containing avatar images (JPG or JPEG).
     * 
     * @throws Exception if the file cannot be found
     */
    public EnemyAvatar(String folderPath) throws Exception {
        avatarPaths = new ArrayList<>();
        Path path = Paths.get(folderPath);

        if (Files.exists(path) && Files.isDirectory(path)) {
        	DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path);
        	for (Path file : directoryStream) {
                
            	String fileName = file.getFileName().toString().toLowerCase();                
                if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                    avatarPaths.add(file.toString());
                }
            }
        }

        Collections.shuffle(avatarPaths);
    }

    /**
     * Retrieves and removes the next available avatar image from the internal list.
     * 
     * @return A loaded {@code Image} object from the shuffled avatar list,
     *         or {@code null} if loading fails or list is empty.
     */
    public Image getAvatarPath() {
        if (avatarPaths.isEmpty()) {
            System.err.println("No more avatar images available.");
            return null;
        }

        String avatarPath = avatarPaths.remove(0);

        try {
            return ImageIO.read(new File(avatarPath));
        } 
        catch (IOException e) {
            return null;
        }
    }
}
