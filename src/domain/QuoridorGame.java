package domain;

import java.awt.Color;
import java.util.*;

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

    public void setPlayers(int player, String name, String color){

        box BoxInit = (player == 1) ? board.getBox(0, 4) : board.getBox(8, 4);

        playerTab playerGame = new playerTab(BoxInit, color, name);  

        players[player-1] = playerGame;

    }

    public void moveTab(String direction) throws QuoripoobException{

        if (actualPlayer >= 0 && actualPlayer < players.length){

            boolean diagonal= true;

            playerTab currentPlayer = players[actualPlayer];
            box currentBox = currentPlayer.getCurrentBox();
        
            int[] newPosition= new int[2]; newPosition[0]=currentBox.getRow();newPosition[1]=currentBox.getColumn();
            newPosition=obtainNewPosition(direction, newPosition);
            boolean comprobePlayer=comprobePlayer(newPosition[1],newPosition[0]);

            if(comprobePlayer && !(direction=="SE" || direction=="SW"|| direction=="NE" || direction=="WE")){
   
                newPosition=obtainNewPosition(direction, newPosition);
            }
            else if (comprobePlayer){

                diagonal=false;
            }
            if (isValidMove(newPosition[0], newPosition[1], direction) && diagonal) {

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

    public void putBarrier(){



    }

    public void playMachine(){
        


    }

    public void moveTabBegginer(){
        


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
    

    public void comprobeWinner(){

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


}
