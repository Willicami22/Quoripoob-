package domain;

import java.awt.Color;
import java.util.*;

import javax.swing.JOptionPane;

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

    public boolean isNew(){
        return isNew;
    }

    public void setDifficult(String difficult){

        this.difficult=difficult;

    }

    public void setPlayer(String player){
        vsMachine = "M".equals(player);
    }

    public void setMode(String mode){
        this.mode=mode;
    }

    public void setPlayers(int player, String name, Color color){

        box BoxInit = (player == 1) ? board.getBox(0, 4) : board.getBox(8, 4);

        int winningRow = (player == 1) ? 8 : 0;

        playerTab playerGame = new playerTab(BoxInit, color, name, winningRow);  

        players[player-1] = playerGame;

    }

    

    public void moveTab(String direction) throws QuoripoobException{

        if (actualPlayer >= 0 && actualPlayer < players.length){

            

            playerTab currentPlayer = players[actualPlayer];
            box currentBox = currentPlayer.getCurrentBox();
        
            int[] newPosition= new int[2]; newPosition[0]=currentBox.getRow();newPosition[1]=currentBox.getColumn();
            newPosition=obtainNewPosition(direction, newPosition);
            boolean comprobePlayer=comprobePlayer(newPosition[1],newPosition[0]);

            if(comprobePlayer && !(direction=="SE" || direction=="SW"|| direction=="NE" || direction=="WE")){
   
                newPosition=obtainNewPosition(direction, newPosition);
            }

            if (isValidMove(newPosition[0], newPosition[1], direction)) {

                currentPlayer.setCurrentBox(board.getBox(newPosition[0], newPosition[1]));
                comprobeWinner();
                actualPlayer = (actualPlayer + 1) % players.length;


            }
        }
    }

    public int[] obtainNewPosition(String direction,int[] newPosition){
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
            case "NE":
                newPosition[1]++;
                newPosition[0]++;
                break;
            case "NW":
                newPosition[1]--;
                newPosition[0]++;
            default:
                break;
        }

        return newPosition;
    }

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

    public boolean isValidBarrierPlacement(int rowInit,int columnInit,String orientation){

        if (rowInit < 0 && rowInit > board.getSize() && columnInit < 0 && columnInit > board.getSize() ){
            return false;
        }

        if ((orientation=="H" && (columnInit==8 || columnInit==9))|| (orientation=="V" && (rowInit==8 || rowInit==9)))

        if((orientation=="H" && rowInit==0) || (orientation=="H" && rowInit==9) || (orientation=="V" && columnInit==0) || (orientation=="V" && columnInit==9)){
            return false;
        }

        if (orientation.equals("H")) {
            return !board.getBox(rowInit, columnInit).hasBarrier("S") &&
                   !board.getBox(rowInit, columnInit + 1).hasBarrier("S") &&
                   !board.getBox(rowInit+1, columnInit ).hasBarrier("N") &&
                   !board.getBox(rowInit+1, columnInit + 1).hasBarrier("N");
        } else {
            return  !board.getBox(rowInit, columnInit-1).hasBarrier("E") &&
                    !board.getBox(rowInit, columnInit ).hasBarrier("W") &&
                    !board.getBox(rowInit+1, columnInit-1 ).hasBarrier("E") &&
                    !board.getBox(rowInit+1, columnInit ).hasBarrier("W");
        }

    }

    public void playMachine(){
        
        if(difficult=="begginer"){
            Random random = new Random();
            int randomNumber = random.nextInt(2) + 1;

            if(randomNumber==1){
                moveTabBegginer();
            }
            else {
                putBarrierBegginer();
            }
        }

        else if(difficult=="intermediate"){

        }

        else{

        }


    }

    public void moveTabBegginer(){
        
        

    }

    public void putBarrierBegginer(){

    }



    public boolean isValidBarrier(){

        boolean isValid=false;




        return isValid;

    }

    public boolean isValidMove(int newRow, int newColumn, String direction){

        boolean isValid=true;
        box newBox= board.getBox(newRow, newColumn);

        if(direction=="S" || direction=="E"|| direction=="N" || direction=="W"){
            boolean comprobe=checkOrthogonalMovements(direction, newBox);
    
            if(comprobe){
                isValid=false;
            }
        }
        else{
            boolean comprobe= checkDiagonalMovements(direction,newBox);
            if(comprobe){
                isValid=false;
            }
        }
        
        if (newRow < 0 && newRow > board.getSize() && newColumn < 0 && newColumn > board.getSize()){
            isValid=false;
        }

        

        return isValid;
    }

    private boolean checkOrthogonalMovements(String direction,box newBox){
        boolean comprobe=false;
        if(direction=="S"){
            comprobe = newBox.thereIsABarrier("N");
        }
        else if(direction=="N"){
            comprobe = newBox.thereIsABarrier("S");
        }

        else if(direction=="E"){
            comprobe = newBox.thereIsABarrier("W");
        }

        else{
            comprobe = newBox.thereIsABarrier("E");
        }
        return comprobe;
    }

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
    

    public void comprobeWinner() {
        for (playerTab player : players) {
            if (player.hasWon()) {
                JOptionPane.showMessageDialog(null, "Player " + player.getName() + " has won!");
                endGame("Player Win");
                return;  
            }
        }
    }

    public void giveUp(){

        playerTab lostPlayer = players[actualPlayer]; 

        JOptionPane.showMessageDialog(null, "Player " + lostPlayer.getName() + " you give up.");

        endGame("Give up");
    }

    public boolean comprobePlayer(int Row, int Column){
 
        boolean comprobe=false;

        int indexOtherPlayer=(actualPlayer + 1) % players.length;
        playerTab otherPlayer= players[indexOtherPlayer];
        box boxOther= otherPlayer.getCurrentBox();

        if(boxOther.getColumn()==Column && boxOther.getRow()==Row){
            comprobe=true;
        }

        return comprobe;

    }

    public void endGame(String cause){

        JOptionPane.showMessageDialog(null, "Game over. Thanks for playing!");


    }

    public playerTab[] getPlayers(){
        return players;
    }

    public board getBoard(){
        return board;
    }


}
