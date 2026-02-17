package characters;

import java.awt.Image;

import data.QuestionManager;
import data.InfoManager;
import shotbox.ShotBox;

/**
 * Represents a Section Leader enemy in the game, the most basic type of {@link KnowledgeKeeper}.
 *
 * Section Leaders move in a simple left-to-right horizontal bouncing pattern and shoot 
 * either information or question-based ShotBoxes at regular intervals.
 *
 * Shooting Behavior:
 * - 70% chance to shoot an info box (level 1)
 * - 30% chance to shoot a question box (level 1)
 *
 * Movement Behavior:
 * - Always moves horizontally, bouncing off screen edges
 *
 * This class extends {@link KnowledgeKeeper} and overrides its {@code shoot()} method
 * to define simple shooting logic.
 */

public class SectionLeader extends KnowledgeKeeper {

    /**
     * Constructs a new SectionLeader with the specified parameters.
     *
     * @param x        The initial x-coordinate of the enemy.
     * @param y        The initial y-coordinate of the enemy.
     * @param avatar   The avatar image representing the enemy.
     * @param qm       The {@link QuestionManager} to retrieve question content.
     * @param im       The {@link InfoManager} to retrieve info content.
     */
    public SectionLeader(int x, int y, Image avatar, QuestionManager qm, InfoManager im) {
        super(x, y, avatar, 3, qm, im);
    }

    /**
     * Randomly fires a shot from the Section Leader.
     * 
     * There is a 70% chance to shoot an "info" ShotBox and a 30% chance to shoot a "question" ShotBox.
     * The difficulty level used for content retrieval is fixed at 1.
     *
     * @return A {@link ShotBox} containing either informational text or a question.
     */
    @Override
    public ShotBox shoot() {
        double chanceOfInfo = RANDOM.nextDouble();

        if (chanceOfInfo < 0.7) {
            String text = ınfoManager.getRandomInfo(1);
            return new ShotBox(this.x, this.y, 3, text, "info", this);
        } else {
            String text = questionManager.getRandomQuestion(1);
            return new ShotBox(this.x, this.y, 3, text, "question", this);
        }
    }
}
