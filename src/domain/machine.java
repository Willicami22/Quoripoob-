package domain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class machine extends playerTab {


    private String difficult;

    public machine(box boxPosition, Color color, String name, int winningRow,String diffilcult ){
        super(boxPosition, color, name, winningRow);
        this.difficult=diffilcult;
    }
}
