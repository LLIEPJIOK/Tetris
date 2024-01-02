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
    private final static Random rnd;
    private Square[] squares;
    private int type;

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
        rnd = new Random();
    }

    {
        squares = new Square[4];
        generateFigure();
    }

    public void generateFigure() {
        type = rnd.nextInt(7);
        for (int i = 0; i < 4; ++i) {
            int x = (figures[type][i] & 1) + WIDTH / 2 - 1;
            int y = figures[type][i] / 2;
            squares[i] = new Square(x, y);
        }
    }

    private Square @NotNull [] squareArrayCopy(Square @NotNull [] squares) {
        Square[] copy = new Square[squares.length];
        for (int i = 0; i < squares.length; ++i) {
            copy[i] = new Square(squares[i].getX(), squares[i].getY());
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

    private void shiftI(Square[] squares) {
        if (squares[1].getX() > squares[0].getX()) {
            Arrays.stream(squares).forEach(Square::moveUp);
        } else if (squares[1].getX() < squares[0].getX()) {
            Arrays.stream(squares).forEach(Square::moveDown);
        } else if (squares[1].getY() > squares[0].getY()) {
            Arrays.stream(squares).forEach(Square::moveRight);
        } else {
            Arrays.stream(squares).forEach(Square::moveLeft);
        }
    }

    public Square[] rotateRight() {
        // no need to rotate O
        if (type == 2) {
            return squares;
        }
        Square[] rotatedSquares = squareArrayCopy(squares);
        for (int i = 0; i < 4; ++i) {
            int x = squares[i].getX() - squares[1].getX();
            int y = squares[i].getY() - squares[1].getY();
            rotatedSquares[i].setX(squares[1].getX() - y);
            rotatedSquares[i].setY(squares[1].getY() + x);
        }
        // shift I
        if (type == 1) {
            shiftI(rotatedSquares);
        }
        return rotatedSquares;
    }

    public void paint(Graphics g) {
        Arrays.stream(squares)
              .forEach(square -> Painter.paintSquare(g, square.getX(), square.getY()));
    }
}
