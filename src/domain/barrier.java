package domain;

import java.io.Serializable;

/**
 * The barrier class represents a barrier in the Quoridor game.
 */
public class barrier implements Serializable{

    private static final long serialVersionUID = 1L; 
    protected String color;
    private boolean orientation;
    

    /**
     * Constructs a barrier with the specified orientation.
     * 
     * @param orientation The orientation of the barrier.
     */
    public barrier(boolean orientation){
        color = "black";
        this.orientation = orientation;
    }
}

