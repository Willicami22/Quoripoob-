package domain;

import java.util.*;

public class QuoridorGame{

    private Map<String,playerTab> players;
    private board board;
    private int actualPlayer;
    private boolean vsMachine;
    private String mode;
    private boolean isNew;
    private String difficult;



    public QuoridorGame(){  
        
        isNew=true;
        board= new board(9);

        }

    public boolean isNew(){
        return isNew;
    }

    public void setDifficult(String difficult){

        this.difficult=difficult;
    }

    public void setPlayer(String player){

        if(player=="M"){
            vsMachine=true;
        }
        else{
            vsMachine=true;
        }
    }

    public void setMode(String mode){
        this.mode=mode;
    }
}
