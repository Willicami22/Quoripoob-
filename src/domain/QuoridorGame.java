package domain;

import java.awt.Color;
import java.util.*;

import javax.swing.JOptionPane;


    /**
     * Constructs a new instance of QuoridorGame.
     * Initializes the game with a board size of 9x9 and sets up initial player positions.
     */

public class QuoridorGame  {

    private playerTab[] players;
    private board board;
    private int actualPlayer;
    private boolean vsMachine;
    private String mode;
    private boolean isNew;
    private String difficult;



    public QuoridorGame(){  
        
        isNew=true;
        board= new board(9);
        players= new playerTab[2];
        actualPlayer=0;

    }

        /**
     * Checks if the game is in a new state.
     * 
     * @return true if the game is new, false otherwise.
     */
    public boolean isNew(){
        return isNew;
    }

    /**
     * Sets the difficulty level for the game.
     * 
     * @param difficult The difficulty level to set.
     */
    public void setDifficult(String difficult){
        this.difficult=difficult;
    }

    /**
     * Sets the player mode (human or machine).
     * 
     * @param player The player mode to set. "M" for machine, "H" for human.
     */
    public void setPlayer(String player){
        vsMachine = "M".equals(player);
    }

    /**
     * Sets the game mode.
     * 
     * @param mode The game mode to set.
     */
    public void setMode(String mode){
        this.mode=mode;
    }

    /**
     * Sets up players for the game.
     * 
     * @param player The player number (1 or 2).
     * @param name The name of the player.
     * @param color The color of the player's pieces.
     */
    public void setPlayers(int player, String name, Color color){
        box BoxInit = (player == 1) ? board.getBox(0, 4) : board.getBox(8, 4);
        int winningRow = (player == 1) ? 8 : 0;
        playerTab playerGame = new playerTab(BoxInit, color, name, winningRow);  
        players[player-1] = playerGame;
    }

    /**
     * Moves the player's piece in the specified direction.
     * 
     * @param direction The direction in which to move the player's piece.
     * @throws QuoripoobException If an error occurs during the move.
     */
    public void moveTab(String direction) throws QuoripoobException{
        if (actualPlayer >= 0 && actualPlayer < players.length){
            playerTab currentPlayer = players[actualPlayer];
            box currentBox = currentPlayer.getCurrentBox();
            int[] newPosition= new int[2]; newPosition[0]=currentBox.getRow();newPosition[1]=currentBox.getColumn();
            newPosition=obtainNewPosition(direction, newPosition);
            boolean comprobePlayer=comprobePlayer(newPosition[1],newPosition[0]);
            if(comprobePlayer && !(direction.equals("SE") || direction.equals("SW")|| direction.equals("NE") || direction.equals("WE"))){
                newPosition=obtainNewPosition(direction, newPosition);
            }
            if (isValidMove(newPosition[0], newPosition[1], direction)) {
                currentPlayer.setCurrentBox(board.getBox(newPosition[0], newPosition[1]));
                comprobeWinner();
                actualPlayer = (actualPlayer + 1) % players.length;
            }
        }
    }

    /**
     * Obtains the new position based on the current position and direction.
     * 
     * @param direction The direction in which to move.
     * @param newPosition The current position of the player's piece.
     * @return The new position after moving in the specified direction.
     */
    public int[] obtainNewPosition(String direction, int[] newPosition){
        switch (direction) {
            case "S":
                newPosition[0]--;
                break;
            case "N":
                newPosition[0]++;
                break;
            case "E":
                newPosition[1]--;
                break;
            case "W":
                newPosition[1]++;
                break;
            case "SE":
                newPosition[1]++;
                newPosition[0]--;
                break;
            case "SW":
                newPosition[1]--;
                newPosition[0]--;
                break;
            case "NE":
                newPosition[1]++;
                newPosition[0]++;
                break;
            case "NW":
                newPosition[1]--;
                newPosition[0]++;
                break;
            default:
                break;
        }
        return newPosition;
    }

