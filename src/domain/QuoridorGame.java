package domain;

import java.awt.Color;
import java.util.*;

public class QuoridorGame{

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

        box BoxInit = (player == 1) ? board.getBoxd(0, 4) : board.getBoxd(8, 4);

        playerTab playerGame = new playerTab(BoxInit, color, name);  

        players[player-1] = playerGame;

    }

    public void moveTab(String direction){




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

    public boolean isValidMove(){

        boolean isValid=false;




        return isValid;
    }


}
