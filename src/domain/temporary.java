package domain;

/**
 * The temporary class represents a temporary barrier in the Quoridor game.
 * It inherits from the barrier class.
 */
public class temporary extends barrier {

    private static final long serialVersionUID = 1L; 
    private playerTab owner; // The player who owns the temporary barrier.
    private int turn; // The number of turns the temporary barrier has been active.
    private box[] boxes; // The array of boxes affected by the temporary barrier.
    private String orientation; // The orientation of the temporary barrier.

    /**
     * Constructs a temporary barrier with the specified owner and orientation.
     * 
     * @param owner The player who owns the temporary barrier.
     * @param orientation The orientation of the temporary barrier.
     */
    public temporary(playerTab owner, String orientation) {
        super(true); // Initializes the barrier with a boolean parameter (true).
        this.owner = owner;
        this.turn = 0; // Initializes the turn counter to zero.
        this.color = colorsPalette.VIBRANT_BLUE; // Sets the color of the temporary barrier to vibrant blue.
        boxes = new box[4]; // Initializes the array of boxes affected by the temporary barrier with size 4.
        this.orientation = orientation;
    }

    /**
     * Updates the boxes affected by the temporary barrier.
     * 
     * @param box1 The first box affected by the barrier.
     * @param box2 The second box affected by the barrier.
     * @param box3 The third box affected by the barrier.
     * @param box4 The fourth box affected by the barrier.
     */
    public void updateBox(box box1, box box2, box box3, box box4) {
        boxes[0] = box1;
        boxes[1] = box2;
        boxes[2] = box3;
        boxes[3] = box4;
    }

    /**
     * Checks if the temporary barrier has lasted for 20 turns.
     * If so, removes the barrier and updates the player's barriers count.
     * 
     * @return true if the barrier has lasted for 20 turns and removed, false otherwise.
     */
    public boolean comprobeBarrier() {
        turn += 1; // Increment the turn counter.
        if (turn == 20) { // If the barrier has lasted for 20 turns.
            owner.updateBarrierTemporaly(); // Updates the player's temporary barriers count.
            eraseBarrier(); // Removes the barrier from the affected boxes.
            return true; // Returns true to indicate the barrier has been removed.
        } else {
            return false; // Returns false as the barrier is still active.
        }
    }

    /**
     * Removes the temporary barrier from the affected boxes based on its orientation.
     */
    public void eraseBarrier() {
        if (orientation.equals("H")) { // If the orientation is horizontal.
            boxes[0].eraseBarrier("N");
            boxes[1].eraseBarrier("N");
            boxes[2].eraseBarrier("S");
            boxes[3].eraseBarrier("S");
        } else { // If the orientation is vertical.
            boxes[0].eraseBarrier("W");
            boxes[1].eraseBarrier("W");
            boxes[2].eraseBarrier("E");
            boxes[3].eraseBarrier("E");
        }
    }
}
