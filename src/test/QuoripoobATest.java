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
}




