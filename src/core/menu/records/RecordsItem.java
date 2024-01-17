package core.menu.records;

import config.ApplicationData;
import dto.Square;
import lombok.Setter;
import utils.GamePainter;

import java.awt.*;
import java.util.Random;

public class RecordsItem {
    private static final Random rnd;
    private static final int squareSize;
    private final int number;
    private int score;
    private final int color;
    private final Square[] squares;
    private int distance;
    private int inMove;
    @Setter
    private int neededY;
    private int scoreOpacity;
    private int scoreX;
    @Setter
    private float brightness;

    static {
        rnd = new Random();
        squareSize = ApplicationData.getSquareSize();
    }

    public RecordsItem(int number, int score) {
        this.number = number;
        this.score = score;
        this.color = (number % 6) + 1;
        this.squares = new Square[number];
        for (int i = 0; i < number; ++i) {
            squares[i] = new Square(68 + i * squareSize, -1);
        }
        reset();
    }

    public void setScore(int score) {
        this.score = score;
        reset();
    }

    private void reset() {
        for (Square square : squares) {
            square.setY(-1);
        }
        distance = 20;
        inMove = 0;
        scoreOpacity = 0;
        scoreX = number * squareSize + 158;
        brightness = 1;
    }

    public boolean nextFrame() {
        int cnt = 0;
        for (Square square : squares) {
            if (square.getY() != -1 && square.getY() != neededY) {
                square.setY(Math.min(square.getY() + 6, neededY));
                ++cnt;
            }
        }
        distance += 6;
        if (inMove != number && distance >= 20) {
            int next;
            do {
                next = rnd.nextInt(number);
            } while (squares[next].getY() != -1);
            squares[next].setY(100);
            ++inMove;
            distance = 0;
            ++cnt;
        }
        if (cnt == 0) {
            scoreOpacity += 15;
            scoreX -= 5;
            return scoreOpacity != 255;
        }
        return true;
    }

    public void paint(Graphics g) {
        for (Square square : squares) {
            if (square.getY() != -1) {
                GamePainter.paintSquare(g, 0, 0, square.getX(), square.getY(), squareSize, color, brightness);
            }
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setFont(new Font("Arial", Font.PLAIN, 16));
        g2d.setColor(new Color(255, 255, 255, scoreOpacity));
        g2d.drawString(String.valueOf(score), scoreX, neededY + 15);
    }
}
