package domain;

import java.util.HashMap;
import java.util.Map;

public class box {

    protected int row;
    protected int column;
    protected boolean isNormal;
    protected Map<String, barrier> barriers;

    public box(int row, int column){

        this.row=row;
        this.column=column;
        this.barriers = new HashMap<>();
        barriers.put("S",null);
        barriers.put("N",null);
        barriers.put("E",null);
        barriers.put("W",null);
    }    

    public int getRow(){

        return row;

    }

    public int getColumn(){

        return column;

    }

    public boolean thereIsABarrier(String direction){

        barrier barrier= barriers.get(direction);

        return (!barrier.equals(null));

    }
 }


