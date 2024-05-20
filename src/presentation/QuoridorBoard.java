package presentation;

import javax.swing.*;
import java.awt.*;
import domain.*;

public class QuoridorBoard extends JPanel {

    private QuoridorGame game;
    private Color[] playerColors = {Color.RED, Color.BLUE}; 

    public QuoridorBoard(QuoridorGame game) {
        this.game = game;
        setPreferredSize(new Dimension(800, 800)); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        drawPlayers(g);
    }

    private void drawBoard(Graphics g) {
        int boxSize = getWidth() / game.getBoard().getSize();
        for (int i = 0; i < game.getBoard().getSize(); i++) {
            for (int j = 0; j < game.getBoard().getSize(); j++) {
                int x = j * boxSize;
                int y = i * boxSize;
                g.setColor(Color.WHITE); 
                g.fillRect(x, y, boxSize, boxSize);
                g.setColor(Color.BLACK); 
                g.drawRect(x, y, boxSize, boxSize);
            }
        }
    }

    private void drawPlayers(Graphics g) {
        int boxSize = getWidth() / game.getBoard().getSize();
        for (playerTab player : game.getPlayers()) {
            box position = player.getCurrentBox();
            int x = position.getColumn() * boxSize + boxSize / 4;
            int y = position.getRow() * boxSize + boxSize / 4;
            int size = boxSize / 2;
            g.setColor(playerColors[0]); 
            g.fillOval(x, y, size, size); 
        }
    }
}


