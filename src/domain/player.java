package domain;

import java.awt.Color;

/**
 * The player class represents a human player in the Quoridor game.
 * This class inherits from the playerTab class.
 */
public class player extends playerTab {
    private static final long serialVersionUID = 1L; 
    /**
     * Constructs a player with the specified attributes.
     * 
     * @param boxPosition The initial position of the player on the game board.
     * @param color The color of the player's token.
     * @param name The name of the player.
     * @param winningRow The winning row of the player.
     */
    public player(box boxPosition, Color color, String name, int winningRow) {
        super(boxPosition, color, name, winningRow); // Calls the constructor of the superclass (playerTab) with the given attributes.
    }
}