    /**
     * Places a barrier on the board at the specified location and orientation.
     * 
     * @param rowInit The initial row position of the barrier.
     * @param columnInit The initial column position of the barrier.
     * @param orientation The orientation of the barrier ("H" for horizontal, "V" for vertical).
     * @throws QuoripoobException If the barrier placement is invalid.
     */
    public void putBarrier(int rowInit, int columnInit, String orientation) throws QuoripoobException {
        if (!isValidBarrierPlacement(rowInit, columnInit, orientation)) {
            throw new QuoripoobException("Invalid barrier placement");
        }
        if (orientation.equals("H")) {
            barrier barrier= new barrier(true);
            board.getBox(rowInit, columnInit).placeBarrier(barrier,"S");
            board.getBox(rowInit, columnInit + 1).placeBarrier(barrier,"S");
            board.getBox(rowInit+1, columnInit).placeBarrier(barrier,"N");
            board.getBox(rowInit+1, columnInit + 1).placeBarrier(barrier,"N");
        } else if (orientation.equals("V")) {
            barrier barrier= new barrier(false);
            board.getBox(rowInit, columnInit-1).placeBarrier(barrier,"E");
            board.getBox(rowInit , columnInit).placeBarrier(barrier,"W");
            board.getBox(rowInit+1, columnInit-1).placeBarrier(barrier,"E");
            board.getBox(rowInit+1 , columnInit).placeBarrier(barrier,"W");
        } else {
            throw new QuoripoobException("Invalid orientation");
        }
    }

    /**
     * Checks if a barrier placement is valid.
     * 
     * @param rowInit The initial row position of the barrier.
     * @param columnInit The initial column position of the barrier.
     * @param orientation The orientation of the barrier ("H" for horizontal, "V" for vertical).
     * @return true if the barrier placement is valid, false otherwise.
     */
    public boolean isValidBarrierPlacement(int rowInit, int columnInit, String orientation){
        if (rowInit < 0 && rowInit > board.getSize() && columnInit < 0 && columnInit > board.getSize() ){
            return false;
        }

        if ((orientation.equals("H") && (columnInit == 8 || columnInit == 9)) || (orientation.equals("V") && (rowInit == 8 || rowInit == 9))) {
            return false;
        }

        if ((orientation.equals("H") && (rowInit == 0 || rowInit == 9)) || (orientation.equals("V") && (columnInit == 0 || columnInit == 9))) {
            return false;
        }

        if (orientation.equals("H")) {
            return !board.getBox(rowInit, columnInit).hasBarrier("S") &&
                   !board.getBox(rowInit, columnInit + 1).hasBarrier("S") &&
                   !board.getBox(rowInit + 1, columnInit).hasBarrier("N") &&
                   !board.getBox(rowInit + 1, columnInit + 1).hasBarrier("N");
        } else {
            return !board.getBox(rowInit, columnInit - 1).hasBarrier("E") &&
                   !board.getBox(rowInit, columnInit).hasBarrier("W") &&
                   !board.getBox(rowInit + 1, columnInit - 1).hasBarrier("E") &&
                   !board.getBox(rowInit + 1, columnInit).hasBarrier("W");
        }
    }

    /**
     * Plays the machine's turn based on the selected difficulty level.
     */
    public void playMachine(){
        if(difficult.equals("begginer")){
            Random random = new Random();
            int randomNumber = random.nextInt(2) + 1;

            if(randomNumber == 1){
                moveTabBegginer();
            }
            else {
                putBarrierBegginer();
            }
        }
        else if(difficult.equals("intermediate")){
            // Implement intermediate difficulty level logic
        }
        else{
            // Implement advanced difficulty level logic
        }
    }

    /**
     * Moves the player's piece in the beginner mode.
     */
    public void moveTabBegginer(){
        // Implement movement logic for beginner mode
    }

    /**
     * Places a barrier in the beginner mode.
     */
    public void putBarrierBegginer(){
        // Implement barrier placement logic for beginner mode
    }

    /**
     * Checks if a barrier placement is valid.
     * 
     * @return true if the barrier placement is valid, false otherwise.
     */
    public boolean isValidBarrier(){
        boolean isValid = false;
        // Add logic to check barrier validity
        return isValid;
    }

    /**
     * Checks if a move to the specified position in the specified direction is valid.
     * 
     * @param newRow The new row position.
     * @param newColumn The new column position.
     * @param direction The direction of the move.
     * @return true if the move is valid, false otherwise.
     */
    public boolean isValidMove(int newRow, int newColumn, String direction){
        boolean isValid = true;
        box newBox = board.getBox(newRow, newColumn);

        if(direction.equals("S") || direction.equals("E") || direction.equals("N") || direction.equals("W")){
            boolean comprobe = checkOrthogonalMovements(direction, newBox);

            if(comprobe){
                isValid = false;
            }
        }
        else{
            boolean comprobe = checkDiagonalMovements(direction, newBox);
            if(comprobe){
                isValid = false;
            }
        }
        
        if (newRow < 0 && newRow > board.getSize() && newColumn < 0 && newColumn > board.getSize()){
            isValid = false;
        }
        
        return isValid;
    }

