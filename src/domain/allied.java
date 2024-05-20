package domain;

public class allied extends barrier {

    private playerTab owner;

    public allied(boolean orientation, playerTab owner){

        super(orientation);
        this.owner=owner;
    }

}
