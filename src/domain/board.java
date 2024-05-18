package domain;

public class board {

    private int size;
    private box[][] boxes;


    public board(int size){

        this.size=size;
        boxes= new box[size][size];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                box box= new box(i,j);
                boxes[i][j]=box;
            }

        }
    }

    
}
