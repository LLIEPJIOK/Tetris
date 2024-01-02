import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;

@Getter
@Setter
public class Figure {
    private final static int WIDTH;
    private final static int HEIGHT;
    private Square[] squares;

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
    }

    {
        squares = new Square[4];
        generateFigure();
    }

    public void generateFigure() {
        Random rnd = new Random();
        int type = rnd.nextInt(7);
        for (int i = 0; i < 4; ++i) {
            int x = (figures[type][i] & 1) + WIDTH / 2 - 1;
            int y = figures[type][i] / 2;
            squares[i] = new Square(x, y);
        }
    }

    private Square @NotNull [] squareArrayCopy(Square @NotNull [] arr) {
        Square[] copy = new Square[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            copy[i] = new Square(arr[i].getX(), arr[i].getY());
        }
        return copy;
    }

    public void moveUp() {
        Arrays.stream(squares).forEach(Square::moveUp);
    }

    public void moveLeft() {
        Arrays.stream(squares).forEach(Square::moveLeft);
    }


    public void moveDown() {
        Arrays.stream(squares).forEach(Square::moveDown);
    }

    public void moveRight() {
        Arrays.stream(squares).forEach(Square::moveRight);
    }

    public Square[] rotateRight() {
        Square[] rotatedSquares = squareArrayCopy(squares);
        for (int i = 0; i < 4; ++i) {
            int x = squares[i].getX() - squares[1].getX();
            int y = squares[i].getY() - squares[1].getY();
            rotatedSquares[i].setX(squares[1].getX() - y);
            rotatedSquares[i].setY(squares[1].getY() + x);
        }
        return rotatedSquares;
    }

    public void paint(Graphics g) {
        Arrays.stream(squares)
              .forEach(square -> Painter.paintSquare(g, square.getX(), square.getY()));
    }
}
