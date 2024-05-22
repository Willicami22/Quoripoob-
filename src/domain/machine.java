package domain;

import java.awt.Color;

public class machine extends playerTab {
    private String difficult;

    public machine(box boxPosition, Color color, String name, int winningRow, String difficult) {
        super(boxPosition, color, name, winningRow);
        this.difficult = difficult;
    }

}
