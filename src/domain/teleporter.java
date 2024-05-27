package domain;

import java.awt.Color;

/**
 * The teleporter class represents a special box with teleporter attribute in the Quoridor game.
 * It inherits from the box class.
 */
public class teleporter extends box {

    /**
     * Constructs a teleporter box with the specified row and column.
     * 
     * @param row The row of the teleporter box.
     * @param column The column of the teleporter box.
     */
    public teleporter(int row, int column) {
        super(row, column); // Calls the constructor of the superclass (box) with the given row and column.
        this.color = Color.LIGHT_GRAY; // Sets the color of the teleporter box to light gray.
    }

}
