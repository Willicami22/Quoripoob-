package domain;

import java.awt.Color;

/**
 * The doubleShift class represents a special type of box in the Quoridor game board.
 * This type of box is indicated by a yellow color.
 */
public class doubleShift extends box {

    /**
     * Constructs a doubleShift box with the specified row and column.
     * 
     * @param row The row of the box.
     * @param column The column of the box.
     */
    public doubleShift(int row, int column) {
        super(row, column); // Calls the constructor of the superclass (box) with the given row and column.
        this.color = Color.YELLOW; // Sets the color of the doubleShift box to yellow.
    }

}

