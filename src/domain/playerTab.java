package domain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;

public class playerTab {

    private Color color;
    private ArrayList<box> movements; 
    private String name;
    private Map<String, Integer> barriers;
    protected box position;
    private int winningRow;

    /**
     * Constructs a player with the specified initial position, color, name, and winning row.
     * 
     * @param boxPosition The initial position of the player.
     * @param color The color of the player.
     * @param name The name of the player.
     * @param winningRow The row at which the player wins the game.
     */
    public playerTab(box boxPosition, Color color, String name, int winningRow ){
        position = boxPosition;
        movements = new ArrayList<>();
        movements.add(position);
        this.color = color;
        this.name = name;
        this.winningRow = winningRow;
    }

    /**
     * Sets the barriers for the player.
     * 
     * @param barriers The map containing the barriers.
     */
    public void setBarriers(Map<String, Integer> barriers){
        this.barriers = barriers;
    }

    /**
     * Gets the current box position of the player.
     * 
     * @return The current box position.
     */
    public box getCurrentBox(){
        return position;
    }

    /**
     * Sets the current box position of the player.
     * 
     * @param newBox The new box position.
     */
    public void setCurrentBox(box newBox){
        position = newBox;
        movements.add(newBox);
    }

    /**
     * Checks if the player has won the game.
     * 
     * @return true if the player has won, false otherwise.
     */
    public boolean hasWon() {
        return position.getRow() == winningRow;
    }

    /**
     * Gets the name of the player.
     * 
     * @return The name of the player.
     */
    public String getName(){
        return name;
    }

    /**
     * Gets the color of the player.
     * 
     * @return The color of the player.
     */
    public Color getColor(){
        return color;
    }

    public int getWinningRow(){
        return winningRow;
    }
}
