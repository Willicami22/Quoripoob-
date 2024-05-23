package domain;

import java.awt.Color;
import java.util.*;

import java.io.*;

import javax.swing.JOptionPane;


    /**
     * Constructs a new instance of QuoridorGame.
     * Initializes the game with a board size of 9x9 and sets up initial player positions.
     */

public class QuoridorGame implements Serializable {

    private static final long serialVersionUID = 1L; 
    private playerTab[] players;
    private board board;
    private int actualPlayer;
    private boolean vsMachine=true;
    private String mode;
    private String difficult;
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};




    public QuoridorGame(){
        
    
        board= new board(9);
        players= new playerTab[2];
        actualPlayer=0;

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
        if (player=="M"){
            vsMachine=true;
        }
        else{
            vsMachine=false;
        }
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
    public void setPlayers(int player, String name, Color color) {
        box BoxInit = (player == 0) ? board.getBox(0, 4) : board.getBox(8, 4);
        int winningRow = (player == 0) ? 8 : 0;

        if (player == 0 || (player == 1 && !vsMachine)) {
            player playerGame = new player(BoxInit, color, name, winningRow);
            players[player] = playerGame;
        } else {
            machine playerGame = new machine(BoxInit, color, name, winningRow, difficult);
            players[player] = playerGame;
        }
    }
    

    /**
     * Moves the player's piece in the specified direction.
     * 
     * @param direction The direction in which to move the player's piece.
     * @throws QuoripoobException If an error occurs during the move.
     */
    public void moveTab(String direction) throws QuoripoobException {
        if (actualPlayer < 0 || actualPlayer >= players.length) {
            throw new QuoripoobException("Invalid player index.");
        }
    
        playerTab currentPlayer = players[actualPlayer];
        if (currentPlayer == null) {
            throw new QuoripoobException("Current player not found.");
        }
    
        box currentBox = currentPlayer.getCurrentBox();
        if (currentBox == null) {
            throw new QuoripoobException("Current box not found for player.");
        }
    
        int[] newPosition = new int[2];
        newPosition[0] = currentBox.getRow();
        newPosition[1] = currentBox.getColumn();
        newPosition = obtainNewPosition(direction, newPosition);
    
        boolean comprobePlayer = comprobePlayer(newPosition[0], newPosition[1]);
        if (comprobePlayer && !(direction.equals("SE") || direction.equals("SW") || direction.equals("NE") || direction.equals("WE"))) {
            newPosition = obtainNewPosition(direction, newPosition);
        }
    
        if (!isValidMove(newPosition[0], newPosition[1], direction)) {
            throw new QuoripoobException("Invalid move.");
        }
    
        currentPlayer.setCurrentBox(board.getBox(newPosition[0], newPosition[1]));
        comprobeWinner();
        actualPlayer = (actualPlayer + 1) % players.length;

        if(vsMachine){
            playMachine();
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
    barrier barrier = new barrier(orientation.equals("H"));

    if (orientation.equals("H")) {
        board.getBox(rowInit, columnInit).placeBarrier(barrier, "N");
        board.getBox(rowInit, columnInit + 1).placeBarrier(barrier, "N");
        board.getBox(rowInit - 1, columnInit).placeBarrier(barrier, "S");
        board.getBox(rowInit - 1, columnInit + 1).placeBarrier(barrier, "S");
    } else if (orientation.equals("V")) {
        board.getBox(rowInit, columnInit).placeBarrier(barrier, "W");
        board.getBox(rowInit + 1, columnInit).placeBarrier(barrier, "W");
        board.getBox(rowInit, columnInit - 1).placeBarrier(barrier, "E");
        board.getBox(rowInit + 1, columnInit - 1).placeBarrier(barrier, "E");
    } else {
        throw new QuoripoobException("Invalid orientation");
    }

    if (!canPlayerWin()) {
        // Remove barriers if they block the path
        if (orientation.equals("H")) {
            board.getBox(rowInit, columnInit).eraseBarrier("N");
            board.getBox(rowInit, columnInit + 1).eraseBarrier("N");
            board.getBox(rowInit - 1, columnInit).eraseBarrier("S");
            board.getBox(rowInit - 1, columnInit + 1).eraseBarrier("S");
        } else {
            board.getBox(rowInit, columnInit).eraseBarrier("W");
            board.getBox(rowInit + 1, columnInit).eraseBarrier("W");
            board.getBox(rowInit, columnInit - 1).eraseBarrier("E");
            board.getBox(rowInit + 1, columnInit - 1).eraseBarrier("E");
        }
        throw new QuoripoobException("El camino bloquea al jugador");
    }

    actualPlayer = (actualPlayer + 1) % players.length;

    if (vsMachine) {
        playMachine();
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
    private boolean isValidBarrierPlacement(int rowInit, int columnInit, String orientation) {
        int boardSize = board.getSize();
    
        // Verificar si las coordenadas están dentro de los límites del tablero
        if (rowInit < 0 || rowInit >= boardSize || columnInit < 0 || columnInit >= boardSize) {
            return false;
        }
    
        // Verificar restricciones específicas para la orientación de la barrera
        if (orientation.equals("H")) {
            // Si la orientación es horizontal, verificar los límites de columna
            if (columnInit == boardSize - 1 || columnInit == boardSize) {
                return false; // Fuera de los límites
            }
        } else if (orientation.equals("V")) {
            // Si la orientación es vertical, verificar los límites de fila
            if (rowInit == boardSize - 1 || rowInit == boardSize) {
                return false; // Fuera de los límites
            }
        } else {
            // Orientación inválida
            return false;
        }
    
        // Verificar si hay barreras en las posiciones adyacentes
        if (orientation.equals("H")) {
            return !board.getBox(rowInit-1, columnInit).hasBarrier("S") &&
                   !board.getBox(rowInit-1, columnInit + 1).hasBarrier("S") &&
                   !board.getBox(rowInit , columnInit).hasBarrier("N") &&
                   !board.getBox(rowInit , columnInit + 1).hasBarrier("N");
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
    public void playMachine() throws QuoripoobException{
        if(difficult.equals("begginer")){
            
            playBeginner();

        }
        else if(difficult.equals("intermediate")){

            playIntermediate();

            }
        else{
            // Implement advanced difficulty level logic
        }
        
        actualPlayer = (actualPlayer + 1) % players.length;
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
                
                String[] directions = {"N", "S", "E", "W", "NE", "NW", "SE", "SW"};
                String randomDirection = directions[random.nextInt(directions.length)];
                try {
                    moveTab(randomDirection);
                } catch (QuoripoobException e) {
                    Log.record(e);
                    playBeginner();
                }
            } else {
                
                int row = random.nextInt(board.getSize());
                int col = random.nextInt(board.getSize());
                String orientation = random.nextBoolean() ? "H" : "V";
                try {
                    putBarrier(row, col, orientation);
                } catch (QuoripoobException e) {
                    
                    Log.record(e);
                    playBeginner();
                }
            }
        }
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
            if(!comprobe){
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
            comprobe = newBox.thereIsABarrier("S");
        }
        else if(direction.equals("N")){
            comprobe = newBox.thereIsABarrier("N");
        }
        else if(direction.equals("E")){
            comprobe = newBox.thereIsABarrier("E");
        }
        else{
            comprobe = newBox.thereIsABarrier("W");
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
    private boolean checkDiagonalMovements(String direction, box currentBox) {
        boolean isPossible = false;
    
        int[] orthogonalPosition = new int[2];
        orthogonalPosition[0] = currentBox.getRow();
        orthogonalPosition[1] = currentBox.getColumn();
    
        char firstDirection = direction.charAt(0);
        char secondDirection = direction.charAt(1);
    
        orthogonalPosition = obtainNewPosition(String.valueOf(firstDirection), orthogonalPosition);
    
        // Verificar si hay un jugador adyacente y una barrera que impida el salto
        if (comprobePlayer(orthogonalPosition[0], orthogonalPosition[1])) {
            box adjacentBox = board.getBox(orthogonalPosition[0], orthogonalPosition[1]);
            if (checkOrthogonalMovements(String.valueOf(firstDirection), adjacentBox)) {
                isPossible = true;
            }
        }

        if (comprobePlayer(orthogonalPosition[0], orthogonalPosition[1])) {
            box adjacentBox = board.getBox(orthogonalPosition[0], orthogonalPosition[1]);
            if (checkOrthogonalMovements(String.valueOf(secondDirection), adjacentBox)) {
                isPossible = true;
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

    public playerTab getActualPlayer(){ 
        return players[actualPlayer];
    }

    public boolean canPlayerWin() {
        playerTab currentPlayer = players[actualPlayer];
        Set<box> visited = new HashSet<>();
        Queue<box> queue = new ArrayDeque<>();
        
        // Añadir la posición inicial del jugador a la cola y marcarla como visitada
        queue.offer(currentPlayer.getCurrentBox());
        visited.add(currentPlayer.getCurrentBox());
        
        while (!queue.isEmpty()) {
            box currentBox = queue.poll();
            int row = currentBox.getRow();
            int col = currentBox.getColumn();
            
            // Verificar si el jugador ha alcanzado su fila de victoria
            if (row == currentPlayer.getWinningRow()) {
                return true;
            }
            
            // Expandir las casillas vecinas no visitadas
            for (int[] dir : DIRECTIONS) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                String movement;
                
                if (dir[0] == -1 && dir[1] == 0) {
                    movement = "N";
                } else if (dir[0] == 0 && dir[1] == -1) {
                    movement = "W";
                } else if (dir[0] == 1 && dir[1] == 0) {
                    movement = "S";
                } else {
                    movement = "E";
                }
    
                // Verificar si la nueva posición está dentro del tablero y no ha sido visitada
                if (newRow >= 0 && newRow < board.getSize() && newCol >= 0 && newCol < board.getSize() &&
                    !visited.contains(board.getBox(newRow, newCol)) && isValidMove(newRow, newCol, movement)) {
                    
                    queue.offer(board.getBox(newRow, newCol));
                    visited.add(board.getBox(newRow, newCol));
                }
            }
        }
        
        // El jugador no puede ganar si no alcanza su fila de victoria
        return false;
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
                throw new QuoripoobException("El archivo no contiene un jardín válido.");
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

    private int[][] createGraph() {
        int size = board.getSize();
        int[][] graph = new int[size * size][size * size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int current = i * size + j;
                box currentBox = board.getBox(i, j);
                if (!currentBox.hasBarrier("N") && i > 0) {
                    int neighbor = (i - 1) * size + j;
                    graph[current][neighbor] = 1;
                }
                if (!currentBox.hasBarrier("S") && i < size - 1) {
                    int neighbor = (i + 1) * size + j;
                    graph[current][neighbor] = 1;
                }
                if (!currentBox.hasBarrier("W") && j > 0) {
                    int neighbor = i * size + (j - 1);
                    graph[current][neighbor] = 1;
                }
                if (!currentBox.hasBarrier("E") && j < size - 1) {
                    int neighbor = i * size + (j + 1);
                    graph[current][neighbor] = 1;
                }
            }
        }

        return graph;
    }

    private Map<Integer, Integer> dijkstra(int[][] graph, int source) {
        int size = graph.length;
        int[] dist = new int[size];
        int[] prev = new int[size];
        boolean[] visited = new boolean[size];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[source] = 0;

        for (int i = 0; i < size; i++) {
            int u = minDistance(dist, visited);
            if (u == -1) break;
            visited[u] = true;

            for (int v = 0; v < size; v++) {
                if (!visited[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                    prev[v] = u;
                }
            }
        }

        Map<Integer, Integer> path = new HashMap<>();
        for (int i = 0; i < size; i++) {
            path.put(i, prev[i]);
        }

        return path;
    }

    private int minDistance(int[] dist, boolean[] visited) {
        int min = Integer.MAX_VALUE, minIndex = -1;

        for (int v = 0; v < dist.length; v++) {
            if (!visited[v] && dist[v] <= min) {
                min = dist[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    public void playIntermediate() throws QuoripoobException {
        if (!vsMachine) {
            throw new QuoripoobException("This method can only be called when playing against the machine.");
        }

        if (actualPlayer == 1) {
            playerTab machine = players[1];
            playerTab human = players[0];
            int[][] graph = createGraph();

            int machinePosition = machine.getCurrentBox().getRow() * board.getSize() + machine.getCurrentBox().getColumn();
            int humanPosition = human.getCurrentBox().getRow() * board.getSize() + human.getCurrentBox().getColumn();

            Set<Integer> machineTargets = new HashSet<>();
            for (int col = 0; col < board.getSize(); col++) {
                machineTargets.add((board.getSize() - 1) * board.getSize() + col);
            }

            Set<Integer> humanTargets = new HashSet<>();
            for (int col = 0; col < board.getSize(); col++) {
                humanTargets.add(col);
            }

            Map<Integer, Integer> machinePath = dijkstra(graph, machinePosition);
            Map<Integer, Integer> humanPath = dijkstra(graph, humanPosition);

            int machineDistance = findShortestPathDistance(machinePath, machinePosition, machineTargets);
            int humanDistance = findShortestPathDistance(humanPath, humanPosition, humanTargets);

            if (machineDistance < humanDistance) {
                int nextPosition = findNextPosition(machinePath, machinePosition, machineTargets);
                int[] newPosition = {nextPosition / board.getSize(), nextPosition % board.getSize()};
                int[] currentPosition = {machine.getCurrentBox().getRow(), machine.getCurrentBox().getColumn()};
                String direction = determineDirection(currentPosition, newPosition);
                moveTab(direction);
            } else {
                int row = new Random().nextInt(board.getSize());
                int col = new Random().nextInt(board.getSize());
                String orientation = new Random().nextBoolean() ? "H" : "V";
                try {
                    putBarrier(row, col, orientation);
                } catch (QuoripoobException e) {
                    playIntermediate();
                }
            }
        }
    }

    private int findShortestPathDistance(Map<Integer, Integer> path, int start, Set<Integer> targets) {
        int minDistance = Integer.MAX_VALUE;
        for (int target : targets) {
            int distance = 0;
            for (int at = target; at != -1; at = path.get(at)) {
                distance++;
                if (at == start) break;
            }
            minDistance = Math.min(minDistance, distance);
        }
        return minDistance;
    }

    private int findNextPosition(Map<Integer, Integer> path, int start, Set<Integer> targets) {
        int minDistance = Integer.MAX_VALUE;
        int nextPosition = start;
        for (int target : targets) {
            int distance = 0;
            int at;
            for (at = target; at != -1; at = path.get(at)) {
                if (path.get(at) == start) {
                    nextPosition = at;
                    break;
                }
                distance++;
            }
            if (distance < minDistance) {
                minDistance = distance;
                nextPosition = at;
            }
        }
        return nextPosition;
    }

    private String determineDirection(int[] currentPosition, int[] newPosition) {
        int dRow = newPosition[0] - currentPosition[0];
        int dCol = newPosition[1] - currentPosition[1];

        if (dRow == -1 && dCol == 0) return "N";
        if (dRow == 1 && dCol == 0) return "S";
        if (dRow == 0 && dCol == 1) return "E";
        if (dRow == 0 && dCol == -1) return "W";
        if (dRow == -1 && dCol == 1) return "NE";
        if (dRow == -1 && dCol == -1) return "NW";
        if (dRow == 1 && dCol == 1) return "SE";
        if (dRow == 1 && dCol == -1) return "SW";

        return null;
    }
}
