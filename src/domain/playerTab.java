package domain;
import java.awt.Color;
import java.util.*;

public class playerTab {

    private String color;
    private ArrayList<int[][]> movements; 
    private String name;
    private Map<String, Integer> barriers;
    protected box position;

    public playerTab(box boxPosition, String color, String name ){
        
        position=boxPosition;
        this.color=color;
        this.name=name;

    }

    public void setBarriers(Map<String, Integer> barriers){

        this.barriers=barriers;

    }


}
