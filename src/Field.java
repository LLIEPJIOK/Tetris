import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Field extends JComponent implements KeyListener {
    private final static int SQUARE_SIZE;
    private final static int WIDTH;
    private final static int HEIGHT;
    private final static int TIMER_DURATION;
    private final int[][] field;
    private final Figure curFigure;

    static {
        SQUARE_SIZE = 20;
        WIDTH = 10;
        HEIGHT = 20;
        TIMER_DURATION = 500;
    }

    {
        field = new int[HEIGHT][WIDTH];
        curFigure = new Figure(field);
        Timer gameTimer = new Timer(TIMER_DURATION, e -> {
            curFigure.moveDown();
            repaint();
        });
        gameTimer.start();

        setSize(WIDTH * SQUARE_SIZE + 2, HEIGHT * SQUARE_SIZE + 2);
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawRect(0, 0, WIDTH * SQUARE_SIZE, HEIGHT * SQUARE_SIZE);

        curFigure.paint(g);
        for (int i = 0; i < HEIGHT; ++i) {
            for (int j = 0; j < WIDTH; ++j) {
                if (field[i][j] != 0) {
                    Painter.paintSquare(g, j, i);
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_A -> {
                curFigure.moveLeft();
            }
            case KeyEvent.VK_S -> {
                curFigure.moveDown();
            }
            case KeyEvent.VK_D -> {
                curFigure.moveRight();
            }
            case KeyEvent.VK_R -> {
                curFigure.rotateRight();
            }
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // empty body
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // empty body
    }
}
