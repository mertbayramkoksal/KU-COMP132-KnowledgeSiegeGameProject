package characters;

import java.awt.Image;
import data.QuestionManager;
import data.InfoManager;
import shotbox.ShotBox;

/**
 * Represents a Teaching Assistant (TA) enemy in the game, a mid-level type of {@link KnowledgeKeeper}.
 *
 * Teaching Assistants have faster speed than Section Leaders and exhibit semi-intelligent
 * movement behavior. Occasionally, they track the player horizontally, simulating
 * a smarter enemy. Their shots are balanced between info and question boxes.
 *
 * Shooting Behavior:
 * - 50% chance to shoot an info box (level 2)
 * - 50% chance to shoot a question box (level 2)
 *
 * Movement Behavior:
 * - Every 2 second, decides whether to follow the player's x-position (40% chance)
 * - Otherwise, bounces horizontally like a regular enemy
 *
 * This class extends {@link KnowledgeKeeper} and overrides its {@code move()} and {@code shoot()} methods
 * to provide intermediate-level challenge.
 */

public class TeachingAssistants extends KnowledgeKeeper {
	
	/** Indicates whether the enemy is currently tracking the player's horizontal position. */
	private boolean chanceToPlayer;

	/** Time in milliseconds when the last movement decision was made. */
	private long lastDecisionTime;

	/** The delay in milliseconds between enemy decision changes. */
	private static final long DECISION_INTERVAL = 1000;


	
    /**
     * Constructs a TeachingAssistant enemy.
     *
     * @param x        The initial x-coordinate of the TA.
     * @param y        The initial y-coordinate of the TA.
     * @param avatar   The avatar image representing the TA.
     * @param qm       The {@link QuestionManager} to retrieve question content.
     * @param im       The {@link InfoManager} to retrieve informational content.
     */
    public TeachingAssistants(int x, int y, Image avatar, QuestionManager qm, InfoManager im) {
        super(x, y, avatar, 5, qm, im);
    }

    /**
     * Moves the TA either horizontally or toward the player based on a periodic decision.
     * 
     * This method improves upon the original random behavior by making movement decisions
     * at fixed time intervals (e.g., every {@code DECISION_INTERVAL} milliseconds).
     * 
     * With a 40% probability at each decision point, the TA will begin following the player's
     * horizontal position to simulate more intelligent behavior. Otherwise, it continues to move
     * in a bouncing pattern like a standard {@link KnowledgeKeeper}.
     *
     * @param player     The current {@link Player} instance, used to obtain the player's x-position.
     * @param panelWidth The width of the game panel used for movement boundary enforcement.
     */
    @Override
    public void move(Player player, int panelWidth) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastDecisionTime > DECISION_INTERVAL) {
            chanceToPlayer = RANDOM.nextDouble() < 0.4;
            lastDecisionTime = currentTime;
        }

        if (chanceToPlayer) {
            if (player.getPlayerX() > this.x) {
                x += speed;
            } else if (player.getPlayerX() < this.x) {
                x -= speed;
            }
        } 
        else {
            super.move(player, panelWidth);
        }
    }



    /**
     * Fires a shot from the TA.
     * 
     * With a 50% probability, the shot is either an informational box or a question box.
     * The difficulty level for both is set to 2.
     *
     * @return A {@link ShotBox} instance containing the question or information.
     */
    @Override
    public ShotBox shoot() {
        double chanceOfInfo = RANDOM.nextDouble();

        if (chanceOfInfo < 0.5) {
            String type = "info";
            String text = ınfoManager.getRandomInfo(2);
            return new ShotBox(this.x, this.y, 5, text, type, this);
        } else {
            String type = "question";
            String text = questionManager.getRandomQuestion(2);
            return new ShotBox(this.x, this.y, 5, text, type, this);
        }
    }
}
