package test;
import static org.junit.Assert.*;


import org.junit.*;

import java.awt.Color;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import domain.*;


/**
 * The test class Quopripoobv1.
 *
 * @author  POOB
 * @version 2024-1
 */
public class Quopripoobv1
{

    private QuoridorGame quoripoob;
    /**
     * Default constructor for test class Qupripoobv1
     */
    public Quopripoobv1()
    {
    }


    @Test
    public void shouldCreateBoardsOfDifferentSizes(){

        QuoridorGame quoripoob1= new QuoridorGame();
        quoripoob1.setBoard(10);
        assertEquals(10,quoripoob1.getBoard().getSize());

        QuoridorGame quoripoob2= new QuoridorGame();
        quoripoob2.setBoard(5);
        assertEquals(5,quoripoob2.getBoard().getSize());

        QuoridorGame quoripoob3= new QuoridorGame();
        quoripoob3.setBoard(10);
        assertEquals(10,quoripoob3.getBoard().getSize());
    }     
  
    
    @Test
    public void shouldAssignBarriersToPlayers(){
        QuoridorGame quoripoob1= new QuoridorGame();
        quoripoob1.setBoard(8);
        quoripoob1.setPlayer("H");
        quoripoob1.setPlayers(0, "Pedro", Color.RED);
        quoripoob1.setPlayers(1,"Jose,",Color.BLUE);
        quoripoob1.setBarriers(5, 2, 1,1);

        assertEquals(5,quoripoob1.getPlayers()[0].getNumberBarrier("Normal"));
        assertEquals(2,quoripoob1.getPlayers()[0].getNumberBarrier("Allied"));
        assertEquals(1,quoripoob1.getPlayers()[0].getNumberBarrier("Temporary"));
        assertEquals(1,quoripoob1.getPlayers()[0].getNumberBarrier("Large"));

        QuoridorGame quoripoob2= new QuoridorGame();
        quoripoob2.setBoard(14);
        quoripoob2.setPlayer("M");
        quoripoob2.setPlayers(0, "Pedro", Color.RED);
        quoripoob2.setPlayers(1,"Jose,",Color.BLUE);
        quoripoob2.setBarriers(0, 7, 3,5);

        assertEquals(0,quoripoob2.getPlayers()[0].getNumberBarrier("Normal"));
        assertEquals(7,quoripoob2.getPlayers()[0].getNumberBarrier("Allied"));
        assertEquals(3,quoripoob2.getPlayers()[0].getNumberBarrier("Temporary"));
        assertEquals(5,quoripoob2.getPlayers()[0].getNumberBarrier("Large"));


        
    }  
    
    
    @Test
    public void shouldMoveOrthogonallyAPawn(){
        quoripoob = new QuoridorGame();
        quoripoob.setBoard(9); 
        quoripoob.setDifficult("H");
        quoripoob.setPlayers(0, "Player1",Color.RED);
        quoripoob.setPlayers(1, "Player2", Color.BLUE);
        playerTab player =quoripoob.getActualPlayer();
        box initialBox = player.getCurrentBox();
        int initialRow = initialBox.getRow();
        int initialCol = initialBox.getColumn();
        try
        {
            quoripoob.moveTab("N"); 
        }   
        catch(QuoripoobException e){
            fail();
        }
        box newBox = player.getCurrentBox();
        
        assertEquals(initialRow +1, newBox.getRow());
        assertEquals(initialCol, newBox.getColumn());

        try{
            quoripoob.moveTab("S");
            quoripoob.moveTab("E");
            quoripoob.moveTab("N");
            quoripoob.moveTab("W");
        }
        catch(QuoripoobException e){
            fail();
        }

        newBox = player.getCurrentBox();
        assertEquals(initialRow+1, newBox.getRow());
        assertEquals(initialCol, newBox.getColumn());

    }     
    

    @Test
    public void shouldMoveDiagonallyAPawn(){

        quoripoob = new QuoridorGame();
        quoripoob.setBoard(9); 
        quoripoob.setPlayer("H");
        quoripoob.setPlayers(0, "Player1",Color.RED);
        quoripoob.setPlayers(1, "Player2", Color.BLUE);
        quoripoob.setBarriers(10, 0, 0, 0);
        playerTab player =quoripoob.getActualPlayer();
        box initialBox = player.getCurrentBox();
        int initialRow = initialBox.getRow();
        int initialCol = initialBox.getColumn();

        try{
            quoripoob.moveTab("N");
            quoripoob.moveTab("S");
            quoripoob.moveTab("N");
            quoripoob.moveTab("S");
            quoripoob.moveTab("N");
            quoripoob.moveTab("S");                        
            quoripoob.moveTab("N");  
            quoripoob.putBarrier(6, 4, "H", "Normal");
            quoripoob.moveTab("NE");  


        }
    catch(QuoripoobException e){
        fail();
    }

    assertEquals(initialRow+5,player.getCurrentBox().getRow() );
    assertEquals(initialCol+1,player.getCurrentBox().getColumn() );
    }  
    
