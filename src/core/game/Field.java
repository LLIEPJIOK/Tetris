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
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Field extends JPanel {
    private static int squareSize;
    private static int fieldWidth;
    private static int fieldHeight;
    private int[][] spaces;
    private Figure curFigure;
    private Figure landingFigure;
    @Getter
    private Figure nextFigure;
    private final Timer timer;
    private final Timer lastFigureTimer;
    private Timer stoppedTimer;
    private final Timer changeBrightnessTimer;
    private final Timer brightnessLineTimer;
    private final Timer removeLineTimer;
    private float lastFigureTransparency;
    private float lastFigureDTransparency;
    private float lastFigurePaintTimes;
    private static BufferedImage image;
    private static BufferedImage extraImage;
    private static int[] speeds;
    private static int[] neededLines;
    private int curSpeed;
    private int lines;
    private float brightness;
    private float dBrightness;
    private float brightnessLine;
    private float lineOpacity;
    private final HashSet<Integer> linesSet;
    private final List<ActionListener> actionListeners;

    {
        actionListeners = new ArrayList<>();
        timer = new Timer(500, e -> {
            moveDown();
            repaint();
        });
        lastFigureTimer = new Timer(50, e -> lastFigureTimerFunction());
        changeBrightnessTimer = new Timer(10, e -> changeSpeedTimerFunction());
        brightnessLineTimer = new Timer(5, e -> brightnessLineTimerFunction());
        removeLineTimer = new Timer(5, e -> removeLineTimerFunction());

        linesSet = new HashSet<>();
        brightnessLine = 1;
        lineOpacity = 1;

        setSize(new Dimension(fieldWidth * squareSize, fieldHeight * squareSize));
    }

    public static void load() {
        squareSize = ApplicationData.getSquareSize();
        fieldWidth = ApplicationData.getFieldWidth();
        fieldHeight = ApplicationData.getFieldHeight();
        speeds = new int[]{500, 250, 100, 50, 20};
        neededLines = new int[]{0, 40, 80, 120, 160};
        try {
            image = ImageIO.read(new File(Objects.requireNonNull(Field.class.getResource("GameBackground.jpg")).getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        extraImage = image;
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
        }
        RescaleOp rescaleOp = new RescaleOp(brightness, 0, null);
        image = rescaleOp.filter(extraImage, null);
        repaint();
    }

    private void brightnessLineTimerFunction() {
        brightnessLine += 4f;
        if (brightnessLine == 41) {
            brightnessLineTimer.stop();
            removeLineTimer.start();
        }
        repaint();
    }

    private void removeLineTimerFunction() {
        lineOpacity -= 0.2f;
        if (lineOpacity <= 0) {
            lineOpacity = 0;
            removeLineTimer.stop();
            nextFigure();
            removeFullLines();
            if (stoppedTimer != timer) {
                timer.start();
            }
            brightnessLine = 1;
            lineOpacity = 1;
        }
        repaint();
    }

    private void moveLandingFigureInEmptySpace() {
        while (isInEmptySpace(landingFigure.getSquares())) {
            landingFigure.moveDown();
        }
        landingFigure.moveUp();
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

    private void removeFullLines() {
        for (Integer i : linesSet) {
            clearLine(i);
        }
        addLines(linesSet.size());
        ScoreEvent scoreEvent = new ScoreEvent(this, linesSet.size());
        notifyListeners(scoreEvent);
        linesSet.clear();
    }

    private void findFullLines() {
        for (int i = 0; i < fieldHeight; i++) {
            for (int j = 0; j < fieldWidth; j++) {
                if (spaces[i][j] == 0) {
                    break;
                }
                if (j + 1 == fieldWidth) {
                    linesSet.add(i);
                }
            }
        }
        if (!linesSet.isEmpty()) {
            if (curSpeed < 2) {
                timer.stop();
                brightnessLineTimer.start();
            } else {
                removeFullLines();
                nextFigure();
            }
        } else {
            nextFigure();
        }
    }

    private void startLastFigureTimer() {
        lastFigureTransparency = 0f;
        lastFigureDTransparency = 0.5f;
        lastFigurePaintTimes = 6;
        lastFigureTimer.start();
    }

    private void nextFigure() {
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

    public void handleFigureLanding() {
        saveInField(curFigure.getSquares(), curFigure.getColor());
        findFullLines();
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

        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < fieldHeight; ++i) {
            if (linesSet.contains(i)) {
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, lineOpacity));
            }
            for (int j = 0; j < fieldWidth; ++j) {
                if (spaces[i][j] != 0) {
                    if (linesSet.contains(i)) {
                        GamePainter.paintSquare(g, j, i, 0, 0, squareSize, spaces[i][j], brightnessLine);
                    } else {
                        GamePainter.paintSquare(g, j, i, spaces[i][j]);
                    }
                }
            }
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        }

        if (timer.isRunning() || stoppedTimer == timer) {
            GamePainter.paintCurFigure(g, curFigure, 0, 0);
            GamePainter.paintLandingFigure(g, landingFigure, curFigure.getLeftSquare(), curFigure.getRightSquare());
        } else if (lastFigureTimer.isRunning() || stoppedTimer == lastFigureTimer) {
            GamePainter.paintLastFigure(g, curFigure, lastFigureTransparency);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
