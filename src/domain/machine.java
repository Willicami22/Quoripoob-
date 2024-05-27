package domain;

import java.awt.Color;

/**
 * The machine class represents a computer-controlled player in the Quoridor game.
 * This class inherits from the playerTab class and adds a difficulty level attribute.
 */
public class machine extends playerTab {
    private String difficult; // The difficulty level of the computer-controlled player.

    /**
     * Constructs a machine player with the specified attributes.
     * 
     * @param boxPosition The initial position of the machine player on the game board.
     * @param color The color of the machine player's token.
     * @param name The name of the machine player.
     * @param winningRow The winning row of the machine player.
     * @param difficult The difficulty level of the machine player.
     */
    public machine(box boxPosition, Color color, String name, int winningRow, String difficult) {
        super(boxPosition, color, name, winningRow); // Calls the constructor of the superclass (playerTab) with the given attributes.
        this.difficult = difficult; // Sets the difficulty level of the machine player.
    }
}