    @Test
    public void shouldPlaceANormalBarrier(){
        quoripoob = new QuoridorGame();
        quoripoob.setBoard(9); 
        quoripoob.setPlayer("H");
        quoripoob.setPlayers(0, "Player1",Color.RED);
        quoripoob.setPlayers(1, "Player2", Color.BLUE);
        quoripoob.setBarriers(10, 0, 0, 0);


        try{
            quoripoob.putBarrier(5, 4, "V", "Normal");
            quoripoob.putBarrier(1, 1, "H", "Normal");

        }
    catch(QuoripoobException e){
        fail();
    }
        assertTrue(quoripoob.getBoard().getBox(5, 4).hasBarrier("W"));
        assertTrue(quoripoob.getBoard().getBox(6, 4).hasBarrier("W"));
        assertTrue(quoripoob.getBoard().getBox(5, 3).hasBarrier("E"));
        assertTrue(quoripoob.getBoard().getBox(6, 3).hasBarrier("E"));

        assertTrue(quoripoob.getBoard().getBox(0, 1).hasBarrier("S"));
        assertTrue(quoripoob.getBoard().getBox(0, 2).hasBarrier("S"));
        assertTrue(quoripoob.getBoard().getBox(1, 1).hasBarrier("N"));
        assertTrue(quoripoob.getBoard().getBox(1, 2).hasBarrier("N"));

    }     
    
    
    @Test
    public void shouldMoveAPawnOverAPawn(){
        quoripoob = new QuoridorGame();
        quoripoob.setBoard(10); 
        quoripoob.setPlayer("H");
        quoripoob.setPlayers(0, "Player1",Color.RED);
        quoripoob.setPlayers(1, "Player2", Color.BLUE);
        quoripoob.setBarriers(10, 0, 0, 0);
        playerTab player =quoripoob.getActualPlayer();
        box initialBox = player.getCurrentBox();
        int initialRow = initialBox.getRow();
        int initialCol = initialBox.getColumn();

        try{
            quoripoob.moveTab("N");
            quoripoob.moveTab("S");
            quoripoob.moveTab("N");
            quoripoob.moveTab("S");
            quoripoob.moveTab("N");
            quoripoob.moveTab("S");                        
            quoripoob.moveTab("N");  
            quoripoob.moveTab("S");      
            quoripoob.moveTab("N");
 
                        
        }
    catch(QuoripoobException e){
        fail();
    }

    assertEquals(initialRow+6,player.getCurrentBox().getRow() );
    assertEquals(initialCol,player.getCurrentBox().getColumn() );

    QuoridorGame quoripoob1 = new QuoridorGame();
    quoripoob1.setBoard(10); 
    quoripoob1.setPlayer("H");
    quoripoob1.setPlayers(0, "Player1",Color.RED);
    quoripoob1.setPlayers(1, "Player2", Color.BLUE);
    quoripoob1.setBarriers(10, 0, 0, 0);
    playerTab player1 =quoripoob1.getActualPlayer();
    box initialBox1 = player1.getCurrentBox();
    int initialRow1 = initialBox1.getRow();
    int initialCol1 = initialBox1.getColumn();

    try{
        quoripoob1.moveTab("N");
        quoripoob1.moveTab("S");
        quoripoob1.moveTab("N");
        quoripoob1.moveTab("S");
        quoripoob1.moveTab("N");
        quoripoob1.moveTab("S");                        
        quoripoob1.moveTab("N");  
        quoripoob1.moveTab("S");      
        quoripoob1.moveTab("E");
        quoripoob1.moveTab("S");
        quoripoob1.moveTab("W");
    }
catch(QuoripoobException e){
    fail();
}

    assertEquals(initialRow1+4,player1.getCurrentBox().getRow() );
    assertEquals(initialCol1+1,player1.getCurrentBox().getColumn() );

    }  
    
    
    @Test
    public void shouldNotMoveAPawnOverANonAlliedBarrier(){
        quoripoob = new QuoridorGame();
        quoripoob.setBoard(9); 
        quoripoob.setPlayer("H");
        quoripoob.setPlayers(0, "Player1",Color.RED);
        quoripoob.setPlayers(1, "Player2", Color.BLUE);
        quoripoob.setBarriers(10, 0, 0, 0);
        playerTab player =quoripoob.getActualPlayer();
        box initialBox = player.getCurrentBox();
        int initialRow = initialBox.getRow();
        int initialCol = initialBox.getColumn();

        try{
            quoripoob.moveTab("N");
            quoripoob.moveTab("S");
            quoripoob.putBarrier(1, 4, "H", "Normal");
            quoripoob.moveTab("S");
            quoripoob.putBarrier(1, 4, "V","Temporary");
            quoripoob.moveTab("E");
       
        }
        catch(QuoripoobException e){
        
        }
    assertEquals(initialRow+1,player.getCurrentBox().getRow() );
    assertEquals(initialCol,player.getCurrentBox().getColumn() );
    
    try{
        quoripoob.moveTab("W");
        quoripoob.putBarrier(1,5,"V","Large");
        quoripoob.moveTab("W");

    }  
    catch(QuoripoobException e){

    }

    assertEquals(initialRow+1,player.getCurrentBox().getRow() );
    assertEquals(initialCol+1,player.getCurrentBox().getColumn() );
    }
    
