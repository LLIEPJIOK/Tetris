import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Field extends JPanel {
    private final static int squareSize;
    private final static int fieldWidth;
    private final static int fieldHeight;
    private final static int timerDuration;
    private int[][] field;
    private Figure figure;
    private Timer timer;
    private final List<ActionListener> actionListeners;

    static {
        squareSize = ApplicationConstants.getSquareSize();
        fieldWidth = ApplicationConstants.getFieldWidth();
        fieldHeight = ApplicationConstants.getFieldHeight();
        timerDuration = ApplicationConstants.getTimerDuration();
    }

    {
        actionListeners = new ArrayList<>();
        timer = new Timer(timerDuration, e -> {
            moveDown();
            repaint();
        });

        setSize(new Dimension(fieldWidth * squareSize, fieldHeight * squareSize));
    }

    public void addActionListener(ActionListener listener) {
        actionListeners.add(listener);
    }

    public void startNewGame() {
        field = new int[fieldHeight][fieldWidth];
        figure = new Figure();
        figure.generateFigure(fieldWidth / 2 - 1);
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
                square -> square.getX() < 0 || square.getX() >= fieldWidth || square.getY() >= fieldHeight ||
                        field[square.getY()][square.getX()] != 0);
    }

    private void saveInField(Square[] squares) {
        Arrays.stream(squares).forEach(square -> field[square.getY()][square.getX()] = 1);
    }

    private void clearLine(int id) {
        for (int i = id; i > 0; --i) {
            field[i] = field[i - 1];
        }
        field[0] = new int[fieldWidth];
    }

    private void notifyListeners(int lines) {
        ScoreEvent scoreEvent = new ScoreEvent(this, lines);
        for (ActionListener actionListener : actionListeners) {
            actionListener.actionPerformed(scoreEvent);
        }
    }

    private void clearFullLines() {
        int lines = 0;
        for (int i = 0; i < fieldHeight; i++) {
            for (int j = 0; j < fieldWidth; j++) {
                if (field[i][j] == 0) {
                    break;
                }
                if (j + 1 == fieldWidth) {
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
        figure.generateFigure(fieldWidth / 2 - 1);
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
        g.drawRect(0, 0, fieldWidth * squareSize, fieldHeight * squareSize);

        figure.paint(g);
        for (int i = 0; i < fieldHeight; ++i) {
            for (int j = 0; j < fieldWidth; ++j) {
                if (field[i][j] != 0) {
                    Painter.paintSquare(g, j, i);
                }
            }
        }
    }
}
