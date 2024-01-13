package core;

import dto.Figure;
import dto.ScoreEvent;
import dto.Square;
import lombok.Getter;
import dto.ApplicationData;
import utils.GamePainter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Field extends JPanel {
    private final static int squareSize;
    private final static int fieldWidth;
    private final static int fieldHeight;
    private final static int timerDuration;
    private int[][] spaces;
    private Figure curFigure;
    @Getter
    private Figure nextFigure;
    private final Timer timer;
    private static BufferedImage image;
    private final List<ActionListener> actionListeners;

    static {
        squareSize = ApplicationData.getSquareSize();
        fieldWidth = ApplicationData.getFieldWidth();
        fieldHeight = ApplicationData.getFieldHeight();
        timerDuration = ApplicationData.getTimerDuration();

        try {
            image = ImageIO.read(new File(Objects.requireNonNull(GamePainter.class.getResource("../GameBackground.jpg")).getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        spaces = new int[fieldHeight][fieldWidth];
        curFigure = new Figure();
        curFigure.generateFigure(fieldWidth / 2 - 1);
        nextFigure = new Figure();
        nextFigure.generateFigure(fieldWidth / 2 - 1);
        timer.start();
    }

    public void pauseGame() {
        timer.stop();
    }

    public void resumeGame() {
        timer.start();
    }

    private boolean isInEmptySpace(Square[] squares) {
        return Arrays.stream(squares).noneMatch(
                square -> square.getX() < 0 || square.getY() < 0 || square.getX() >= fieldWidth || square.getY() >= fieldHeight ||
                        spaces[square.getY()][square.getX()] != 0);
    }

    private void saveInField(Square[] squares, int color) {
        Arrays.stream(squares).forEach(square -> spaces[square.getY()][square.getX()] = color);
    }

    private void clearLine(int id) {
        for (int i = id; i > 0; --i) {
            spaces[i] = spaces[i - 1];
        }
        spaces[0] = new int[fieldWidth];
    }

    private void notifyListeners(ActionEvent event) {
        for (ActionListener actionListener : actionListeners) {
            actionListener.actionPerformed(event);
        }
    }

    private void clearFullLines() {
        int lines = 0;
        for (int i = 0; i < fieldHeight; i++) {
            for (int j = 0; j < fieldWidth; j++) {
                if (spaces[i][j] == 0) {
                    break;
                }
                if (j + 1 == fieldWidth) {
                    clearLine(i);
                    ++lines;
                }
            }
        }
        if (lines != 0) {
            ScoreEvent scoreEvent = new ScoreEvent(this, lines);
            notifyListeners(scoreEvent);
        }
    }

    public void handleFigureLanding() {
        saveInField(curFigure.getSquares(), curFigure.getColor());
        clearFullLines();
        Figure.copy(nextFigure, curFigure);
        nextFigure.generateFigure(fieldWidth / 2 - 1);
        ActionEvent actionEvent = new ActionEvent(this, 1, "repaint");
        notifyListeners(actionEvent);
    }

    public void moveLeft() {
        for (Square square : curFigure.getSquares()) {
            if (square.getX() == 0 || spaces[square.getY()][square.getX() - 1] != 0) {
                return;
            }
        }
        curFigure.moveLeft();
    }


    public void moveDown() {
        for (Square square : curFigure.getSquares()) {
            if (square.getY() + 1 == fieldHeight || spaces[square.getY() + 1][square.getX()] != 0) {
                handleFigureLanding();
                return;
            }
        }
        curFigure.moveDown();
    }

    public void moveRight() {
        for (Square square : curFigure.getSquares()) {
            if (square.getX() + 1 == fieldWidth || spaces[square.getY()][square.getX() + 1] != 0) {
                return;
            }
        }
        curFigure.moveRight();
    }

    public void rotateLeft() {
        Square[] rotatedSquares = curFigure.rotateLeft();
        if (isInEmptySpace(rotatedSquares)) {
            curFigure.setSquares(rotatedSquares);
        }
    }

    public void rotateRight() {
        Square[] rotatedSquares = curFigure.rotateRight();
        if (isInEmptySpace(rotatedSquares)) {
            curFigure.setSquares(rotatedSquares);
        }
    }

    public void drop() {
        do {
            curFigure.moveDown();
        } while (isInEmptySpace(curFigure.getSquares()));
        curFigure.moveUp();
        handleFigureLanding();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        GamePainter.paintCurFigure(g, curFigure, 0, 0);
        for (int i = 0; i < fieldHeight; ++i) {
            for (int j = 0; j < fieldWidth; ++j) {
                if (spaces[i][j] != 0) {
                    GamePainter.paintSquare(g, j, i, spaces[i][j]);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
