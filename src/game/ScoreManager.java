package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import user.User;

/**
 * Utility class responsible for managing and formatting scoreboard data based on user game sessions.
 */
public class ScoreManager {

    /**
     * Generates a formatted scoreboard string by aggregating all users' game scores.
     * 
     * Each game session is represented by a {@link GameSession} object. Sessions are sorted:
     * -First, by score in descending order
     * -Second, by username alphabetically (if scores are equal)
     * 
     * @param allUsers The list of all registered users with their game scores.
     * @return A formatted scoreboard string for display purposes.
     */
    public static String getSortedScoreboard(List<User> allUsers) {
        List<GameSession> sessions = new ArrayList<>();

        for (User user : allUsers) {
            List<Integer> scores = user.getScores();
            for (int i = 0; i < scores.size(); i++) {
                sessions.add(new GameSession(user.getName(), i + 1, scores.get(i)));
            }
        }

        Collections.sort(sessions);

        StringBuilder stringBuilder = new StringBuilder();
        for (GameSession session : sessions) {
            stringBuilder.append(session).append("\n");
        }

        return stringBuilder.toString();
    }
}
