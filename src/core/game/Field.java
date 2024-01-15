package core.game;

import dto.Figure;
import dto.ScoreEvent;
import dto.Square;
import lombok.Getter;
import config.ApplicationData;
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
    private int[][] spaces;
    private Figure curFigure;
    @Getter
    private Figure nextFigure;
    private final Timer timer;
    private final Timer lastFigureTimer;
    private Timer stoppedTimer;
    private float lastFigureTransparency;
    private float lastFigureDTransparency;
    private float lastFigurePaintTimes;
    private static BufferedImage image;
    private static final int[] speeds;
    private static final int[] neededLines;
    private int curSpeed;
    private int lines;
    private final List<ActionListener> actionListeners;

    static {
        squareSize = ApplicationData.getSquareSize();
        fieldWidth = ApplicationData.getFieldWidth();
        fieldHeight = ApplicationData.getFieldHeight();
        speeds = new int[]{500, 250, 100, 50, 20};
        neededLines = new int[]{0, 40, 80, 120, 160};
    }

    {
        actionListeners = new ArrayList<>();
        timer = new Timer(500, e -> {
            moveDown();
            repaint();
        });
        lastFigureTimer = new Timer(50, e -> lastFigureTimerFunction());
        try {
            image = ImageIO.read(new File(Objects.requireNonNull(this.getClass().getResource("GameBackground.jpg")).getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        lines = 0;
        curSpeed = 0;
        timer.setDelay(500);
        timer.start();
    }

    public void pauseGame() {
        if (timer.isRunning()) {
            timer.stop();
            stoppedTimer = timer;
        } else if (lastFigureTimer.isRunning()) {
            lastFigureTimer.stop();
            stoppedTimer = lastFigureTimer;
        }
    }

    public void resumeGame() {
        if (stoppedTimer != null) {
            stoppedTimer.start();
            stoppedTimer = null;
        }
    }

    public boolean isHandleMoves() {
        return timer.isRunning();
    }

    private void lastFigureTimerFunction() {
        lastFigureTransparency += lastFigureDTransparency;
        if (lastFigureTransparency >= 1f) {
            lastFigureTransparency = 1f;
            lastFigureDTransparency *= -1;
            --lastFigurePaintTimes;
        } else if (lastFigureTransparency <= 0) {
            lastFigureTransparency = 0f;
            lastFigureDTransparency *= -1;
            --lastFigurePaintTimes;
        }
        repaint();
        if (lastFigurePaintTimes == 0) {
            lastFigureTimer.stop();
            ActionEvent actionEvent = new ActionEvent(this, 1, "end game");
            notifyListeners(actionEvent);
        }
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

    private void addLines(int lines) {
        this.lines += lines;
        if (curSpeed + 1 < speeds.length && neededLines[curSpeed + 1] <= this.lines) {
            ++curSpeed;
            timer.setDelay(speeds[curSpeed]);
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
            addLines(lines);
            ScoreEvent scoreEvent = new ScoreEvent(this, lines);
            notifyListeners(scoreEvent);
        }
    }

    private void startLastFigureTimer() {
        lastFigureTransparency = 0f;
        lastFigureDTransparency = 0.5f;
        lastFigurePaintTimes = 6;
        lastFigureTimer.start();
    }

    public void handleFigureLanding() {
        saveInField(curFigure.getSquares(), curFigure.getColor());
        clearFullLines();
        Figure.copy(nextFigure, curFigure);
        if (!isInEmptySpace(nextFigure.getSquares())) {
            timer.stop();
            startLastFigureTimer();
            return;
        }
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
        while (isInEmptySpace(curFigure.getSquares())) {
            curFigure.moveDown();
        }
        curFigure.moveUp();
        handleFigureLanding();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i < fieldHeight; ++i) {
            for (int j = 0; j < fieldWidth; ++j) {
                if (spaces[i][j] != 0) {
                    GamePainter.paintSquare(g, j, i, spaces[i][j]);
                }
            }
        }

        if (timer.isRunning()) {
            GamePainter.paintCurFigure(g, curFigure, 0, 0);
        } else if (lastFigureTimer.isRunning()) {
            GamePainter.paintLastFigure(g, curFigure, lastFigureTransparency);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
