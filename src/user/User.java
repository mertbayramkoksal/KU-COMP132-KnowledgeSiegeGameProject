package user;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the game, including credentials, avatar selection, and a history of game scores.
 */
public class User {

    private String username;
    private String password;
    private String avatarPath;
    private List<Integer> scores;

    /**
     * Constructs a new User with the specified username, password, and avatar path.
     *
     * @param name        the username of the user
     * @param password    the user's password
     * @param avatarPath  the file path to the user's avatar image
     */
    public User(String name, String password, String avatarPath) {
        this.username = name;
        this.password = password;
        this.avatarPath = avatarPath;
        this.scores = new ArrayList<>();
    }


    /**
     * Returns the avatar image path of the user.
     *
     * @return the avatar file path
     */
    public String getAvatarPath() {
        return avatarPath;
    }

    /**
     * Returns the username of the user.
     *
     * @return the username
     */
    public String getName() {
        return username;
    }

    /**
     * Returns the password of the user.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns a list of all game scores for this user.
     *
     * @return a list of integers representing the scores
     */
    public List<Integer> getScores() {
        return scores;
    }


    /**
     * Checks whether the provided password matches the user's password.
     *
     * @param password the password to validate
     * @return true if the password matches, false otherwise
     */
    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    /**
     * Adds a new score to the user's game history.
     *
     * @param score the score to add
     */
    public void addScore(int score) {
        scores.add(score);
    }

}
