package domain;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The playerTab class represents a player in the Quoridor game.
 * It contains information about the player's position, color, name, barriers, movements, and other attributes.
 */
public class playerTab implements Serializable {

    private static final long serialVersionUID = 1L; 
    private Color color; // The color of the player's token.
    private Color originalColor; // The original color of the player's token.
    private ArrayList<box> movements; // The list of movements made by the player.
    private String name; // The name of the player.
    private Map<String, Integer> barriers; // The map of barriers held by the player.
    protected box position; // The current position of the player on the game board.
    private int winningRow; // The row at which the player wins the game.
    private ArrayList<String> directions; // The list of directions in which the player moves.
    private boolean star; // Indicates if the player is in star mode.
    private int numStar; // The number of turns the player has been in star mode.

    /**
     * Constructs a player with the specified initial position, color, name, and winning row.
     * 
     * @param boxPosition The initial position of the player.
     * @param color The color of the player.
     * @param name The name of the player.
     * @param winningRow The row at which the player wins the game.
     */
    public playerTab(box boxPosition, Color color, String name, int winningRow) {
        position = boxPosition;
        movements = new ArrayList<>();
        movements.add(position);
        this.color = color;
        originalColor = color;
        this.name = name;
        this.winningRow = winningRow;
        this.directions = new ArrayList<>();
        barriers = new HashMap<>();

        // Initialize the barriers map with default values
        barriers.put("Allied", 0);
        barriers.put("Temporary", 0);
        barriers.put("Normal", 0);
        barriers.put("Large", 0);
    }

    /**
     * Initializes the barriers held by the player with the specified quantities.
     * 
     * @param normal The quantity of normal barriers.
     * @param allied The quantity of allied barriers.
     * @param temporary The quantity of temporary barriers.
     * @param large The quantity of large barriers.
     */
    public void startBarriers(int normal, int allied, int temporary, int large) {
        barriers.put("Allied", allied);
        barriers.put("Temporary", temporary);
        barriers.put("Normal", normal);
        barriers.put("Large", large);
    }

    /**
     * Gets the current box position of the player.
     * 
     * @return The current box position.
     */
    public box getCurrentBox() {
        return position;
    }

    /**
     * Sets the current box position of the player.
     * 
     * @param newBox The new box position.
     */
    public void setCurrentBox(box newBox) {
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
    public String getName() {
        return name;
    }

    /**
     * Gets the color of the player.
     * 
     * @return The color of the player.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Gets the winning row of the player.
     * 
     * @return The winning row of the player.
     */
    public int getWinningRow() {
        return winningRow;
    }

    /**
     * Gets the list of movements made by the player.
     * 
     * @return The list of movements.
     */
    public ArrayList<box> getMovements() {
        return movements;
    }

    /**
     * Gets the list of directions in which the player moves.
     * 
     * @return The list of directions.
     */
    public ArrayList<String> getDirections() {
        return directions;
    }

    /**
     * Adds a direction to the list of directions.
     * 
     * @param direction The direction to add.
     */
    public void setDirections(String direction) {
        directions.add(direction);
    }

    /**
     * Removes the last 'num' movements from the list of movements.
     * 
     * @param num The number of movements to remove.
     */
    public void removeLastMovements(int num) {
        for (int z = 0; z < num; z++) {
            movements.remove(movements.size() - 1);
        }
    }

    /**
     * Removes the last 'num' directions from the list of directions.
     * 
     * @param num The number of directions to remove.
     */
    public void removeLastDirections(int num) {
        for (int z = 0; z < num; z++) {
            directions.remove(directions.size() - 1);
        }
    }

    /**
     * Updates the number of temporary barriers held by the player.
     */
    public void updateBarrierTemporaly() {
        Integer currentValue = barriers.get("Temporary");
    
        // Increment the value by one
        int updatedValue = currentValue + 1;
        
        // Update the value in the map
        barriers.put("Temporary", updatedValue);
    }


    /**
     * Gets the number of barriers of the specified type held by the player.
     * 
     * @param type The type of barriers.
     * @return The number of barriers of the specified type.
     */
    public int getNumberBarrier(String type) {
        return barriers.get(type);
    }

    /**
     * Updates the number of barriers of the specified type held by the player.
     * 
     * @param type The type of barriers to update.
     */
    public void updateBarrier(String type) {
        Integer currentValue = barriers.get(type);
    
        // Decrease the value by one
        int updatedValue = currentValue - 1;
        
        // Update the value in the map
        barriers.put(type, updatedValue);
    }

    /**
     * Activates the star mode for the player, changing the player's color to purple.
     */
    public void nowStar() {
        color = colorsPalette.VIBRANT_PURPLE;
        star = true;
        numStar = 0;
    }

    /**
     * Updates the star mode for the player, changing the player's color back to the original color after two turns.
     */
    public void updateStar() {
        if (star) {
            numStar += 1;
            if (numStar == 2) {
                star = false;
                this.color = originalColor;
                numStar = 0;
            }
        }
    }

    /**
     * Checks if the player is currently in star mode.
     * 
     * @return true if the player is in star mode, false otherwise.
     */
    public boolean isStar() {
        return star;
    }
}
