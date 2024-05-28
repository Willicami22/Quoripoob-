package domain;

import java.awt.Color;
import java.util.*;
import java.io.*;
import javax.swing.JOptionPane;

/**
 * The QuoridorGame class represents the main game logic of the Quoridor game.
 * It manages players, the game board, and various game settings.
 */
public class QuoridorGame implements Serializable {

    private static final long serialVersionUID = 1L; 
    private playerTab[] players; // Array to store players participating in the game.
    private board board; // Instance of the game board.
    private int actualPlayer; // Index of the currently active player.
    public boolean vsMachine = true; // Flag indicating if the game is played against a machine or human.
    private String mode; // The game mode (e.g., "Single Player", "Multiplayer").
    private String difficult; // The difficulty level for the machine player.
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Array defining possible movement directions.

    /**
     * Constructs a new instance of QuoridorGame.
     * Initializes the game with a board size of 9x9 and sets up initial player positions.
     */
    public QuoridorGame() {
        players = new playerTab[2]; // Initializes the array to store two players.
        actualPlayer = 0; // Initializes the active player index to 0.
    }

    /**
     * Sets the difficulty level for the game.
     * 
     * @param difficult The difficulty level to set.
     */
    public void setDifficult(String difficult) {
        this.difficult = difficult;
    }

    public String getDifficult(){
        return difficult;
    }

    /**
     * Sets the player mode (human or machine).
     * 
     * @param player The player mode to set. "M" for machine, "H" for human.
     */
    public void setPlayer(String player) {
        vsMachine = (player.equals("M")); // Sets vsMachine flag based on the player mode.
    }

    /**
     * Sets the game mode.
     * 
     * @param mode The game mode to set.
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMode(){
        return mode;
    }

    /**
     * Sets up players for the game.
     * 
     * @param player The player number (1 or 2).
     * @param name The name of the player.
     * @param color The color of the player's pieces.
     */
    public void setPlayers(int player, String name, Color color) {
        // Determines the initial position and winning row based on the player number.
        box BoxInit = (player == 0) ? board.getBox(0, (int)Math.ceil(board.getSize() / 2.0) - 1) : board.getBox(board.getSize() - 1, (int)Math.ceil(board.getSize() / 2.0) - 1);
        int winningRow = (player == 0) ? board.getSize()-1 : 0;

        // Creates a new player object based on the player mode (human or machine).
        if (player == 0 || (player == 1 && !vsMachine)) {
            player playerGame = new player(BoxInit, color, name, winningRow);
            players[player] = playerGame;
        } else {
            machine playerGame = new machine(BoxInit, color, name, winningRow, difficult);
            players[player] = playerGame;
        }
    }

    /**
     * Sets the number of barriers for each player.
     * 
     * @param normalBarriers The number of normal barriers.
     * @param alliedBarriers The number of allied barriers.
     * @param temporaryBarriers The number of temporary barriers.
     * @param largeBarriers The number of large barriers.
     */
    public void setBarriers(int normalBarriers, int alliedBarriers, int temporaryBarriers, int largeBarriers) {
        for(playerTab p : players) {
            p.startBarriers(normalBarriers, alliedBarriers, temporaryBarriers, largeBarriers);
        }
    }

    /**
     * Sets up the game board with the specified size.
     * 
     * @param size The size of the game board.
     */
    public void setBoard(int size) {
        board = new board(size);
    }

  /**
     * Moves the player's piece in the specified direction.
     * 
     * @param direction The direction in which to move the player's piece.
     * @throws QuoripoobException If an error occurs during the move.
     */
    public void moveTab(String direction) throws QuoripoobException {
        validatePlayerIndex();
    
        playerTab currentPlayer = players[actualPlayer];
        box currentBox = currentPlayer.getCurrentBox();
        int[] newPosition = obtainNewPosition(direction, new int[]{currentBox.getRow(), currentBox.getColumn()});
    
        validateNewPosition(newPosition);
    
        if (shouldRecalculatePosition(direction, newPosition,currentPlayer.isStar()) && isValidMove(newPosition[0], newPosition[1], direction, currentPlayer.isStar())) {
            newPosition = obtainNewPosition(direction, newPosition);
        }
    
        validateFinalPosition(newPosition, direction, currentPlayer, currentBox);
    
        updatePlayerPosition(currentPlayer, newPosition, direction);
        handleSpecialBoxes(currentPlayer, newPosition);
    
        if (!(board.getBox(newPosition[0], newPosition[1]) instanceof doubleShift)) {
            moveToNextPlayer();
            if (vsMachine) {
                playMachine();
            }
        }
    }
    
