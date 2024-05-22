package domain;

import java.util.HashMap;
import java.util.Map;

/**
 * The box class represents a single cell or square on the game board in the Quoridor game.
 */
public class box {

    protected int row;
    protected int column;
    protected boolean isNormal;
    protected Map<String, barrier> barriers;

    /**
     * Constructs a box with the specified row and column.
     * 
     * @param row The row of the box.
     * @param column The column of the box.
     */
    public box(int row, int column){
        this.row = row;
        this.column = column;
        this.barriers = new HashMap<>();
        barriers.put("S", null);
        barriers.put("N", null);
        barriers.put("E", null);
        barriers.put("W", null);
    }    

    /**
     * Gets the row of the box.
     * 
     * @return The row of the box.
     */
    public int getRow(){
        return row;
    }

    /**
     * Gets the column of the box.
     * 
     * @return The column of the box.
     */
    public int getColumn(){
        return column;
    }

    /**
     * Checks if there is a barrier in the specified direction.
     * 
     * @param direction The direction to check.
     * @return true if there is a barrier, false otherwise.
     */
    public boolean thereIsABarrier(String direction){
        barrier barrier = barriers.get(direction);
        return (barrier != null);
    }

    /**
     * Checks if the box has a barrier in the specified direction.
     * 
     * @param direction The direction to check.
     * @return true if the box has a barrier, false otherwise.
     */
    public boolean hasBarrier(String direction){
        return (barriers.get(direction) != null);
    }

    /**
     * Places a barrier in the specified direction.
     * 
     * @param barrier The barrier to place.
     * @param direction The direction to place the barrier.
     */
    public void placeBarrier(barrier barrier, String direction){
        barriers.put(direction, barrier);
    }

    public void eraseBarrier(String direction){
        barriers.put(direction, null);

    }
 }