    @Test
    public void shouldMoveAPawnOverAnAlliedBarrier(){

        quoripoob = new QuoridorGame();
        quoripoob.setBoard(9); 
        quoripoob.setPlayer("H");
        quoripoob.setPlayers(0, "Player1",Color.RED);
        quoripoob.setPlayers(1, "Player2", Color.BLUE);
        quoripoob.setBarriers(5, 3, 0, 0);
        playerTab player =quoripoob.getActualPlayer();
        box initialBox = player.getCurrentBox();
        int initialRow = initialBox.getRow();
        int initialCol = initialBox.getColumn();

        try{
            quoripoob.moveTab("N");
            quoripoob.moveTab("S");
            quoripoob.moveTab("N");
            quoripoob.moveTab("S");
            quoripoob.moveTab("N");
            quoripoob.moveTab("S");  
            quoripoob.putBarrier(4, 4, "H", "Allied");
            quoripoob.moveTab("E");
            quoripoob.moveTab("N");

        } 

        catch(QuoripoobException e){
            fail();
        }

        assertEquals(initialRow+4,player.getCurrentBox().getRow() );
        assertEquals(initialCol,player.getCurrentBox().getColumn() );

    }    
    
    
    @Test
    public void shouldKnowWhenSomeoneWonTheGame() {

    }
    
    @Test
    public void shouldKnowTheBarriersLeftForEachPlayer(){
        quoripoob = new QuoridorGame();
        quoripoob.setBoard(9); 
        quoripoob.setPlayer("H");
        quoripoob.setPlayers(0, "Player1",Color.RED);
        quoripoob.setPlayers(1, "Player2", Color.BLUE);
        quoripoob.setBarriers(5, 3, 1,1);
        playerTab player1 =quoripoob.getPlayers()[0];
        playerTab player2 =quoripoob.getPlayers()[1];

        try{
            quoripoob.putBarrier(5, 4, "V", "Normal");
            quoripoob.putBarrier(1, 4, "H", "Normal");
            quoripoob.putBarrier(1, 4, "V","Temporary");
            quoripoob.putBarrier(7, 4, "H", "Large");
            quoripoob.putBarrier(4 , 4, "H", "Normal");
            quoripoob.putBarrier(3, 6, "V","Allied");
       
        }
        catch(QuoripoobException e){

            fail();
        
        }

        assertEquals(player1.getNumberBarrier("Normal"),3);
        assertEquals(player1.getNumberBarrier("Allied"),3);
        assertEquals(player1.getNumberBarrier("Large"),1);
        assertEquals(player1.getNumberBarrier("Temporary"),0);
        assertEquals(player2.getNumberBarrier("Normal"),4);
        assertEquals(player2.getNumberBarrier("Allied"),2);
        assertEquals(player2.getNumberBarrier("Large"),0);
        assertEquals(player2.getNumberBarrier("Temporary"),1);


    } 
    
