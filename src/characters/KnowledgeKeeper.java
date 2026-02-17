package characters;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;
import java.util.List;

import javax.swing.Timer;

import data.QuestionManager;
import data.InfoManager;
import shotbox.*;

/**
 * Abstract base class representing an enemy character ("KnowledgeKeeper")
 * in the game. These entities can move, shoot questions or info, and have
 * associated visual representations (avatars).
 * 
 * Subclasses include:
 * - SectionLeader
 * - TeachingAssistants
 * - Professors
 * 
 * They differ in scoring and damage logic.
 */
public abstract class KnowledgeKeeper implements Shootable {

    /** X position on screen */
    protected int x;

    /** Y position on screen */
    protected int y;

    /** Movement speed in pixels per update */
    protected int speed;

    /** Width and height of the avatar image */
    protected final int width = 55;
    protected final int height = 70;

    /** Avatar image representing this character */
    protected Image avatar;

    /** Random generator for movement and timing */
    protected static final SecureRandom RANDOM = new SecureRandom();

    /** Managers for questions and information generation */
    protected QuestionManager questionManager;
    protected InfoManager ınfoManager;

    /** Shooting timer for periodic question/info launching */
    protected Timer shootTimer;

    /** Direction of horizontal movement */
    protected boolean movingRight = RANDOM.nextBoolean();

    /**
     * Constructs a new KnowledgeKeeper.
     *
     * @param x     The initial x position
     * @param y     The initial y position
     * @param avatar Avatar image
     * @param speed Movement speed
     * @param qm    Reference to question manager
     * @param im    Reference to info manager
     */
    public KnowledgeKeeper(int x, int y, Image avatar, int speed, QuestionManager qm, InfoManager im) {
        this.x = x;
        this.y = y;
        this.avatar = avatar;
        this.speed = speed;
        this.questionManager = qm;
        this.ınfoManager = im;
    }

    /**
     * Moves the enemy horizontally across the panel, bouncing off edges.
     *
     * @param player     Reference to player (unused in basic movement)
     * @param panelWidth Width of the game panel
     */
    public void move(Player player, int panelWidth) {
        if (movingRight) {
            x += speed;
            if (x + width >= panelWidth) {
                x = panelWidth - width;
                movingRight = false;
            }
        } else {
            x -= speed;
            if (x <= 0) {
                x = 0;
                movingRight = true;
            }
        }
    }

    /**
     * Starts the shooting timer which periodically creates new ShotBoxes
     * and adds them to the given activeShots list.
     *
     * @param activeShots List to which generated ShotBoxes will be added
     */
    public void startShooting(List<ShotBox> activeShots) {
        int delay = 2000 + RANDOM.nextInt(2000);

        shootTimer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShotBox shot = shoot();
                if (shot != null) {
                    activeShots.add(shot);
                }
                shootTimer.setDelay(2000 + RANDOM.nextInt(2000));
            }
        });

        shootTimer.setRepeats(true);
        shootTimer.start();
    }

    /**
     * Stops the shooting timer for this enemy.
     */
    public void stopShooting() {
        if (shootTimer != null) {
            shootTimer.stop();
        }
    }

    /**
     * Returns the score the player should gain when collecting info
     * from this enemy type.
     *
     * @return score value (depends on subclass)
     */
    public int getInfoScore() {
        if (this instanceof SectionLeader) {
        	return 10;
        }
        if (this instanceof TeachingAssistants) {
        	return 20;
        }
        if (this instanceof Professors) {
        	return 30;
        }
        return 10;
    }

    /**
     * Returns the damage the player receives when hit by this enemy’s question.
     *
     * @return damage value (depends on subclass)
     */
    public int getQuestionDamage() {
        if (this instanceof SectionLeader) {
        	return 5;
        }
        if (this instanceof TeachingAssistants) {
        	return 10;
        }
        if (this instanceof Professors) {
        	return 20;
        }
        return 10;
    }

    /**
     * Renders the enemy on the screen.
     *
     * @param g The Graphics context to draw on
     */
    public void draw(Graphics g) {
        g.drawImage(avatar, x, y, null);
    }
}
