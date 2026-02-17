package shotbox;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import characters.KnowledgeKeeper;
import data.Logger;

/**
 * Represents a shot (either a question or information) dropped by a {@code KnowledgeKeeper} enemy.
 * 
 * A {@code ShotBox} travels vertically downward and contains a visual icon as well as a text payload.
 * These are used to interact with the player when a collision occurs.
 */
public class ShotBox {

    private int x;
    private int y;
    private int speed;
    private String text;
    private String type;

    private Image icon;
    private static Image infoIcon;
    private static Image questionIcon;

    private KnowledgeKeeper enemy;

    /**
     * Constructs a new {@code ShotBox} instance with the given parameters.
     *
     * @param x       The initial x-coordinate.
     * @param y       The initial y-coordinate.
     * @param speed   The speed at which the shot will move downwards.
     * @param text    The text content of the shot (question or info).
     * @param type    The type of the shot ("info" or "question").
     * @param enemy   The {@code KnowledgeKeeper} that fired this shot.
     */
    public ShotBox(int x, int y, int speed, String text, String type, KnowledgeKeeper enemy) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.text = text;
        this.type = type;
        this.enemy = enemy;
        loadIcons();
        this.icon = type.equals("info") ? infoIcon : questionIcon;
    }


    /**
     * Returns the text of the shot.
     *
     * @return The string content (question or info).
     */
    public String getText() {
        return text;
    }

    /**
     * Returns the type of the shot ("info" or "question").
     *
     * @return The type as a string.
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the enemy who fired this shot.
     *
     * @return The {@code KnowledgeKeeper} instance.
     */
    public KnowledgeKeeper getEnemy() {
        return enemy;
    }

    /**
     * Returns the bounding box for collision detection.
     *
     * @return A {@link Rectangle} representing the shot's current position and size.
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, icon.getWidth(null), icon.getHeight(null));
    }


    /**
     * Moves the shot downward by its speed.
     */
    public void moveDown() {
        y += speed;
    }
    

    /**
     * Renders the shot icon on the screen at its current location.
     *
     * @param g The {@link Graphics} context used for drawing.
     */
    public void draw(Graphics g) {
        g.drawImage(icon, x, y, null);
    }


    /**
     * Loads static icons used for representing "info" and "question" shots.
     */
    private static void loadIcons() {
        if (infoIcon == null || questionIcon == null) {
            try {
                infoIcon = ImageIO.read(new File("assets/info.png"));
                questionIcon = ImageIO.read(new File("assets/question.png"));
            } 
            catch (IOException e) {
            	Logger.log("ShotBox image cannot load: " + e.getMessage());
                JOptionPane.showMessageDialog(null, "An error occurred", "File Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
