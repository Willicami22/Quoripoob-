package domain;

import java.util.HashMap;
import java.util.Map;

public class box {

    protected int row;
    protected int column;
    protected boolean isNormal;
    protected Map<String, box> barriers;

    public box(int row, int column){

        this.row=row;
        this.column=column;
        this.barriers = new HashMap<>();
        barriers.put("S",null);
        barriers.put("N",null);
        barriers.put("E",null);
        barriers.put("W",null);
    }    
}

