package domain;

/**
 * The barrier class represents a barrier in the Quoridor game.
 */
public class barrier {

    protected String color;
    private boolean orientation;
    protected box positionI; 

    /**
     * Constructs a barrier with the specified orientation.
     * 
     * @param orientation The orientation of the barrier.
     */
    public barrier(boolean orientation){
        color = "black";
        this.orientation = orientation;
        positionI = null;
    }
}

