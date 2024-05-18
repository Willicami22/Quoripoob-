package domain;
import java.util.*;

public class playerTab {

    private String color;
    private ArrayList<int[][]> movements; 
    private String name;
    private Map<String, Integer> barriers;
    protected int row;
    protected int column;

    public playerTab(String color,String name, int rowInit, int columnInit, Map<String, Integer> barriers){
        
        this.color=color;
        this.name=name;
        this.row=row;
        this.column=column;
        this.barriers=barriers;

    }
}
