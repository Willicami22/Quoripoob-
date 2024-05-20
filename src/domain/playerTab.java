package domain;
import java.awt.Color;
import java.util.*;

public class playerTab {

    private Color color;
    private ArrayList<box> movements; 
    private String name;
    private Map<String, Integer> barriers;
    protected box position;
    private int winningRow;

    public playerTab(box boxPosition, Color color, String name, int winningRow ){
        
        position=boxPosition;
        movements= new ArrayList<>();
        movements.add(position);
        this.color=color;
        this.name=name;

    }

    public void setBarriers(Map<String, Integer> barriers){

        this.barriers=barriers;

    }

    public box getCurrentBox(){
        return position;
    }

    public void setCurrentBox(box newBox){

        position=newBox;

    }

    public boolean hasWon() {

        return position.getRow() == winningRow;
    }

    public String getName(){
        return name;
    }

    public Color getColor(){
        return color;
    }
}
