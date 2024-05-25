package domain;

import java.io.Serializable;

/**
 * The board class represents the game board in the Quoridor game.
 */
public class board implements Serializable {

    private static final long serialVersionUID = 1L; 
    private int size;
    private box[][] boxes;

    /**
     * Constructs a board with the specified size.
     * 
     * @param size The size of the board.
     */
    public board(int size) {
        this.size = size;
        boxes = new box[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                boxes[i][j] = new box(i, j);
            }        
        }
        boxes[1][4]=new doubleShift(1, 4);
        boxes[1][5]=new teleporter(1, 5);
        boxes[1][3]=new goBack(1, 3);
    }

    /**
     * Gets the box located at the specified row and column indices.
     * 
     * @param row The row index of the box.
     * @param col The column index of the box.
     * @return The box located at the specified row and column indices.
     * @throws IndexOutOfBoundsException if the indices are out of bounds
     */
    public box getBox(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IndexOutOfBoundsException("Row or column index out of bounds");
        }
        return boxes[row][col];
    }

    /**
     * Gets the size of the board.
     * 
     * @return The size of the board.
     */
    public int getSize() {
        return size;
    }

    public box[][] getBoxes(){
        return boxes;
    }
}