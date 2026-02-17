package characters;

import java.awt.Image;
import data.InfoManager;
import data.QuestionManager;
import shotbox.ShotBox;

/**
 * Represents a Professor enemy in the game, the most advanced type of {@link KnowledgeKeeper}.
 *
 * Professors have the highest movement speed and cause the most damage when shooting.
 * They exhibit intelligent movement by following the player 50% of the time and shoot
 * advanced-level questions or information with more challenge.
 *
 * Shooting Behavior:
 * - 30% chance to shoot an info box (level 3)
 * - 70% chance to shoot a question box (level 3)
 *
 * Movement Behavior:
 * - Every 1 second: 60% chance to follow the player horizontally
 * - 40% chance to bounce as default behavior
 *
 * This class extends {@link KnowledgeKeeper} and overrides its {@code move()} and {@code shoot()} methods.
 */
public class Professors extends KnowledgeKeeper {

	/** Indicates whether the enemy is currently tracking the player's horizontal position. */
	private boolean chanceToPlayer;

	/** Time in milliseconds when the last movement decision was made. */
	private long lastDecisionTime;

	/** The delay in milliseconds between enemy decision changes. */
	private static final long DECISION_INTERVAL = 1000;
	
    /**
     * Constructs a Professor enemy.
     *
     * @param x       The initial x-coordinate of the Professor.
     * @param y       The initial y-coordinate of the Professor.
     * @param avatar  The avatar image representing the Professor.
     * @param qm      The {@link QuestionManager} used to fetch level 3 questions.
     * @param im      The {@link InfoManager} used to fetch level 3 information.
     */
    public Professors(int x, int y, Image avatar, QuestionManager qm, InfoManager im) {
        super(x, y, avatar, 7, qm, im);
    }

    /**
     * Moves the Professor horizontally, either randomly or by tracking the player's position.
     *
     * This method improves upon static or purely random movement by incorporating
     * time-based decision logic. Every {@code DECISION_INTERVAL} milliseconds, a new
     * movement decision is made. With a 60% probability, the Professor will begin tracking
     * the player's x-position, making it appear more intelligent and reactive.
     *
     * If the Professor is not in "follow mode", it defaults to the inherited
     * left-right bouncing behavior from {@link KnowledgeKeeper}.
     *
     * @param player     The {@link Player} instance used to determine horizontal position for tracking.
     * @param panelWidth The total width of the game panel for boundary enforcement.
     */
    @Override
    public void move(Player player, int panelWidth) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastDecisionTime > DECISION_INTERVAL) {
            chanceToPlayer = RANDOM.nextDouble() < 0.6;  
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
     * Shoots a {@link ShotBox} from the Professor.
     *
     * The shot is randomly chosen with 30% probability for an info box and
     * 70% for a question box, both set to difficulty level 3.
     *
     * @return A {@code ShotBox} representing the shot fired.
     */
    @Override
    public ShotBox shoot() {
        double chanceOfInfo = RANDOM.nextDouble();

        if (chanceOfInfo < 0.3) {
            String type = "info";
            String text = ınfoManager.getRandomInfo(3);
            return new ShotBox(this.x, this.y, 7, text, type, this);
        } else {
            String type = "question";
            String text = questionManager.getRandomQuestion(3);
            return new ShotBox(this.x, this.y, 7, text, type, this);
        }
    }
}
