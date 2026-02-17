package game;

/**
 * Represents a single gameplay session of a user, including username, session number, and score. This class is used primarily for displaying
 * ordered results in a scoreboard.
 *
 * Implements {@link Comparable} to allow sorting by score and name.
 */
public class GameSession implements Comparable<GameSession> {

    private String username;
    private int gameNumber;
    private int score;

    /**
     * Constructs a GameSession object with the specified username, game number, and score.
     *
     * @param username   The name of the player who played this session.
     * @param gameNumber The sequential number of the game session for the user.
     * @param score      The score achieved in this session.
     */
    public GameSession(String username, int gameNumber, int score) {
        this.username = username;
        this.gameNumber = gameNumber;
        this.score = score;
    }

    /**
     * @return The username of the player.
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return The game number (e.g., Game1, Game2).
     */
    public int getGameNumber() {
        return gameNumber;
    }

    /**
     * @return The score achieved in this session.
     */
    public int getScore() {
        return score;
    }

    /**
     * Compares two GameSession objects to sort by:
     * -Descending score (higher scores come first)
     * -Alphabetical username order (if scores are equal)
     *
     * @param other The GameSession to compare with.
     * @return A negative integer, zero, or a positive integer depending on sort order.
     */
    @Override
    public int compareTo(GameSession other) {
        if (this.score != other.score) {
            return Integer.compare(other.score, this.score);
        }
        return this.username.compareToIgnoreCase(other.username);
    }



    /**
     * Returns a string representation of the game session for display in a scoreboard.
     *
     * @return A string in the format: "GameX by username - Score: Y"
     */
    @Override
    public String toString() {
        return "Game" + gameNumber + " by " + username + " - Score: " + score;
    }
}