    /**
     * Validates whether the player index is within bounds.
     * 
     * @throws QuoripoobException If the player index is out of bounds.
     */
    private void validatePlayerIndex() throws QuoripoobException {
        if (actualPlayer < 0 || actualPlayer >= players.length) {
            throw new QuoripoobException(QuoripoobException.OutOfIndex);
        }
    }
    
    /**
     * Validates whether the new position is within the bounds of the board.
     * 
     * @param newPosition The new position to validate.
     * @throws QuoripoobException If the new position is out of bounds.
     */
    public void validateNewPosition(int[] newPosition) throws QuoripoobException {
        int boardSize = board.getSize();
        if (newPosition[0] < 0 || newPosition[1] >= boardSize || newPosition[1] < 0 || newPosition[0] >= boardSize) {
            
            throw new QuoripoobException(QuoripoobException.OutOfBoard);
        }
    }
    
    /**
     * Determines whether to recalculate the position based on the direction and the presence of another player.
     * 
     * @param direction The direction in which to move.
     * @param newPosition The new position to consider.
     * @param star A boolean indicating if the player has a star power-up.
     * @return True if the position should be recalculated, false otherwise.
     * @throws QuoripoobException If the direction is invalid.
     */
    private boolean shouldRecalculatePosition(String direction, int[] newPosition, boolean star) throws QuoripoobException {
        return comprobePlayer(newPosition[0], newPosition[1]) &&
               !isDiagonalDirection(direction);
    }
    
    /**
     * Checks if the direction represents a diagonal movement.
     * 
     * @param direction The direction to check.
     * @return True if the direction is diagonal, false otherwise.
     */
    public boolean isDiagonalDirection(String direction) {
        return direction.equals("SE") || direction.equals("SW") || direction.equals("NE") || direction.equals("NW");
    }
    
    /**
     * Validates the final position after considering the movement direction.
     * 
     * @param newPosition The new position after movement.
     * @param direction The direction of movement.
     * @param currentPlayer The current player.
     * @param currentBox The current box of the player.
     * @throws QuoripoobException If the final position is invalid.
     */
    private void validateFinalPosition(int[] newPosition, String direction, playerTab currentPlayer, box currentBox) throws QuoripoobException {
        if (isDiagonalDirection(direction) && comprobePlayer(newPosition[0], newPosition[1])) {
            throw new QuoripoobException(QuoripoobException.InvalidMove);
        } else if (!isValidMove(newPosition[0], newPosition[1], direction, currentPlayer.isStar()) && !(currentBox instanceof teleporter)) {
            throw new QuoripoobException(QuoripoobException.InvalidMove);
        }
    }
    
    /**
     * Updates the player's position on the board after movement.
     * 
     * @param currentPlayer The current player.
     * @param newPosition The new position after movement.
     * @param direction The direction of movement.
     */
    private void updatePlayerPosition(playerTab currentPlayer, int[] newPosition, String direction) {
        currentPlayer.updateStar();
        currentPlayer.setCurrentBox(board.getBox(newPosition[0], newPosition[1]));
        currentPlayer.setDirections(direction);
        board.comprobeBarrier();
        comprobeWinner();
    }
    
    
    /**
     * Handles special boxes that the player might encounter after movement.
     * 
     * @param currentPlayer The current player.
     * @param newPosition The new position after movement.
     * @throws QuoripoobException If there is an error while handling special boxes.
     */
    private void handleSpecialBoxes(playerTab currentPlayer, int[] newPosition) throws QuoripoobException {
        box newBox = board.getBox(newPosition[0], newPosition[1]);
    
        if (newBox instanceof goBack) {
            handleGoBack(currentPlayer);
        } else if (newBox instanceof star) {
            currentPlayer.nowStar();
        }
    }
    
