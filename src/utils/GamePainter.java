package utils;

import dto.ApplicationData;
import dto.Figure;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Arrays;

public class GamePainter {
    private final static int squareSize;
    private final static int delta;

    static {
        squareSize = ApplicationData.getSquareSize();
        delta = ApplicationData.getDelta();
    }

    public static void paintSquare(@NotNull Graphics g, int x, int y) {
        g.drawRect(x * squareSize, y * squareSize, squareSize, squareSize);
        g.fillRect(x * squareSize + delta, y * squareSize + delta, squareSize - 2 * delta, squareSize -  2 * delta);
    }

    public static void paintSquare(@NotNull Graphics g, int x, int y, int dx, int dy) {
        g.drawRect(x * squareSize + dx, y * squareSize + dy, squareSize, squareSize);
        g.fillRect(x * squareSize + delta + dx, y * squareSize + delta + dy, squareSize - 2 * delta, squareSize -  2 * delta);
    }

    public static void paintFigure(Graphics g, @NotNull Figure figure, int dx, int dy) {
        Arrays.stream(figure.getSquares()).forEach(square ->
                paintSquare(g, square.getX(), square.getY(), dx, dy));
    }
}
