package presentation;

import javax.swing.*;
import java.awt.*;
import domain.*;

public class QuoridorBoard extends JPanel {

    private QuoridorGame game;

    public QuoridorBoard(QuoridorGame game) {
        this.game = game;
        setPreferredSize(new Dimension(800, 800)); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        drawPlayers(g);
        drawBarriers(g);
        drawCoordinates(g);
    }

    private void drawBoard(Graphics g) {
        int boxSize = getWidth() / game.getBoard().getSize();
        box[][] boxes = game.getBoard().getBoxes();
        for (box[] i : boxes) {
            for (box j : i) {
                int x = j.getColumn() * boxSize;
                int y = j.getRow() * boxSize;

                g.setColor(j.getColor());
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
            g.setColor(player.getColor());
            g.fillOval(x, y, size, size);
        }
    }

    private void drawBarriers(Graphics g) {
        int boxSize = getWidth() / game.getBoard().getSize();
        for (int i = 0; i < game.getBoard().getSize(); i++) {
            for (int j = 0; j < game.getBoard().getSize(); j++) {
                box currentBox = game.getBoard().getBox(i, j);
                int x = j * boxSize;
                int y = i * boxSize;

                g.setColor(Color.YELLOW);

                if (currentBox.hasBarrier("N")) {
                    g.setColor(currentBox.getBarrier("N").getColor());
                    g.fillRect(x, y - boxSize / 10, boxSize, boxSize / 5);
                }
                if (currentBox.hasBarrier("S")) {
                    g.setColor(currentBox.getBarrier("S").getColor());
                    g.fillRect(x, y + boxSize - boxSize / 10, boxSize, boxSize / 5);
                }
                if (currentBox.hasBarrier("E")) {
                    g.setColor(currentBox.getBarrier("E").getColor());
                    g.fillRect(x + boxSize - boxSize / 10, y, boxSize / 5, boxSize);
                }
                if (currentBox.hasBarrier("W")) {
                    g.setColor(currentBox.getBarrier("W").getColor());
                    g.fillRect(x - boxSize / 10, y, boxSize / 5, boxSize);
                }
            }
        }
    }

    private void drawCoordinates(Graphics g) {
        int boxSize = getWidth() / game.getBoard().getSize();
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 12));

        // Draw row numbers on the left side
        for (int i = 0; i < game.getBoard().getSize(); i++) {
            int y = i * boxSize + boxSize / 2;
            g.drawString(String.valueOf(i + 1), 5, y);
        }

        // Draw column numbers on the top side
        for (int j = 0; j < game.getBoard().getSize(); j++) {
            int x = j * boxSize + boxSize / 2;
            g.drawString(String.valueOf(j + 1), x, 15);
        }
    }
}
