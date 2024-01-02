import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class Figure {
    private final static int WIDTH;
    private final static int HEIGHT;
    private final static int SQUARE_SIZE;
    private Square[] figure;
    private final int[][] field;

    /*
     * Template for figures:
     *
     *     |0 1|
     *     |2 3|
     *     |4 5|
     *     |6 7|
     */
    private final static int[][] figures =
            {
                    {1, 3, 5, 4}, // J
                    {0, 2, 4, 6}, // I
                    {0, 1, 2, 3}, // O
                    {0, 2, 4, 5}, // L
                    {1, 3, 2, 4}, // Z
                    {0, 2, 4, 3}, // T
                    {0, 2, 3, 5}, // S
            };

    static {
        WIDTH = 10;
        HEIGHT = 20;
        SQUARE_SIZE = 20;
    }

    {
        figure = new Square[4];
        generate();
    }

    public Figure(int[][] field) {
        this.field = field;
    }

    private void generate() {
        Random rnd = new Random();
        int type = rnd.nextInt(7);
        for (int i = 0; i < 4; ++i) {
            int x = (figures[type][i] & 1) + WIDTH / 2 - 1;
            int y = figures[type][i] / 2;
            figure[i] = new Square(x, y);
        }
    }

    private Square @NotNull [] squareArrayCopy(Square @NotNull [] arr) {
        Square[] copy = new Square[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            copy[i] = new Square(arr[i].getX(), arr[i].getY());
        }
        return copy;
    }

    private boolean isOutOfSpace(Square[] figure) {
        return Arrays.stream(figure).anyMatch(
                square -> square.getX() < 0 || square.getX() >= WIDTH || square.getY() >= HEIGHT ||
                        field[square.getY()][square.getX()] != 0);
    }

    private void saveInField() {
        Arrays.stream(figure).forEach(square -> field[square.getY()][square.getX()] = 1);
    }

    public void moveUp() {
        Arrays.stream(figure).forEach(Square::moveUp);
    }

    public void moveLeft() {
        for (Square square : figure) {
            if (square.getX() <= 0 || field[square.getY()][square.getX() - 1] != 0) {
                return;
            }
        }
        Arrays.stream(figure).forEach(Square::moveLeft);
    }


    public void moveDown() {
        for (Square square : figure) {
            if (square.getY() >= HEIGHT - 1 || field[square.getY() + 1][square.getX()] != 0) {
                saveInField();
                generate();
                return;
            }
        }
        Arrays.stream(figure).forEach(Square::moveDown);
    }

    public void moveRight() {
        for (Square square : figure) {
            if (square.getX() >= WIDTH - 1 || field[square.getY()][square.getX() + 1] != 0) {
                return;
            }
        }
        Arrays.stream(figure).forEach(Square::moveRight);
    }

    public void rotateRight() {
        Square[] rotatedFigure = squareArrayCopy(figure);
        for (int i = 0; i < 4; ++i) {
            int x = figure[i].getX() - figure[1].getX();
            int y = figure[i].getY() - figure[1].getY();
            rotatedFigure[i].setX(figure[1].getX() - y);
            rotatedFigure[i].setY(figure[1].getY() + x);
        }
        if (!isOutOfSpace(rotatedFigure)) {
            figure = rotatedFigure;
        }
    }

    public void paint(Graphics g) {
        Arrays.stream(figure)
              .forEach(square -> Painter.paintSquare(g, square.getX(), square.getY()));
    }
}
