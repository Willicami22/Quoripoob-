package domain;

public class temporary extends barrier {

    private playerTab owner;
    private int turno;
    

    public temporary(playerTab owner,boolean si){
        super(si);
        this.owner=owner;
        this.turno=0;
        this.color=colorsPalette.VIBRANT_BLUE;
    }

    public boolean comprobeBarrier(){

        turno+=1;
        if (turno==16){

            owner.updateBarrierTemporaly();
            return true;
        }
        else{
            return false;
        }

    }
}
