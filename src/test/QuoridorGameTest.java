package test;

import domain.*;
import java.awt.Color;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.*;

public class QuoridorGameTest {

    private QuoridorGame game;

    @Before
    public void setUp() {
        game = new QuoridorGame();
        game.setBoard(9); // Set the board size to 9x9
        game.setPlayers(0, "Player 1", Color.RED);
        game.setPlayers(1, "Player 2", Color.BLUE);
        game.setBarriers(5, 2, 1, 1); // Set the number of barriers for each player
    }

    @Test
    public void testSetDifficult() {
        game.setDifficult("B");
        assertEquals("B", game.getDifficult());

        game.setDifficult("I");
        assertNotEquals("A", game.getDifficult());
    }

    @Test
    public void testSetPlayer() {
        game.setPlayer("M");
        assertTrue(game.vsMachine);
        
        game.setPlayer("");
        assertFalse(game.vsMachine);
    }

    @Test
    public void testSetMode() {
        game.setMode("N");
        assertEquals("N", game.getMode());

        game.setMode("TT");
        assertNotEquals("N", game.getMode());
    }

    @Test
    public void testSetPlayers() {
        game.setPlayers(0, "Player 1", Color.RED);
        game.setPlayers(1, "Player 2", Color.BLUE);
        assertEquals("Player 1", game.getPlayers()[0].getName());
        assertEquals("Player 2", game.getPlayers()[1].getName());
        assertEquals(Color.RED, game.getPlayers()[0].getColor());
        assertEquals(Color.BLUE, game.getPlayers()[1].getColor());

        game.setPlayers(0, "Player1", Color.YELLOW);
        game.setPlayers(1, "Player2", Color.ORANGE);
        assertEquals("Player1", game.getPlayers()[0].getName());
        assertEquals("Player2", game.getPlayers()[1].getName());
        assertEquals(Color.YELLOW, game.getPlayers()[0].getColor());
        assertEquals(Color.ORANGE, game.getPlayers()[1].getColor());
    }

    @Test
    public void testSetBarriers() {
        game.setBarriers(5, 2, 1, 1);
        assertEquals(5, game.getPlayers()[0].getNumberBarrier("Normal"));
        assertEquals(2, game.getPlayers()[0].getNumberBarrier("Allied"));
        assertEquals(1, game.getPlayers()[0].getNumberBarrier("Temporary"));
        assertEquals(1, game.getPlayers()[0].getNumberBarrier("Large"));
    }

    @Test
    public void testSetBoard() {
        game.setBoard(9);
        assertEquals(9, game.getBoard().getSize());
    }

    @Test
    public void testMoveTab() throws QuoripoobException {
        // Test moving a player's piece
        game.moveTab("N"); // Move player 1 north
        assertEquals(1, game.getActualPlayer().getCurrentBox().getRow());
        assertEquals(4, game.getActualPlayer().getCurrentBox().getColumn());
    }

    @Test
    public void testPutBarrier() throws QuoripoobException {
        // Test placing a barrier
        game.putBarrier(4, 4, "H", "Normal"); // Place a normal horizontal barrier at (4, 4)
        assertTrue(game.getBoard().getBox(4, 4).hasBarrier("N"));
        assertTrue(game.getBoard().getBox(4, 5).hasBarrier("N"));
        assertTrue(game.getBoard().getBox(3, 4).hasBarrier("S"));
        assertTrue(game.getBoard().getBox(3, 5).hasBarrier("S"));
    }

    @Test
    public void testComprobeWinner() {
        // Test checking for a winner
        game.getActualPlayer().setCurrentBox(game.getBoard().getBox(8, 4)); // Move player 1 to the winning row
        game.comprobeWinner();
        // Assert that the game has ended and the winner is player 1
    }

    @Test
    public void testGiveUp() {
        game.giveUp();
        // Assert that the game has ended and the current player has given up
    }

    @Test
    public void testCanPlayerWin() throws QuoripoobException {
        // Test checking if a player can win
        assertTrue(game.canPlayerWin(game.getActualPlayer())); // Player 1 should be able to win initially
        game.putBarrier(8, 3, "H", "Normal"); // Place a barrier blocking player 1's path
        game.putBarrier(8, 5, "H", "Normal");
        assertFalse(game.canPlayerWin(game.getActualPlayer())); // Player 1 should not be able to win after the barrier placement
    }

    @Test
    public void testDistributeSpecialBoxes() {
        game.distributeSpecialBoxes(2, 2, 2, 2);
        // Assert that the special boxes are distributed correctly on the board
    }

    // Add more test methods for other functionalities like save(), open(), export(), importData(), etc.

}