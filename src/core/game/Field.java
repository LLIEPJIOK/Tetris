package core.game;

import dto.Figure;
import dto.ScoreEvent;
import dto.Square;
import lombok.Getter;
import config.ApplicationData;
import utils.GamePainter;
import utils.SoundPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
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
    private Figure landingFigure;
    @Getter
    private Figure nextFigure;
    private final Timer timer;
    private final Timer lastFigureTimer;
    private Timer stoppedTimer;
    private final Timer changeBrightnessTimer;
    private float lastFigureTransparency;
    private float lastFigureDTransparency;
    private float lastFigurePaintTimes;
    private static BufferedImage image;
    private static BufferedImage extraImage;
    private static final int[] speeds;
    private static final int[] neededLines;
    private int curSpeed;
    private int lines;
    private float brightness;
    private float dBrightness;
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
        changeBrightnessTimer = new Timer(10, e -> changeSpeedTimerFunction());
        try {
            image = ImageIO.read(new File(Objects.requireNonNull(this.getClass().getResource("GameBackground.jpg")).getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        extraImage = image;

        setSize(new Dimension(fieldWidth * squareSize, fieldHeight * squareSize));
    }

    public void addActionListener(ActionListener listener) {
        actionListeners.add(listener);
    }

    public void startNewGame() {
        spaces = new int[fieldHeight][fieldWidth];
        landingFigure = new Figure();

        curFigure = new Figure();
        curFigure.generateFigure(fieldWidth / 2 - 1);

        Figure.copy(curFigure, landingFigure);
        moveLandingFigureInEmptySpace();

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

    private void changeSpeedTimerFunction() {
        brightness += dBrightness;
        if (brightness >= 20) {
            brightness = 20;
            dBrightness *= -1;
        } else if (brightness <= 1) {
            brightness = 1;
            changeBrightnessTimer.stop();
            repaint();
        }
        RescaleOp rescaleOp = new RescaleOp(brightness, 0, null);
        image = rescaleOp.filter(extraImage, null);
        repaint();
    }

    private void moveLandingFigureInEmptySpace() {
        while (isInEmptySpace(landingFigure.getSquares())) {
            landingFigure.moveDown();
        }
        while (!isInEmptySpace(landingFigure.getSquares())) {
            landingFigure.moveUp();
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
            SoundPlayer.playChangeSpeedMusic();
            brightness = 1;
            dBrightness = 1.5f;
            changeBrightnessTimer.start();
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
            SoundPlayer.stopGameMusic();
            SoundPlayer.playGameOverMusic();
            return;
        }
        Figure.copy(curFigure, landingFigure);
        moveLandingFigureInEmptySpace();
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
        Figure.copy(curFigure, landingFigure);
        moveLandingFigureInEmptySpace();
    }

    public void moveDown() {
        for (Square square : curFigure.getSquares()) {
            if (square.getY() + 1 == fieldHeight || spaces[square.getY() + 1][square.getX()] != 0) {
                handleFigureLanding();
                return;
            }
        }
        curFigure.moveDown();
        Figure.copy(curFigure, landingFigure);
        moveLandingFigureInEmptySpace();
    }

    public void moveRight() {
        for (Square square : curFigure.getSquares()) {
            if (square.getX() + 1 == fieldWidth || spaces[square.getY()][square.getX() + 1] != 0) {
                return;
            }
        }
        curFigure.moveRight();
        Figure.copy(curFigure, landingFigure);
        moveLandingFigureInEmptySpace();
    }

    public void rotateLeft() {
        Square[] rotatedSquares = curFigure.rotateLeft();
        if (isInEmptySpace(rotatedSquares)) {
            curFigure.setSquares(rotatedSquares);
            Figure.copy(curFigure, landingFigure);
            moveLandingFigureInEmptySpace();
        }
    }

    public void rotateRight() {
        Square[] rotatedSquares = curFigure.rotateRight();
        if (isInEmptySpace(rotatedSquares)) {
            curFigure.setSquares(rotatedSquares);
            Figure.copy(curFigure, landingFigure);
            moveLandingFigureInEmptySpace();
        }
    }

    public void drop() {
        Figure.copy(landingFigure, curFigure);
        handleFigureLanding();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(new Color(255, 255, 255, 30));
        for (int i = 0; i <= 20; ++i) {
            if (i <= 10) {
                g.drawLine(i * squareSize, 0, i * squareSize, getHeight());
            }
            g.drawLine(0, i * squareSize, getWidth(), i * squareSize);
        }

        for (int i = 0; i < fieldHeight; ++i) {
            for (int j = 0; j < fieldWidth; ++j) {
                if (spaces[i][j] != 0) {
                    GamePainter.paintSquare(g, j, i, spaces[i][j]);
                }
            }
        }

        if (timer.isRunning()) {
            GamePainter.paintCurFigure(g, curFigure, 0, 0);
            GamePainter.paintLandingFigure(g, landingFigure, curFigure.getLeftSquare(), curFigure.getRightSquare());
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
