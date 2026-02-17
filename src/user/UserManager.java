package user;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import javax.swing.JOptionPane;

import data.Logger;

/**
 * Manages all registered users in the game system.
 * Provides functionality to register, authenticate, load and save users to a file.
 */
public class UserManager {

    private Map<String, User> users;

    /**
     * Constructs a new UserManager with an empty user list.
     */
    public UserManager() {
        users = new HashMap<>();
    }


    /**
     * Returns a list of all registered users.
     *
     * @return a List of User objects
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }


    /**
     * Saves all registered users and their data (including scores and avatar paths) to the "user.txt" file.
     */
    public void saveUsers() {
        try (Formatter formatter = new Formatter(new File("data/user.txt"))) {
            for (User user : users.values()) {
                formatter.format("%s,%s,%s,", user.getName(), 
                		user.getPassword(), user.getAvatarPath());
                for (int score : user.getScores()) {
                    formatter.format("%d,", score);
                }
                formatter.format("%n");
            }
        } catch (FileNotFoundException e) {
        	Logger.log("Users cannot save: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "An error occurred", "File Error", 
            JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Loads users and their associated data from the "user.txt" file.
     * Creates User objects and populates the internal map.
     * 
     * @throws Exception if the file cannot be created
     */
    public void loadUsers() throws Exception {
    	File file = new File("data/user.txt");

        if (!file.exists()) {
            try {
                boolean created = file.createNewFile();
                if (created) {
                    Logger.log("user.txt was not found, a new file was created.");
                    return;
                }
            } catch (IOException e) {
                Logger.log("Error creating user.txt: " + e.getMessage());
                throw e;
            }
        }
    	
    	Scanner scanner = new Scanner(file);
    	while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");

            String username = parts[0];
            String password = parts[1];
            String avatarPath = parts[2];

            User user = new User(username, password, avatarPath);

            for (int i = 3; i < parts.length; i++) {
                try {
                    user.addScore(Integer.parseInt(parts[i]));
                } 
                catch (NumberFormatException ignored) {
                	Logger.log("Invalid score for user " + username + ": " + parts[i]);
                }
            }

            users.put(username, user);
        }
    }

    
    /**
     * Registers a new user with the given username, password, and avatar path.
     *
     * @param username    the user's name
     * @param password    the user's password
     * @param avatarPath  the path to the avatar image
     * @throws InvalidUserException if the username is already taken
     */
    public void registerUser(String username, String password, String avatarPath) throws InvalidUserException {
        if (users.containsKey(username)) {
            throw new InvalidUserException("This username already exists!");
        }

        users.put(username, new User(username, password, avatarPath));
        saveUsers();
    }

    /**
     * Authenticates a user based on the provided username and password.
     *
     * @param username the provided username
     * @param password the provided password
     * @return the authenticated User object
     * @throws InvalidUserException if the user is not found or credentials are invalid
     */
    public User login(String username, String password) throws InvalidUserException {
        User user = users.get(username);

        if (user == null) {
            throw new InvalidUserException("User not found!");
        }

        if (!user.getName().equals(username) || !user.getPassword().equals(password)) {
            throw new InvalidUserException("Username or password is incorrect!");
        }

        return user;
    }
}