    /**
     * Handles the "go back" special box if encountered after movement.
     * 
     * @param currentPlayer The current player.
     * @throws QuoripoobException If there are not enough movements to go back.
     */
    private void handleGoBack(playerTab currentPlayer) throws QuoripoobException {
        ArrayList<box> movements = currentPlayer.getMovements();
        ArrayList<String> directions = currentPlayer.getDirections();
    
        if (movements.size() >= 3 && directions.size() >= 2) {
            box lastBox = movements.get(movements.size() - 2);
            box lastSecondBox = movements.get(movements.size() - 3);
            String lastDirection = directions.get(directions.size() - 1);
            String lastSecondDirection = directions.get(directions.size() - 2);
    
            if (isValidMove(lastBox.getRow(), lastBox.getColumn(), getOppositeDirection(lastDirection), currentPlayer.isStar()) &&
                isValidMove(lastSecondBox.getRow(), lastSecondBox.getColumn(), getOppositeDirection(lastSecondDirection), currentPlayer.isStar())) {
                currentPlayer.setCurrentBox(lastSecondBox);
            }
        } else {
            throw new QuoripoobException(QuoripoobException.GoBack);
        }
    }
    
    /**
     * Moves to the next player in the game.
     */
    private void moveToNextPlayer() {
        actualPlayer = (actualPlayer + 1) % players.length;
    }
    
    /**
     * Checks if there is a player at the specified row and column.
     * 
     * @param row The row to check.
     * @param column The column to check.
     * @return True if a player is found, false otherwise.
     */
    public boolean comprobePlayer(int row, int column) {
        int indexOtherPlayer = (actualPlayer + 1) % players.length;
        playerTab otherPlayer = players[indexOtherPlayer];
        box boxOther = otherPlayer.getCurrentBox();
        return boxOther.getColumn() == column && boxOther.getRow() == row;
    }
    
    /**
     * Checks if the proposed move is valid.
     * 
     * @param newRow The new row after movement.
     * @param newColumn The new column after movement.
     * @param direction The direction of movement.
     * @param star A boolean indicating if the player has a star power-up.
     * @return True if the move is valid, false otherwise.
     * @throws QuoripoobException If there is an error while validating the move.
     */
    public boolean isValidMove(int newRow, int newColumn, String direction, boolean star) throws QuoripoobException {
        box newBox = board.getBox(newRow, newColumn);
    
        if (isOrthogonalDirection(direction)) {
            return !checkOrthogonalMovements(direction, newBox, star);
        } else {
            return checkDiagonalMovements(direction, newBox,star);
        }
    }
    
    /**
     * Checks if the direction represents an orthogonal movement.
     * 
     * @param direction The direction to check.
     * @return True if the direction is orthogonal, false otherwise.
     */
    private boolean isOrthogonalDirection(String direction) {
        return direction.equals("S") || direction.equals("E") || direction.equals("N") || direction.equals("W");
    }
    
    /**
     * Checks for barriers in orthogonal movements.
     * 
     * @param direction The direction of movement.
     * @param newBox The box to move into.
     * @param star A boolean indicating if the player has a star power-up.
     * @return True if there is a barrier, false otherwise.
     */
    private boolean checkOrthogonalMovements(String direction, box newBox, boolean star) {
        boolean hasBarrier = newBox.hasBarrier(direction);
    
        if (hasBarrier) {
            barrier barrier = newBox.getBarrier(direction);
            if (barrier instanceof allied) {
                allied alliedBarrier = (allied) barrier;
                if (alliedBarrier.getOwner() == getActualPlayer()) {
                    return false;
                }
            } else if (barrier instanceof large && star) {
                return false;
            }
        }
    
        return hasBarrier;
    }

    /**
     * Checks for barriers in diagonal movements.
     * 
     * @param direction The direction of diagonal movement.
     * @param currentBox The current box of the player.
     * @param star A boolean indicating if the player has a star power-up.
     * @return True if there are valid diagonal movements, false otherwise.
     * @throws QuoripoobException If there is an error while checking diagonal movements.
     */
    private boolean checkDiagonalMovements(String direction, box currentBox, boolean star) throws QuoripoobException {
        boolean isPossible = false;
    
        int[] orthogonalPosition1 = {currentBox.getRow(), currentBox.getColumn()};
        int[] orthogonalPosition2 = {currentBox.getRow(), currentBox.getColumn()};
    
        char firstDirection = direction.charAt(0);
        char secondDirection = direction.charAt(1);
    
        orthogonalPosition1 = obtainNewPosition(String.valueOf(firstDirection), orthogonalPosition1);
        orthogonalPosition2 = obtainNewPosition(String.valueOf(secondDirection), orthogonalPosition2);
    
        // Check for an adjacent player and a barrier preventing the jump in the first direction
        if (comprobePlayer(orthogonalPosition1[0], orthogonalPosition1[1])) {
            box adjacentBox1 = board.getBox(orthogonalPosition1[0], orthogonalPosition1[1]);
            if (!checkOrthogonalMovements(String.valueOf(firstDirection), adjacentBox1, star)) {
                isPossible = true;
            }
        }
    
        // Check for an adjacent player and a barrier preventing the jump in the second direction
        if (comprobePlayer(orthogonalPosition2[0], orthogonalPosition2[1])) {
            box adjacentBox2 = board.getBox(orthogonalPosition2[0], orthogonalPosition2[1]);
            if (!checkOrthogonalMovements(String.valueOf(secondDirection), adjacentBox2, star)) {
                isPossible = true;
            }
        }
    
        return isPossible;
    }
    
    