    /**
     * Checks if there are barriers obstructing the orthogonal movements.
     * 
     * @param direction The direction of movement.
     * @param newBox The box to move to.
     * @return true if there is a barrier obstructing the movement, false otherwise.
     */
    private boolean checkOrthogonalMovements(String direction, box newBox){
        boolean comprobe = false;
        if(direction.equals("S")){
            comprobe = newBox.thereIsABarrier("N");
        }
        else if(direction.equals("N")){
            comprobe = newBox.thereIsABarrier("S");
        }
        else if(direction.equals("E")){
            comprobe = newBox.thereIsABarrier("W");
        }
        else{
            comprobe = newBox.thereIsABarrier("E");
        }
        return comprobe;
    }

    /**
     * Checks if there are barriers obstructing the diagonal movements.
     * 
     * @param direction The direction of movement.
     * @param newBox The box to move to.
     * @return true if there is a barrier obstructing the movement, false otherwise.
     */
    private boolean checkDiagonalMovements(String direction, box newBox) {
        boolean isPossible = true;
        String enemyPosition = "";
    
        playerTab currentPlayer = players[actualPlayer];
        box currentBox = currentPlayer.getCurrentBox();
    
        int[] newPosition1 = new int[2];newPosition1[0] = currentBox.getRow();newPosition1[1] = currentBox.getColumn();
        int[] newPosition2 = new int[2];newPosition2[0] = currentBox.getRow();newPosition2[1] = currentBox.getColumn();
    
        char firstDirection = direction.charAt(0);
        char secondDirection = direction.charAt(1);
    
        newPosition1 = obtainNewPosition(String.valueOf(firstDirection), newPosition1);
        newPosition2 = obtainNewPosition(String.valueOf(secondDirection), newPosition2);
    
        if (!(comprobePlayer(newPosition1[0], newPosition1[1]) || comprobePlayer(newPosition2[0], newPosition2[1]))) {
            isPossible = false;
        } else {
            if (comprobePlayer(newPosition1[0], newPosition1[1])) {
                enemyPosition = String.valueOf(firstDirection);
            } else if (comprobePlayer(newPosition2[0], newPosition2[1])) {
                enemyPosition = String.valueOf(secondDirection);
            }
    
            if (!checkOrthogonalMovements(enemyPosition, newBox)) {
                isPossible = false;
            }
        }
        
        return isPossible;
    }

    /**
     * Checks if a player has won the game.
     */
    public void comprobeWinner() {
        for (playerTab player : players) {
            if (player.hasWon()) {
                JOptionPane.showMessageDialog(null, "Player " + player.getName() + " has won!");
                endGame("Player Win");
                return;  
            }
        }
    }
    

       /**
     * Allows a player to give up, ending the game.
     */
    public void giveUp(){
        playerTab lostPlayer = players[actualPlayer]; 
        JOptionPane.showMessageDialog(null, "Player " + lostPlayer.getName() + " has given up.");
        endGame("Give up");
    }

    /**
     * Checks if a player is present at the specified row and column position.
     * 
     * @param row The row position to check.
     * @param column The column position to check.
     * @return true if a player is present at the specified position, false otherwise.
     */
    public boolean comprobePlayer(int row, int column){
        boolean comprobe = false;
        int indexOtherPlayer = (actualPlayer + 1) % players.length;
        playerTab otherPlayer = players[indexOtherPlayer];
        box boxOther = otherPlayer.getCurrentBox();
        if(boxOther.getColumn() == column && boxOther.getRow() == row){
            comprobe = true;
        }
        return comprobe;
    }

    /**
     * Ends the game with the specified cause.
     * 
     * @param cause The cause of the game end.
     */
    public void endGame(String cause){
        JOptionPane.showMessageDialog(null, "Game over. Thanks for playing!");
    }

    /**
     * Retrieves the array of players.
     * 
     * @return An array containing the players.
     */
    public playerTab[] getPlayers(){
        return players;
    }

    /**
     * Retrieves the game board.
     * 
     * @return The game board.
     */
    public board getBoard(){
        return board;
    }
}

