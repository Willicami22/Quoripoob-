package domain;

import java.io.Serializable;

public class QuoripoobException extends Exception implements Serializable {

    private static final long serialVersionUID = 1L; 
    public static final String OutOfIndex="Invalid player index.";
    public static final String OutOfBoard="The position out of the bord.";
    public static final String InvalidMove="Invalid Move.";
    public static final String GoBack="Not enough movements to go back.";
    public static final String InvalidDirection="Invalid direction.";
    public static final String InvalidPlaceBarrier="Invalid barrier placement";
    public static final String DontHaveBarriers="You don't have more barriers of this type.";
    public static final String PathBlocked="The path blocks the player";






    public QuoripoobException(String msg){
        super(msg);
    }

}
