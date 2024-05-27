package domain;

import java.awt.Color;

/**
 * The large class represents a special type of barrier in the Quoridor game.
 * This type of barrier is indicated by a red color.
 */
public class large extends barrier {

    private static final long serialVersionUID = 1L; 

    /**
     * Constructs a large barrier with the specified orientation.
     * 
     * @param si The orientation of the barrier.
     */
    public large(boolean si) {
        super(si); // Calls the constructor of the superclass (barrier) with the given orientation.
        color = Color.RED; // Sets the color of the large barrier to red.
    }

}
