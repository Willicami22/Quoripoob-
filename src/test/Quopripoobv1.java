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
    }     
    

    @Test
    public void shouldMoveDiagonallyAPawn(){
        fail();
    }  
    
    @Test
    public void shouldPlaceANormalBarrier(){
        fail();
    }     
    
    
    @Test
    public void shouldMoveAPawnOverAPawn(){
        fail();
    } 
    
    
    @Test
    public void shouldNotMoveAPawnOverANonAlliedBarrier(){
        fail();
    }  
    
    @Test
    public void shouldMoveAPawnOverAnAlliedBarrier(){
        fail();
    } 
    
    
    @Test
    public void shouldKnowWhenSomeoneWonTheGame(){
        fail();
    } 
    
    @Test
    public void shouldKnowTheBarriersLeftForEachPlayer(){
        fail();
    } 
    
    @Test
    public void shouldNotBlockThePassageOfAPlayer(){
        fail();
    } 
    
    @Test
    public void shouldMeetNormalBarrierConditions(){
        fail();
    } 
    
    @Test
    public void shouldMeetTemporalBarrierConditions(){
        fail();
    } 
    
    @Test
    public void shouldMeetLongBarrierConditions(){
        fail();
    } 
    
    @Test
    public void shouldMeetAlliedBarrierConditions(){
        fail();
    } 
    
    
    @Test
    public void shouldNotCreateABoardIfItsNotPossible(){
        fail();
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
