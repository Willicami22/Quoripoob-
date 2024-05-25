package domain;

public class allied extends barrier {

    private playerTab owner;

    public allied( playerTab owner){

        super(true);
        this.owner=owner;
        color=colorsPalette.VIBRANT_GREEN;
    }

    public playerTab getOwner(){
        return owner;
    }

}
