package domain;

import java.awt.Color;
import java.io.Serializable;

/**
 * The barrier class represents a barrier in the Quoridor game.
 */
public class barrier implements Serializable{

    private static final long serialVersionUID = 1L; 
    protected Color color;
    

    /**
     * Constructs a barrier with the specified orientation.
     * 
     * @param orientation The orientation of the barrier.
     */
    public barrier(boolean si){
        color = colorsPalette.VIBRANT_YELLOW;
    }

    public Color getColor(){
        return color;
    }
}

