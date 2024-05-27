package domain;

/**
 * The star class represents a special box with star attribute in the Quoridor game.
 * It inherits from the box class.
 */
public class star extends box {

    private static final long serialVersionUID = 1L; 

    /**
     * Constructs a star box with the specified row and column.
     * 
     * @param row The row of the star box.
     * @param column The column of the star box.
     */
    public star(int row, int column) {
        super(row, column); // Calls the constructor of the superclass (box) with the given row and column.
        this.color = colorsPalette.PASTEL_PINK; // Sets the color of the star box to pastel pink.
    }

}

