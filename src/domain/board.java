package domain;

import java.util.ArrayList;
import java.util.List;

public class board {

    private int size;
    private box[][] boxes;
    private List<barrier> barriers;


    public board(int size){

        this.size=size;
        boxes= new box[size][size];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){

                boxes[i][j]=new box(i,j);
            }
        }

        barriers = new ArrayList<>();

    }

    public box getBoxd(int row, int col) {
        return boxes[row][col];
    }


    
}
