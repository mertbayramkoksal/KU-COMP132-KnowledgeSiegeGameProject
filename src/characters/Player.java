package characters;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import data.Logger;

/**
 * Represents the player character in the game.
 * 
 * The {@code Player} can move left and right, keep track of its health and score,
 * and interact with other elements on the screen such as {@code ShotBox}es.
 * The player is visually represented by an avatar image loaded from a given path.
 */
public class Player {

    private int x;
    private int y;
    private Image avatar;
    private final int width = 40;
    private final int height = 54;
    private int health = 100;
    private int score = 0;

    /**
     * Constructs a new {@code Player} with a specified initial position and avatar image.
     *
     * @param x           Initial x-coordinate.
     * @param y           Initial y-coordinate.
     * @param avatarPath  File path of the avatar image to be loaded.
     */
    public Player(int x, int y, String avatarPath) {
        this.x = x;
        this.y = y;
        loadImage(avatarPath);
    }


    /**
     * Returns the current x-coordinate of the player.
     *
     * @return the x-coordinate.
     */
    public int getPlayerX() {
        return x;
    }

    /**
     * Returns the player's current health value.
     *
     * @return the health value (0–100).
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns the player's current score.
     *
     * @return the score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the bounding rectangle of the player, used for collision detection.
     *
     * @return a {@link Rectangle} representing the player's bounds.
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }


    /**
     * Moves the player to the right by a fixed speed.
     */
    public void moveRight(int panelWidth) {
    	if (x + 15 + width <= panelWidth) {
            x += 15;
        } 
    	else {
            x = panelWidth - width;
        }
    }

    /**
     * Moves the player to the left by a fixed speed.
     */
    public void moveLeft() {
    	if (x - 15 >= 0) {
            x -= 15;
        } 
    	else {
            x = 0;
        }
    }


    /**
     * Increases the player's score by a given amount.
     *
     * @param score the amount to add to the current score.
     */
    public void addScore(int score) {
        this.score += score;
    }

    /**
     * Decreases the player's health by a given damage amount.
     *
     * @param damage the amount to subtract from health.
     */
    public void takeDamage(int damage) {
        health -= damage;
    }


    /**
     * Draws the player's avatar at its current location.
     *
     * @param g the {@link Graphics} object used for drawing.
     */
    public void draw(Graphics g) {
        g.drawImage(avatar, x, y, null);
    }


    /**
     * Loads the avatar image from the provided file path and scales it to the player size.
     *
     * @param avatarPath the file path to the image.
     */
    private void loadImage(String avatarPath) {
        try {
            this.avatar = ImageIO.read(new File(avatarPath)).getScaledInstance(width, height, Image.SCALE_SMOOTH);
        } 
        catch (IOException e) {
        	Logger.log("Player avatar cannot load: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "An error occurred", "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
