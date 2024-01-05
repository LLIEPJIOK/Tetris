import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Field extends JPanel {
    private final static int SQUARE_SIZE;
    private final static int WIDTH;
    private final static int HEIGHT;
    private final static int TIMER_DURATION;
    private int[][] field;
    private Figure figure;
    private Timer timer;
    private final List<ActionListener> actionListeners;

    static {
        SQUARE_SIZE = 20;
        WIDTH = 10;
        HEIGHT = 20;
        TIMER_DURATION = 500;
    }

    {
        actionListeners = new ArrayList<>();

        setSize(new Dimension(WIDTH * SQUARE_SIZE, HEIGHT * SQUARE_SIZE));
    }

    public void addActionListener(ActionListener listener) {
        actionListeners.add(listener);
    }

    public void startNewGame() {
        field = new int[HEIGHT][WIDTH];
        figure = new Figure();
        timer = new Timer(TIMER_DURATION, e -> {
            moveDown();
            repaint();
        });
        timer.start();
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

    private void notifyListeners(int lines) {
        ScoreEvent scoreEvent = new ScoreEvent(this, lines);
        for (ActionListener actionListener : actionListeners) {
            actionListener.actionPerformed(scoreEvent);
        }
    }

    private void clearFullLines() {
        int lines = 0;
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (field[i][j] == 0) {
                    break;
                }
                if (j + 1 == WIDTH) {
                    clearLine(i);
                    ++lines;
                }
            }
        }
        if (lines != 0) {
            notifyListeners(lines);
        }
    }

    public void handleFigureLanding() {
        saveInField(figure.getSquares());
        clearFullLines();
        figure.generateFigure();
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
            handleFigureLanding();
        }
    }

    public void moveRight() {
        figure.moveRight();
        if (isOutOfSpace(figure.getSquares())) {
            figure.moveLeft();
        }
    }

    public void rotateLeft() {
        Square[] rotatedSquares = figure.rotateLeft();
        if (!isOutOfSpace(rotatedSquares)) {
            figure.setSquares(rotatedSquares);
        }
    }

    public void rotateRight() {
        Square[] rotatedSquares = figure.rotateRight();
        if (!isOutOfSpace(rotatedSquares)) {
            figure.setSquares(rotatedSquares);
        }
    }

    public void fallDown() {
        do {
            figure.moveDown();
        } while (!isOutOfSpace(figure.getSquares()));
        figure.moveUp();
        handleFigureLanding();
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