    /**
     * Retrieves the opposite direction of the given direction.
     * 
     * @param direction The input direction.
     * @return The opposite direction.
     */
    public String getOppositeDirection(String direction) throws QuoripoobException{
        switch (direction) {
            case "N":
                return "S";  // Moving north has the opposite direction of south
            case "S":
                return "N";  // Moving south has the opposite direction of north
            case "E":
                return "W"; 

            case "W":
                return "E";  // Mover hacia el oeste tiene la dirección opuesta hacia el este
            case "NE":
                return "SW";  // Mover hacia el noreste tiene la dirección opuesta hacia el suroeste
            case "NW":
                return "SE";  // Mover hacia el noroeste tiene la dirección opuesta hacia el sureste
            case "SE":
                return "NW";  // Mover hacia el sureste tiene la dirección opuesta hacia el noroeste
            case "SW":
                return "NE";  // Mover hacia el suroeste tiene la dirección opuesta hacia el noreste
            default:
                throw new QuoripoobException(QuoripoobException.InvalidDirection);  // Manejo de dirección inválida
        }
    }
    
    

   /**
 * Obtains the new position based on the current position and direction.
 * 
 * @param direction The direction in which to move.
 * @param newPosition The current position of the player's piece.
 * @return The new position after moving in the specified direction.
 * @throws QuoripoobException If the direction is invalid.
 */
public int[] obtainNewPosition(String direction, int[] newPosition) throws QuoripoobException {
    switch (direction) {
        case "N":
            newPosition[0]++;  // Moving north increments the X coordinate
            break;
        case "S":
            newPosition[0]--;  // Moving south decrements the X coordinate
            break;
        case "E":
            newPosition[1]--;  // Moving east decrements the Y coordinate
            break;
        case "W":
            newPosition[1]++;  // Moving west increments the Y coordinate
            break;
        case "NE":
            newPosition[0]++;  // Moving northeast increments the X coordinate
            newPosition[1]++;  // Moving northeast increments the Y coordinate
            break;
        case "NW":
            newPosition[0]++;  // Moving northwest increments the X coordinate
            newPosition[1]--;  // Moving northwest decrements the Y coordinate
            break;
        case "SE":
            newPosition[0]--;  // Moving southeast decrements the X coordinate
            newPosition[1]++;  // Moving southeast increments the Y coordinate
            break;
        case "SW":
            newPosition[0]--;  // Moving southwest decrements the X coordinate
            newPosition[1]--;  // Moving southwest decrements the Y coordinate
            break;
        default:
            throw new QuoripoobException(QuoripoobException.InvalidDirection);
    }
    return newPosition;
}

/**
 * Places a barrier on the board at the specified location and orientation.
 * 
 * @param rowInit The initial row position of the barrier.
 * @param columnInit The initial column position of the barrier.
 * @param orientation The orientation of the barrier ("H" for horizontal, "V" for vertical).
 * @throws QuoripoobException If the barrier placement is invalid or if the player has insufficient barriers.
 */
public void putBarrier(int rowInit, int columnInit, String orientation, String type) throws QuoripoobException {

    if (getActualPlayer().getNumberBarrier(type) > 0) {

        if (!isValidBarrierPlacement(rowInit, columnInit, orientation, type)) {
            throw new QuoripoobException(QuoripoobException.InvalidPlaceBarrier);
        }
        barrier barrier = createBarrier(type, orientation);


        setBarrier(rowInit, columnInit, barrier, orientation);

        if (!canPlayerWin(players[0]) || !canPlayerWin(players[1])) {
            eraseBarrier(orientation, rowInit, columnInit, barrier);
        }

        board.comprobeBarrier();
        getActualPlayer().updateBarrier(type);
        moveToNextPlayer();


        if (vsMachine) {
            playMachine();
        }
    } else {
        throw new QuoripoobException(QuoripoobException.DontHaveBarriers);
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
private boolean isValidBarrierPlacement(int rowInit, int columnInit, String orientation, String type) {

    if (!checkLimits(rowInit, columnInit, orientation, type)) {
        return false;
    }

    // Check if there are barriers in adjacent positions
    return checkBarriers(orientation, rowInit, columnInit, type);

}

/**
 * Creates a barrier object based on the given type and orientation.
 * 
 * @param type The type of barrier.
 * @param orientation The orientation of the barrier.
 * @return The created barrier object.
 */
private barrier createBarrier(String type, String orientation) {
    if (type.equals("Large")) {
        return new large(true);
    } else if (type.equals("Allied")) {
        return new allied(getActualPlayer());
    } else if (type.equals("Temporary")) {
        return new temporary(getActualPlayer(), orientation);
    } else {
        return new barrier(true);
    }
}

/**
 * Sets a barrier on the board at the specified location and orientation.
 * 
 * @param rowInit The initial row position of the barrier.
 * @param columnInit The initial column position of the barrier.
 * @param barrier The barrier object to place.
 * @param orientation The orientation of the barrier ("H" for horizontal, "V" for vertical).
 * @throws QuoripoobException If the orientation is invalid.
 */
private void setBarrier(int rowInit, int columnInit, barrier barrier, String orientation) throws QuoripoobException {

    if (orientation.equals("H")) {
        board.getBox(rowInit, columnInit).placeBarrier(barrier, "N");
        board.getBox(rowInit, columnInit + 1).placeBarrier(barrier, "N");
        board.getBox(rowInit - 1, columnInit).placeBarrier(barrier, "S");
        board.getBox(rowInit - 1, columnInit + 1).placeBarrier(barrier, "S");
        if (barrier instanceof large) {
            board.getBox(rowInit, columnInit + 2).placeBarrier(barrier, "N");
            board.getBox(rowInit - 1, columnInit + 2).placeBarrier(barrier, "S");
        } else if (barrier instanceof temporary) {
            ((temporary) barrier).updateBox(board.getBox(rowInit, columnInit), board.getBox(rowInit, columnInit + 1), board.getBox(rowInit - 1, columnInit), board.getBox(rowInit - 1, columnInit + 1));
        }

    } else if (orientation.equals("V")) {
        board.getBox(rowInit, columnInit).placeBarrier(barrier, "W");
        board.getBox(rowInit + 1, columnInit).placeBarrier(barrier, "W");
        board.getBox(rowInit, columnInit - 1).placeBarrier(barrier, "E");
        board.getBox(rowInit + 1, columnInit - 1).placeBarrier(barrier, "E");
        if (barrier instanceof large) {
            board.getBox(rowInit + 2, columnInit - 1).placeBarrier(barrier, "E");
            board.getBox(rowInit + 2, columnInit - 1).placeBarrier(barrier, "W");
        } else if (barrier instanceof temporary) {
            ((temporary) barrier).updateBox(board.getBox(rowInit, columnInit), board.getBox(rowInit, columnInit + 1), board.getBox(rowInit - 1, columnInit), board.getBox(rowInit - 1, columnInit + 1));
        }
    } else {
        throw new QuoripoobException(QuoripoobException.InvalidDirection);
    }

}


/**
 * Erases a barrier from the board at the specified location and orientation.
 * 
 * @param orientation The orientation of the barrier ("H" for horizontal, "V" for vertical).
 * @param rowInit The initial row position of the barrier.
 * @param columnInit The initial column position of the barrier.
 * @param barrier The barrier object to erase.
 * @throws QuoripoobException If the barrier cannot be erased due to blocking a player's path.
 */
private void eraseBarrier(String orientation, int rowInit, int columnInit, barrier barrier) throws QuoripoobException {
    if (orientation.equals("H")) {
        board.getBox(rowInit, columnInit).eraseBarrier("N");
        board.getBox(rowInit, columnInit + 1).eraseBarrier("N");
        board.getBox(rowInit - 1, columnInit).eraseBarrier("S");
        board.getBox(rowInit - 1, columnInit + 1).eraseBarrier("S");
        if (barrier instanceof large) {
            board.getBox(rowInit, columnInit + 2).eraseBarrier("N");
            board.getBox(rowInit - 1, columnInit + 2).eraseBarrier("S");
        }
    } else {
        board.getBox(rowInit, columnInit).eraseBarrier("W");
        board.getBox(rowInit + 1, columnInit).eraseBarrier("W");
        board.getBox(rowInit, columnInit - 1).eraseBarrier("E");
        board.getBox(rowInit + 1, columnInit - 1).eraseBarrier("E");
        if (barrier instanceof large) {
            board.getBox(rowInit + 2, columnInit - 1).eraseBarrier("E");
            board.getBox(rowInit + 2, columnInit - 1).eraseBarrier("W");
        }
    }
    throw new QuoripoobException(QuoripoobException.PathBlocked);
}

/**
 * Checks if the specified position and orientation are within the board limits.
 * 
 * @param rowInit The initial row position of the barrier.
 * @param columnInit The initial column position of the barrier.
 * @param orientation The orientation of the barrier ("H" for horizontal, "V" for vertical).
 * @param type The type of the barrier.
 * @return true if the placement is within limits, false otherwise.
 */
private boolean checkLimits(int rowInit, int columnInit, String orientation, String type) {
    int boardSize = board.getSize();
    if (rowInit < 0 || rowInit >= boardSize || columnInit < 0 || columnInit >= boardSize) {
        return false;
    }

    if (orientation.equals("H") && !type.equals("Large")) {
        if (columnInit >= boardSize - 1 || rowInit == 0) {
            return false;
        }
    } else if (orientation.equals("V") && !type.equals("Large")) {
        if (rowInit >= boardSize - 1 || columnInit == 0) {
            return false;
        }
    } else if (orientation.equals("H") && type.equals("Large")) {
        if (columnInit >= boardSize - 2 || rowInit == 0) {
            return false;
        }
    } else if (orientation.equals("V") && type.equals("Large")) {
        if (rowInit >= boardSize - 2 || columnInit == 0) {
            return false;
        }
    } else {
        return false;
    }
    return true;
}

/**
 * Checks if there are barriers in adjacent positions.
 * 
 * @param orientation The orientation of the barrier ("H" for horizontal, "V" for vertical).
 * @param rowInit The initial row position of the barrier.
 * @param columnInit The initial column position of the barrier.
 * @param type The type of the barrier.
 * @return true if there are no barriers in adjacent positions, false otherwise.
 */
private boolean checkBarriers(String orientation, int rowInit, int columnInit, String type) {
    if (orientation.equals("H") && type != "Large") {
        return !board.getBox(rowInit - 1, columnInit).hasBarrier("S") &&
               !board.getBox(rowInit - 1, columnInit + 1).hasBarrier("S") &&
               !board.getBox(rowInit, columnInit).hasBarrier("N") &&
               !board.getBox(rowInit, columnInit + 1).hasBarrier("N");
    } else if (orientation.equals("V") && type != "Large") {
        return !board.getBox(rowInit, columnInit - 1).hasBarrier("E") &&
               !board.getBox(rowInit, columnInit).hasBarrier("W") &&
               !board.getBox(rowInit + 1, columnInit - 1).hasBarrier("E") &&
               !board.getBox(rowInit + 1, columnInit).hasBarrier("W");
    } else if (orientation.equals("H") && type == "Large") {
        return !board.getBox(rowInit - 1, columnInit).hasBarrier("S") &&
               !board.getBox(rowInit - 1, columnInit + 1).hasBarrier("S") &&
               !board.getBox(rowInit, columnInit).hasBarrier("N") &&
               !board.getBox(rowInit, columnInit + 1).hasBarrier("N") &&
               !board.getBox(rowInit - 1, columnInit + 2).hasBarrier("S") &&
               !board.getBox(rowInit, columnInit + 2).hasBarrier("N");
    } else if (orientation.equals("V") && type == "Large") {
        return !board.getBox(rowInit, columnInit - 1).hasBarrier("E") &&
               !board.getBox(rowInit, columnInit).hasBarrier("W") &&
               !board.getBox(rowInit + 1, columnInit - 1).hasBarrier("E") &&
               !board.getBox(rowInit + 1, columnInit).hasBarrier("W") &&
               !board.getBox(rowInit + 2, columnInit - 1).hasBarrier("E") &&
               !board.getBox(rowInit + 2, columnInit).hasBarrier("W");
    } else {
        return false;
    }
}

    /**
     * Plays the machine's turn based on the selected difficulty level.
     */
    public void playMachine() throws QuoripoobException{
        if(difficult.equals("B")){
            
            playBeginner();

        }
        else if(difficult.equals("intermediate")){


            }
        else{
            // Implement advanced difficulty level logic
        }
        
        moveToNextPlayer();
    }

    /**
     * Plays a beginner-level move for the machine player.
     * 
     * @throws QuoripoobException If an error occurs during the move.
     */
    public void playBeginner() throws QuoripoobException {
        if (!vsMachine) {
            throw new QuoripoobException("This method can only be called when playing against the machine.");
        }
    
        if (actualPlayer == 1) {
            Random random = new Random();
    
            int randomAction = random.nextInt(2);
    
            if (randomAction == 0) {
                // Move the player
                String[] directions = {"N", "S", "E", "W"};
                String randomDirection = directions[random.nextInt(directions.length)];
                try {
                    moveTab(randomDirection);
                } catch (QuoripoobException e) {
                    Log.record(e);
                    playBeginner();
                }
            } else {
                // Place a barrier
                int row = random.nextInt(board.getSize());
                int col = random.nextInt(board.getSize());
                String orientation = random.nextBoolean() ? "H" : "V";
    
                // Choose a random type of barrier
                String[] barrierTypes = {"Normal", "Large", "Allied", "Temporary"};
                String barrierType = barrierTypes[random.nextInt(barrierTypes.length)];
    
                try {
                    putBarrier(row, col, orientation, barrierType);
                } catch (QuoripoobException e) {
                    Log.record(e);
                    playBeginner();
                }
            }
        }
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
     * Ends the game with the specified cause.
     * 
     * @param cause The cause of the game end.
     */
    public void endGame(String cause){
        JOptionPane.showMessageDialog(null, "Game over. Thanks for playing!");
        System.exit(0);    }

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

    public playerTab getActualPlayer() { 
        return players[actualPlayer];
    }
    
    /**
     * Checks if the current player can win the game.
     * 
     * @param currentPlayer The player whose turn it is.
     * @return true if the player can win, false otherwise.
     * @throws QuoripoobException If an error occurs during the check.
     */
    public boolean canPlayerWin(playerTab currentPlayer) throws QuoripoobException {
        Set<box> visited = new HashSet<>();
        Queue<box> queue = new ArrayDeque<>();
        Map<box, box> predecessors = new HashMap<>();
    
        // Add the player's initial position to the queue and mark it as visited
        queue.offer(currentPlayer.getCurrentBox());
        visited.add(currentPlayer.getCurrentBox());
        predecessors.put(currentPlayer.getCurrentBox(), null);  // No predecessor
    
        while (!queue.isEmpty()) {
            box currentBox = queue.poll();
            int row = currentBox.getRow();
            int col = currentBox.getColumn();
    
            // Check if the player has reached their winning row
            if (row == currentPlayer.getWinningRow()) {
                return true;
            }
    
            // Expand to unvisited neighboring cells
            for (int[] dir : DIRECTIONS) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                String movement;
    
                // Determine the direction of movement
                if (dir[0] == -1 && dir[1] == 0) {
                    movement = "S";
                } else if (dir[0] == 0 && dir[1] == -1) {
                    movement = "E";
                } else if (dir[0] == 1 && dir[1] == 0) {
                    movement = "N";
                } else if (dir[0] == 0 && dir[1] == 1) {
                    movement = "W";
                } else {
                    continue; // Ignore diagonal or invalid movements
                }
    
                // Check if the new position is within the board and unvisited
                if (newRow >= 0 && newRow < board.getSize() && newCol >= 0 && newCol < board.getSize() &&
                    !visited.contains(board.getBox(newRow, newCol)) && isValidMove(newRow, newCol, movement, currentPlayer.isStar())) {
                    
                    box newBox = board.getBox(newRow, newCol);
                    queue.offer(newBox);
                    visited.add(newBox);
                    predecessors.put(newBox, currentBox);  // Record predecessor
                }
            }
        }
    
        // The player cannot win if they do not reach their winning row
        return false;
    }
    
    /**
     * Distributes special boxes randomly on the board.
     * 
     * @param cantGoBack The number of goBack special boxes.
     * @param cantDoubleShift The number of doubleShift special boxes.
     * @param cantTeleporter The number of teleporter special boxes.
     * @param cantStar The number of star special boxes.
     */
    public void distributeSpecialBoxes(int cantGoBack, int cantDoubleShift, int cantTeleporter, int cantStar) {
        Random random = new Random();
    
        for (int i = 0; i < cantGoBack; i++) {
            int fila = random.nextInt(board.getSize() - 2) + 1; 
            int columna = random.nextInt(board.getSize());
            board.setSpecialBox(new goBack(fila, columna));
        }
    
        for (int i = 0; i < cantDoubleShift; i++) {
            int fila = random.nextInt(board.getSize() - 2) + 1; 
            int columna = random.nextInt(board.getSize());
            board.setSpecialBox(new doubleShift(fila, columna));
        }
    
        for (int i = 0; i < cantTeleporter; i++) {
            int fila = random.nextInt(board.getSize() - 2) + 1; 
            int columna = random.nextInt(board.getSize());
            board.setSpecialBox(new teleporter(fila, columna));
        }
    
        for (int i = 0; i < cantStar; i++) {
            int fila = random.nextInt(board.getSize() - 2) + 1; 
            int columna = random.nextInt(board.getSize());
            board.setSpecialBox(new star(fila, columna));
        }
    }
    
        /**
     * Saves the current state of the game to a file.
     * 
     * @param file The file to which the game state will be saved.
     * @throws QuoripoobException If an error occurs while saving the game state.
     */
    public void save(File file) throws QuoripoobException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(this);
        } catch (FileNotFoundException e) {
            Log.record(e);
            throw new QuoripoobException("No se puede encontrar el archivo para guardar el juego: " + e.getMessage());
        } catch (IOException e) {
            Log.record(e);
            throw new QuoripoobException("Error al guardar la partida: " + e.getMessage());
        }
    }



