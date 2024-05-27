package domain;

/**
 * The Allied class represents an allied barrier in the Quoridor game.
 * An allied barrier is a barrier that can be placed by a player on the board.
 * This class inherits from the Barrier class.
 */
public class allied extends barrier {

    private static final long serialVersionUID = 1L; 
    private playerTab owner; // The player who owns the allied barrier.

    /**
     * Constructor for the Allied class.
     *
     * @param owner The player who owns the allied barrier.
     */
    public allied(playerTab owner) {
        super(true); // Calls the constructor of the Barrier class, indicating that it is an allied barrier.
        this.owner = owner; // Sets the owner of the allied barrier.
        color = colorsPalette.VIBRANT_GREEN; // Sets the color of the allied barrier.
    }

    /**
     * Gets the player who owns the allied barrier.
     *
     * @return The player who owns the allied barrier.
     */
    public playerTab getOwner() {
        return owner; // Returns the owner of the allied barrier.
    }

}
