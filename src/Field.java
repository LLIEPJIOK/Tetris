import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Field extends JComponent {
    private final static int SQUARE_SIZE;
    private final static int WIDTH;
    private final static int HEIGHT;
    private final static int TIMER_DURATION;
    private final int[][] field;
    private final Figure figure;
    private final Timer timer;

    static {
        SQUARE_SIZE = 20;
        WIDTH = 10;
        HEIGHT = 20;
        TIMER_DURATION = 500;
    }

    {
        field = new int[HEIGHT][WIDTH];
        figure = new Figure();
         timer = new Timer(TIMER_DURATION, e -> {
            moveDown();
            repaint();
        });
        timer.start();

        setSize(WIDTH * SQUARE_SIZE, HEIGHT * SQUARE_SIZE);
    }

    public void pauseGame() {
        timer.stop();
    }

    public void resumeGame() {
        timer.start();
    }

    private boolean isOutOfSpace(Square[] squares) {
        return Arrays.stream(squares).anyMatch(
                square -> square.getX() < 0 || square.getX() >= WIDTH || square.getY() >= HEIGHT ||
                        field[square.getY()][square.getX()] != 0);
    }

    private void saveInField(Square[] squares) {
        Arrays.stream(squares).forEach(square -> field[square.getY()][square.getX()] = 1);
    }

    private void clearLine(int id) {
        for (int i = id; i > 0; --i) {
            field[i] = field[i - 1];
        }
        field[0] = new int[WIDTH];
    }

    private void clearFullLines() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (field[i][j] == 0) {
                    break;
                }
                if (j + 1 == WIDTH) {
                    clearLine(i);
                }
            }
        }
    }

    public void moveLeft() {
        figure.moveLeft();
        if (isOutOfSpace(figure.getSquares())) {
            figure.moveRight();
        }
    }


    public void moveDown() {
        figure.moveDown();
        if (isOutOfSpace(figure.getSquares())) {
            figure.moveUp();
            saveInField(figure.getSquares());
            figure.generateFigure();
            clearFullLines();
        }
    }

    public void moveRight() {
        figure.moveRight();
        if (isOutOfSpace(figure.getSquares())) {
            figure.moveLeft();
        }
    }

    public void rotateRight() {
        Square[] rotatedSquares = figure.rotateRight();
        if (!isOutOfSpace(rotatedSquares)) {
            figure.setSquares(rotatedSquares);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.BLACK);
        g.drawRect(0, 0, WIDTH * SQUARE_SIZE, HEIGHT * SQUARE_SIZE);

        figure.paint(g);
        for (int i = 0; i < HEIGHT; ++i) {
            for (int j = 0; j < WIDTH; ++j) {
                if (field[i][j] != 0) {
                    Painter.paintSquare(g, j, i);
                }
            }
        }
    }
}