    /**
     * Opens a file containing a previously saved state of the game.
     * 
     * @param file The file to be opened to load the game state.
     * @return The game loaded from the file.
     * @throws QuoripoobException If an error occurs while opening or loading the game from the file.
     */
    public static QuoridorGame open(File file) throws QuoripoobException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            return (QuoridorGame) inputStream.readObject();
        } catch (FileNotFoundException e) {
            throw new QuoripoobException("No se puede encontrar el archivo para abrir la partida: " + e.getMessage());
        } catch (IOException e) {
            throw new QuoripoobException("Error al abrir el archivo: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new QuoripoobException("No se puede encontrar la clase necesaria para abrir la partida: " + e.getMessage());
        }
    }

    /**
     * Exports the current state of the game to a file.
     * 
     * @param file The file to which the game state will be exported.
     * @throws QuoripoobException If an error occurs while exporting the game state.
     */
    public void export(File file) throws QuoripoobException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(this);
        } catch (IOException e) {
            throw new QuoripoobException("Error al exportar el estado del jardín: " + e.getMessage());
        }
    }

    /**
     * Imports data from a file and updates the game state.
     * 
     * @param file The file from which data will be imported.
     * @throws QuoripoobException If an error occurs while importing and updating the game state from the file.
     */
    public void importData(File file) throws QuoripoobException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = inputStream.readObject();
            if (obj instanceof QuoridorGame) {
                QuoridorGame importedQuoridor = (QuoridorGame) obj;
                this.board  = importedQuoridor.board;
                this.actualPlayer = importedQuoridor.actualPlayer;
                this.vsMachine= importedQuoridor.vsMachine;
                this.difficult=importedQuoridor.difficult;
                this.mode=importedQuoridor.mode;
                this.players=importedQuoridor.players;
            } else {
                throw new QuoripoobException("El archivo no contiene una partida válida.");
            }
        } catch (IOException e) {
            throw new QuoripoobException("Error al importar los datos: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new QuoripoobException("Error al importar los datos: Clase no encontrada - " + e.getMessage());
        } catch (ClassCastException e) {
            throw new QuoripoobException("Error al importar los datos: No se puede convertir a un objeto Garden - " + e.getMessage());
        } catch (Exception e) {
            throw new QuoripoobException("Error al importar los datos: " + e.getMessage());
        }
    }

}
