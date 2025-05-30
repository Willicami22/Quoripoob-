package test;
import static org.junit.Assert.*;


import org.junit.*;

import java.awt.Color;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import domain.*;

public class QuoripoobATest {
    @Test
    public void testQuoridorGame() throws QuoripoobException {
        // Inicialización del juego
        QuoridorGame game = new QuoridorGame();
        game.setBoard(9); // Establecer el tamaño del tablero a 9x9
        game.setPlayer("H");
        game.setPlayers(0, "Player 1", Color.RED);
        game.setPlayers(1, "Player 2", Color.BLUE);
        game.setBarriers(5, 2, 1, 1); // Establecer el número de barreras para cada jugador

        assertNotNull(game.getBoard());
        assertEquals(2, game.getPlayers().length);

        // Movimiento de jugadores
        playerTab player1 = game.getPlayers()[0];
        playerTab player2 = game.getPlayers()[1];

        game.moveTab("N"); // Mover al jugador 1 una casilla hacia adelante
        assertEquals(game.getBoard().getBox(1, 4), player1.getCurrentBox());

        game.moveTab("S"); // Mover al jugador 2 una casilla hacia adelante
        assertEquals(game.getBoard().getBox(7, 4), player2.getCurrentBox());

        // Colocación de barreras
        game.putBarrier(2, 2, "H", "Normal");
        assertTrue(game.getBoard().getBox(1, 2).hasBarrier("S"));
        assertTrue(game.getBoard().getBox(2, 3).hasBarrier("N"));

        game.putBarrier(5, 5, "V", "Normal");
        assertTrue(game.getBoard().getBox(5, 5).hasBarrier("W"));
        assertTrue(game.getBoard().getBox(5, 4).hasBarrier("E"));

        // Verificar que el turno se ha pasado al jugador 1
        assertEquals(player1, game.getActualPlayer());

        // Finalización del juego
        // Realizar movimientos hasta que uno de los jugadores alcance el lado opuesto
        while (!player1.hasWon() && !player2.hasWon()) {
            if (game.getActualPlayer() == player1) {
                game.moveTab("N");
            } else {
                game.moveTab("S");  
            }
            
        }
        // Verificar que hay un ganador
        assertTrue(player1.hasWon() || player2.hasWon());
    }
    @Test
    public void testQuoridorGame2() throws QuoripoobException{
        //Inicialización del juego
        QuoridorGame game = new QuoridorGame();
        game.setPlayer("H");
        game.setMode("N");
        game.setBoard(9); 
        game.setPlayers(0, "Player 1", Color.YELLOW);
        game.setPlayers(1, "Player 2", Color.CYAN);
        game.setBarriers(5, 2, 1, 0); 

        //Verificacicón información básica del juego
        assertNotNull(game.getBoard());
        assertEquals(2, game.getPlayers().length);
        assertEquals(game.getMode(), "N");

        playerTab player1 = game.getPlayers()[0];
        playerTab player2 = game.getPlayers()[1];

        //Verificación de la información de cada jugador
        assertEquals("Player 1", player1.getName());
        assertEquals("Player 2", player2.getName());
        assertEquals(Color.YELLOW, player1.getColor());
        assertEquals(Color.CYAN, player2.getColor());

        //Verficación de la información de las barreras del jugador 1 y su comportamiento cuando se pone una barrera
        assertEquals(player1.getNumberBarrier("Normal"), 5);
        assertEquals(player1.getNumberBarrier("Allied"), 2);
        assertEquals(player1.getNumberBarrier("Temporary"), 1);
        assertEquals(player1.getNumberBarrier("Large"), 0);

        game.putBarrier(5,5,"V","Normal");
        assertEquals(player1.getNumberBarrier("Normal"), 4);

        //Verificación de la cantidad de movimientos cuando se inicia el juego y después de mover una ficha
        assertEquals(player2.getDirections().size(), 0);

        game.moveTab("E");
        assertEquals(player2.getDirections().size(), 1);
        
        //Verificación del turno
        assertEquals(game.getActualPlayer(), player1);

        game.moveTab("N");

        assertEquals(game.getActualPlayer(), player2);

        game.putBarrier(7, 7, "H", "Allied");

        assertEquals(game.getActualPlayer(), player1);
    }
}




