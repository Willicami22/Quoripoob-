package domain;

import java.awt.Color;
import java.io.Serializable;

/**
 * The Barrier class represents a barrier in the Quoridor game.
 */
public class barrier implements Serializable {

    private static final long serialVersionUID = 1L; 
    protected Color color;

    /**
     * Constructs a barrier with the specified orientation.
     * 
     * @param si The orientation of the barrier.
     */
    public barrier(boolean si) {
        color = colorsPalette.VIBRANT_YELLOW;
    }

    /**
     * Gets the color of the barrier.
     * 
     * @return The color of the barrier.
     */
    public Color getColor() {
        return color;
    }
}


