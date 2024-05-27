package domain;

import java.awt.Color;

/**
 * The goBack class represents a special type of box in the Quoridor game board.
 * This type of box is indicated by a magenta color.
 */
public class goBack extends box {

    /**
     * Constructs a goBack box with the specified row and column.
     * 
     * @param row The row of the box.
     * @param column The column of the box.
     */
    public goBack(int row, int column) {
        super(row, column); // Calls the constructor of the superclass (box) with the given row and column.
        this.color = Color.MAGENTA; // Sets the color of the goBack box to magenta.
    }

}