    @Test
    public void shouldNotBlockThePassageOfAPlayer() {

        quoripoob = new QuoridorGame();
        quoripoob.setBoard(8);
        quoripoob.setPlayer("H");
        quoripoob.setPlayers(0, "Player1", Color.RED);
        quoripoob.setPlayers(1, "Player2", Color.BLUE);
        quoripoob.setBarriers(5, 3, 1, 1);
        String message="Not Failed";

        try {
            quoripoob.putBarrier(1, 0, "H", "Normal");
            quoripoob.putBarrier(1, 2, "H", "Normal");
            quoripoob.putBarrier(1, 4, "H", "Normal");
            quoripoob.putBarrier(1, 6, "H", "Normal");
        } catch (QuoripoobException e) {
            message=e.getMessage();
        }

        assertEquals(message , QuoripoobException.PathBlocked);
    }
    


    @Test
    public void shouldMeetNormalBarrierConditions() {
        String message = "Not Failed";

        quoripoob = new QuoridorGame();
        quoripoob.setBoard(9); 
        quoripoob.setPlayer("H");
        quoripoob.setPlayers(0, "Player1",Color.RED);
        quoripoob.setPlayers(1, "Player2", Color.BLUE);
        quoripoob.setBarriers(10, 0, 0,0);

        // Verifica que una barrera normal se puede colocar en una posición válida
        try {
            quoripoob.putBarrier(4, 4, "H", "Normal");
            quoripoob.putBarrier(5, 5, "V", "Normal");
        } catch (QuoripoobException e) {
            message = e.getMessage();
        }

        assertEquals("Not Failed", message);

        // Verifica que una barrera no se puede colocar fuera del tablero
        try {
            quoripoob.putBarrier(9, 9, "H", "Normal");
        } catch (QuoripoobException e) {
            message = e.getMessage();
        }

        assertEquals(QuoripoobException.InvalidPlaceBarrier, message);

        // Verifica que una barrera no se puede colocar superponiéndose con otra barrera existente
        message = "Not Failed"; // Reset the message
        try {
            quoripoob.putBarrier(6, 6, "H", "Normal");
            quoripoob.putBarrier(6, 7, "H", "Normal");
        } catch (QuoripoobException e) {
            message = e.getMessage();
        }

        assertEquals(QuoripoobException.InvalidPlaceBarrier, message);

        // Verifica que una barrera no bloquee completamente el paso para llegar a la meta
        message = "Not Failed"; // Reset the message
        try {
            quoripoob.putBarrier(1 , 0, "H", "Normal");
            quoripoob.putBarrier(1, 2, "H", "Normal");
            quoripoob.putBarrier(1, 4, "H", "Normal");
            quoripoob.putBarrier(1, 6, "H", "Normal");
            quoripoob.putBarrier(1, 8, "V", "Normal");
            quoripoob.putBarrier(3, 7, "H", "Normal");
        } catch (QuoripoobException e) {
            message = e.getMessage();
        }

        assertEquals(QuoripoobException.PathBlocked, message);
    }

