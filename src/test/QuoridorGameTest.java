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

    @Test
    public void testSetDifficult() {
        game = new QuoridorGame();
        game.setBoard(9);
        game.setPlayer("H");
        game.setPlayers(0, "Player 1", Color.RED);
        game.setPlayers(1, "Player 2", Color.BLUE);
        game.setBarriers(5, 2, 1, 1);

        game.setDifficult("B");
        assertEquals("B", game.getDifficult());

        game.setDifficult("I");
        assertNotEquals("A", game.getDifficult());
    }

    @Test
    public void testSetPlayer() {
        game = new QuoridorGame();
        game.setBoard(9);
        game.setPlayer("H");
        game.setPlayers(0, "Player 1", Color.RED);
        game.setPlayers(1, "Player 2", Color.BLUE);
        game.setBarriers(5, 2, 1, 1);

        game.setPlayer("M");
        assertTrue(game.vsMachine);
        
        game.setPlayer("");
        assertFalse(game.vsMachine);
    }

    @Test
    public void testSetMode() {
        game = new QuoridorGame();
        game.setBoard(9);
        game.setPlayer("H");
        game.setPlayers(0, "Player 1", Color.RED);
        game.setPlayers(1, "Player 2", Color.BLUE);
        game.setBarriers(5, 2, 1, 1);

        game.setMode("N");
        assertEquals("N", game.getMode());

        game.setMode("TT");
        assertNotEquals("N", game.getMode());
    }

    @Test
    public void testSetPlayers() {
        game = new QuoridorGame();
        game.setBoard(9);
        game.setPlayer("H");
        game.setPlayers(0, "Player 1", Color.RED);
        game.setPlayers(1, "Player 2", Color.BLUE);
        game.setBarriers(5, 2, 1, 1);

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
        game = new QuoridorGame();
        game.setBoard(9);
        game.setPlayer("H");
        game.setPlayers(0, "Player 1", Color.RED);
        game.setPlayers(1, "Player 2", Color.BLUE);

        game.setBarriers(5, 2, 1, 1);
        assertEquals(5, game.getPlayers()[0].getNumberBarrier("Normal"));
        assertEquals(2, game.getPlayers()[0].getNumberBarrier("Allied"));
        assertEquals(1, game.getPlayers()[0].getNumberBarrier("Temporary"));
        assertEquals(1, game.getPlayers()[0].getNumberBarrier("Large"));

        game.setBarriers(2, 3, 4, 7);
        assertNotEquals(5, game.getPlayers()[0].getNumberBarrier("Normal"));
        assertNotEquals(2, game.getPlayers()[0].getNumberBarrier("Allied"));
        assertNotEquals(1, game.getPlayers()[0].getNumberBarrier("Temporary"));
        assertNotEquals(1, game.getPlayers()[0].getNumberBarrier("Large"));
    }

    @Test
    public void testSetBoard() {
        game = new QuoridorGame();
        game.setBoard(9);
        game.setPlayer("H");
        game.setPlayers(0, "Player 1", Color.RED);
        game.setPlayers(1, "Player 2", Color.BLUE);
        game.setBarriers(5, 2, 1, 1);

        game.setBoard(9);
        assertEquals(9, game.getBoard().getSize());

        game.setBoard(12);
        assertNotEquals(9, game.getBoard().getSize());
    }

    @Test
    public void testMoveTab() throws QuoripoobException {
        game = new QuoridorGame();
        game.setBoard(9);
        game.setPlayer("H");
        game.setPlayers(0, "Player 1", Color.RED);
        game.setPlayers(1, "Player 2", Color.BLUE);
        game.setBarriers(5, 2, 1, 1);

        playerTab player = game.getActualPlayer();
        box box0 = player.getCurrentBox();
        int box0Row = box0.getRow();
        int box0Col = box0.getColumn();

        try{
            game.moveTab("N");
            game.moveTab("S");
            game.moveTab("N");
            game.moveTab("S");
            game.moveTab("N");
            game.moveTab("S");                        
            game.moveTab("N");  
            game.moveTab("S");      
            game.moveTab("N");
            game.moveTab("W");
            game.moveTab("W");
 
                        
            }
        catch(QuoripoobException e){
            fail();
        }

        assertEquals(box0Row+3, game.getActualPlayer().getCurrentBox().getRow());
        assertEquals(box0Col+1, game.getActualPlayer().getCurrentBox().getColumn());

        assertNotEquals(box0Row, game.getActualPlayer().getCurrentBox().getRow());
        assertNotEquals(box0Col, game.getActualPlayer().getCurrentBox().getColumn());
    }

    @Test
    public void testValidateNewPosition(){
        game = new QuoridorGame();
        game.setBoard(9);
        game.setPlayer("H");
        game.setPlayers(0, "Player 1", Color.RED);
        game.setPlayers(1, "Player 2", Color.BLUE);
        game.setBarriers(5, 2, 1, 1);

        int[] position0 = {4, -4}; // Posición dentro de los límites
        QuoripoobException exception0 = assertThrows(QuoripoobException.class, () -> game.validateNewPosition(position0));
        assertEquals(QuoripoobException.OutOfBoard, exception0.getMessage(), "The position out of the bord.");

        int[] position1 = {-1, 4}; // Posición fuera de los límites
        QuoripoobException exception1 = assertThrows(QuoripoobException.class, () -> game.validateNewPosition(position1));
        assertEquals(QuoripoobException.OutOfBoard, exception1.getMessage(), "The position out of the bord.");
    }

    @Test
    public void testIfIsDiagonalDirection(){
        game = new QuoridorGame();
        game.setBoard(9);
        game.setPlayer("H");
        game.setPlayers(0, "Player 1", Color.RED);
        game.setPlayers(1, "Player 2", Color.BLUE);
        game.setBarriers(5, 2, 1, 1);

        assertTrue(game.isDiagonalDirection("SE"));
        assertTrue(game.isDiagonalDirection("SW"));
        assertTrue(game.isDiagonalDirection("NE"));
        assertTrue(game.isDiagonalDirection("NW"));

        assertFalse(game.isDiagonalDirection("N"));
        assertFalse(game.isDiagonalDirection("S"));
        assertFalse(game.isDiagonalDirection("E"));
        assertFalse(game.isDiagonalDirection("W"));
        assertFalse(game.isDiagonalDirection(""));
    }

    @Test
    public void testComprobePlayer() {
        game = new QuoridorGame();
        game.setBoard(9);
        game.setPlayer("H");
        game.setPlayers(0, "Player 1", Color.RED);
        game.setPlayers(1, "Player 2", Color.BLUE);
        game.setBarriers(5, 2, 1, 1);

        assertTrue(game.comprobePlayer(8, 4));
        assertFalse(game.comprobePlayer(0, 0));
    }

    @Test
    public void testIsValidMove(){
        game = new QuoridorGame();
        game.setBoard(9);
        game.setPlayer("H");
        game.setPlayers(0, "Player 1", Color.RED);
        game.setPlayers(1, "Player 2", Color.BLUE);
        game.setBarriers(5, 2, 1, 1);

        try {
            assertTrue(game.isValidMove(8, 5, "E", false));
            assertFalse(game.isValidMove(7, 5, "NE", false));
        } catch (QuoripoobException e) {
            fail("Invalid move");
        }
    }

    @Test
    public void testGetOppositeDirection(){
        game = new QuoridorGame();
        game.setBoard(9);
        game.setPlayer("H");
        game.setPlayers(0, "Player 1", Color.RED);
        game.setPlayers(1, "Player 2", Color.BLUE);
        game.setBarriers(5, 2, 1, 1);

        try {
            assertEquals("S", game.getOppositeDirection("N"));
            assertEquals("N", game.getOppositeDirection("S"));
            assertEquals("W", game.getOppositeDirection("E"));
            assertEquals("E", game.getOppositeDirection("W"));
            assertEquals("SW", game.getOppositeDirection("NE"));
            assertEquals("SE", game.getOppositeDirection("NW"));
            assertEquals("NW", game.getOppositeDirection("SE"));
            assertEquals("NE", game.getOppositeDirection("SW"));

            assertNotEquals("S", game.getOppositeDirection("S"));
            assertNotEquals("N", game.getOppositeDirection("N"));
            assertNotEquals("W", game.getOppositeDirection("W"));
            assertNotEquals("E", game.getOppositeDirection("E"));
            assertNotEquals("SW", game.getOppositeDirection("SE"));
            assertNotEquals("SE", game.getOppositeDirection("SW"));
            assertNotEquals("NW", game.getOppositeDirection("NE"));
            assertNotEquals("NE", game.getOppositeDirection("NW"));
        } catch (QuoripoobException e) {
            fail("Exception should not be thrown for valid directions");
        }
    }

    @Test
    public void testObtainNewPosition(){
        game = new QuoridorGame();
        game.setBoard(9);
        game.setPlayer("H");
        game.setPlayers(0, "Player 1", Color.RED);
        game.setPlayers(1, "Player 2", Color.BLUE);
        game.setBarriers(5, 2, 1, 1);
        
        try {
            
            int[] position = {0, 0};
            int[] expectedPosition = {1, 0};
            assertArrayEquals(expectedPosition, game.obtainNewPosition("N", position));

            position = new int[]{0, 0};
            expectedPosition = new int[]{-1, 0};
            assertArrayEquals(expectedPosition, game.obtainNewPosition("S", position));

            position = new int[]{0, 0};
            expectedPosition = new int[]{0, -1};
            assertArrayEquals(expectedPosition, game.obtainNewPosition("E", position));

            position = new int[]{0, 0};
            expectedPosition = new int[]{0, 1};
            assertArrayEquals(expectedPosition, game.obtainNewPosition("W", position));

            position = new int[]{0, 0};
            expectedPosition = new int[]{1, 1};
            assertNotEquals(expectedPosition, game.obtainNewPosition("NE", position));

            position = new int[]{0,0};
            expectedPosition = new int[]{1, -1};
            assertNotEquals(expectedPosition, game.obtainNewPosition("NW", position));

            // Test for "SE" direction
            position = new int[]{0, 0};
            expectedPosition = new int[]{-1, 1};
            assertNotEquals(expectedPosition, game.obtainNewPosition("SE", position));

            // Test for "SW" direction
            position = new int[]{0, 0};
            expectedPosition = new int[]{-1, -1};
            assertNotEquals(expectedPosition, game.obtainNewPosition("SW", position));

        } catch (QuoripoobException e) {
            fail("Invalid move.");
        }
    }

    @Test
    public void testPutBarrier() throws QuoripoobException {
        game = new QuoridorGame();
        game.setBoard(9);
        game.setPlayer("H");
        game.setPlayers(0, "Player 1", Color.RED);
        game.setPlayers(1, "Player 2", Color.BLUE);
        game.setBarriers(5, 2, 1, 1);

        game.putBarrier(4, 4, "H", "Normal"); // Place a normal horizontal barrier at (4, 4)
        assertTrue(game.getBoard().getBox(4, 4).hasBarrier("N"));
        assertTrue(game.getBoard().getBox(4, 5).hasBarrier("N"));
        assertFalse(game.getBoard().getBox(7, 7).hasBarrier("S"));
    }

    @Test
    public void testComprobeWinner() {
        game = new QuoridorGame();
        game.setBoard(9);
        game.setPlayer("H");
        game.setPlayers(0, "Player 1", Color.RED);
        game.setPlayers(1, "Player 2", Color.BLUE);
        game.setBarriers(5, 2, 1, 1);

        playerTab player2 = game.getPlayers()[1];

        game.getActualPlayer().setCurrentBox(game.getBoard().getBox(8, 4));
        assertTrue(game.getActualPlayer().hasWon());

        assertFalse(player2.hasWon());
    }

    @Test
    public void testGetPlayers(){
        game = new QuoridorGame();
        game.setBoard(9);
        game.setPlayer("H");
        game.setPlayers(0, "Player 1", Color.RED);
        game.setPlayers(1, "Player 2", Color.BLUE);
        game.setBarriers(5, 2, 1, 1);
        playerTab[] players = game.getPlayers();

        assertEquals("Player 1", players[0].getName());
        assertEquals(Color.RED, players[0].getColor());
        
        assertEquals("Player 2", players[1].getName());
        assertEquals(Color.BLUE, players[1].getColor());
    }

    @Test
    public void testGetBoard() {
        game = new QuoridorGame();
        game.setBoard(9);
        game.setPlayer("H");
        game.setPlayers(0, "Player 1", Color.RED);
        game.setPlayers(1, "Player 2", Color.BLUE);
        game.setBarriers(5, 2, 1, 1);

        board actualBoard = game.getBoard();

        assertNotNull(actualBoard);

        assertEquals(game.getBoard(), actualBoard);
        
        assertEquals(game.getBoard().getSize(), actualBoard.getSize());
    }

    @Test
    public void testGetActualPlayer() {
        game = new QuoridorGame();
        game.setBoard(9);
        game.setPlayer("H");
        game.setPlayers(0, "Player 1", Color.RED);
        game.setPlayers(1, "Player 2", Color.BLUE);
        game.setBarriers(5, 2, 1, 1);

        playerTab player1 = game.getActualPlayer();
        playerTab player2 = game.getPlayers()[1];

        assertNotNull(player1);

        assertEquals("Player 1", player1.getName());
        assertEquals(Color.RED, player1.getColor());

        assertNotNull(player2);

        assertEquals("Player 2", player2.getName());
        assertEquals(Color.BLUE, player2.getColor());
    }

    @Test
    public void testCanPlayerWin() throws QuoripoobException {
        game = new QuoridorGame();
        game.setBoard(9);
        game.setPlayer("H");
        game.setPlayers(0, "Player 1", Color.RED);
        game.setPlayers(1, "Player 2", Color.BLUE);
        game.setBarriers(5, 2, 1, 1);

        box startBox = game.getBoard().getBox(0, 0);
        
        playerTab player = new playerTab(startBox, Color.RED, "Player 1", 8);
        game.setPlayers(0, "Player 1", Color.RED);

        boolean canWin = game.canPlayerWin(player);

        assertTrue(canWin);
    }
}