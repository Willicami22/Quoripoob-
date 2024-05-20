package domain;
import java.util.*;

public class playerTab {

    private String color;
    private ArrayList<box> movements; 
    private String name;
    private Map<String, Integer> barriers;
    protected box position;

    public playerTab(box boxPosition, String color, String name ){
        
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


}