    @Test
    public void shouldMeetTemporalBarrierConditions() {
        String message = "Not Failed";

        quoripoob = new QuoridorGame();
        quoripoob.setBoard(9); 
        quoripoob.setPlayer("H");
        quoripoob.setPlayers(0, "Player1",Color.RED);
        quoripoob.setPlayers(1, "Player2", Color.BLUE);
        quoripoob.setBarriers(5, 0, 5,0);

        try {
            quoripoob.putBarrier(4, 4, "H", "Temporary");
        } catch (QuoripoobException e) {
            message = e.getMessage();
        }

        assertEquals("Not Failed", message);

        assertTrue(quoripoob.getBoard().getBox(3, 4).hasBarrier("S"));
        assertTrue(quoripoob.getBoard().getBox(3,5).hasBarrier("S"));
        assertTrue(quoripoob.getBoard().getBox(4, 4).hasBarrier("N"));
        assertTrue(quoripoob.getBoard().getBox(4, 5).hasBarrier("N"));

        for (int i = 0; i < 2; i++) {
            try{
            quoripoob.moveTab("S");
            quoripoob.moveTab("N"); 
            }
            catch(QuoripoobException e){
                fail();
            }
        }


        assertFalse(quoripoob.getBoard().getBox(3, 4).hasBarrier("S"));
        assertFalse(quoripoob.getBoard().getBox(3,5).hasBarrier("S"));
        assertFalse(quoripoob.getBoard().getBox(4, 4).hasBarrier("N"));
        assertFalse(quoripoob.getBoard().getBox(4, 5).hasBarrier("N"));

        message = "Not Failed";
        try {
            quoripoob.putBarrier(4, 4, "H", "Normal");
        } catch (QuoripoobException e) {
            message = e.getMessage();
        }

        assertEquals("Not Failed", message);
    }

    
    @Test
    public void shouldMeetLongBarrierConditions() {
        String message = "Not Failed";

        quoripoob = new QuoridorGame();
        quoripoob.setBoard(9); 
        quoripoob.setPlayer("H");
        quoripoob.setPlayers(0, "Player1",Color.RED);
        quoripoob.setPlayers(1, "Player2", Color.BLUE);
        quoripoob.setBarriers(5, 0, 0,5);

        // Verifica que una barrera larga se puede colocar en una posición válida (horizontal)
        try {
            quoripoob.putBarrier(4, 4, "H", "Large");
        } catch (QuoripoobException e) {
            message = e.getMessage();
        }

        assertEquals("Not Failed", message);

        // Verifica que la barrera larga está en su lugar (horizontal)
        assertTrue(quoripoob.getBoard().getBox(3, 4).hasBarrier("S"));
        assertTrue(quoripoob.getBoard().getBox(3,5).hasBarrier("S"));
        assertTrue(quoripoob.getBoard().getBox(4, 4).hasBarrier("N"));
        assertTrue(quoripoob.getBoard().getBox(4, 5).hasBarrier("N"));
        assertTrue(quoripoob.getBoard().getBox(3,6).hasBarrier("S"));
        assertTrue(quoripoob.getBoard().getBox(4, 6).hasBarrier("N"));

        // Verifica que una barrera larga se puede colocar en una posición válida (vertical)
        message = "Not Failed";
        try {
            quoripoob.putBarrier(5, 3, "V", "Large");
        } catch (QuoripoobException e) {
            message = e.getMessage();
        }

        assertEquals("Not Failed", message);

        // Verifica que la barrera larga está en su lugar (vertical)
        assertTrue(quoripoob.getBoard().getBox(5, 3).hasBarrier("W"));
        assertTrue(quoripoob.getBoard().getBox(5,2).hasBarrier("E"));
        assertTrue(quoripoob.getBoard().getBox(6, 3).hasBarrier("W"));
        assertTrue(quoripoob.getBoard().getBox(6, 2).hasBarrier("E"));
        assertTrue(quoripoob.getBoard().getBox(7,3).hasBarrier("W"));
        assertTrue(quoripoob.getBoard().getBox(7, 2).hasBarrier("E"));
    }

    
    @Test
    public void shouldMeetAlliedBarrierConditions() {
        String message = "Not Failed";
        quoripoob = new QuoridorGame();
        quoripoob.setBoard(9);
        quoripoob.setPlayer("H");
        quoripoob.setPlayers(0, "Player1",Color.RED);
        quoripoob.setPlayers(1, "Player2", Color.BLUE);
        quoripoob.setBarriers(5, 5, 0,0);
        playerTab player= quoripoob.getActualPlayer();

        try {
            // Coloca una barrera aliada horizontal en (4, 4)
            quoripoob.putBarrier(1, 3, "H", "Allied");

            // Verifica que la barrera está en el lugar correcto y tiene longitud 2
            
            assertTrue(quoripoob.getBoard().getBox(0, 3).hasBarrier("S"));
            assertTrue(quoripoob.getBoard().getBox(0,4).hasBarrier("S"));
            assertTrue(quoripoob.getBoard().getBox(1, 3).hasBarrier("N"));
            assertTrue(quoripoob.getBoard().getBox(1, 4).hasBarrier("N"));

            // Mueve el jugador hacia la barrera
            quoripoob.moveTab("S");
            quoripoob.moveTab("N");

            // Verifica que el jugador que la puso puede pasar a través de ella
            box currentBox = player.getCurrentBox();
            assertEquals(1, currentBox.getRow());
            assertEquals(4, currentBox.getColumn());

        } catch (QuoripoobException e) {
            message = e.getMessage();
        }

        assertEquals("Not Failed", message);
    }
    
    
    @Test
    public void shouldNotMoveOrthogonallyAPawnIfItsNotPossible(){
        fail();
    }     
    

    @Test
    public void shouldNotMoveDiagonallyAPawnIfItsNotPossible(){
        fail();
    }  
    
    @Test
    public void shouldNotPlaceANormalBarrierIfItsNotPossible(){
        fail();
    }     
    
    
    @Test
    public void shouldNotMoveAPawnOverAPawnIfItsNotPossible(){
        fail();
    }
}
