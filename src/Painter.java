import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Painter {
    private final static int squareSize;
    private final static int delta;

    static {
        squareSize = ApplicationConstants.getSquareSize();
        delta = ApplicationConstants.getDelta();
    }

    public static void paintSquare(@NotNull Graphics g, int x, int y) {
        g.drawRect(x * squareSize, y * squareSize, squareSize, squareSize);
        g.fillRect(x * squareSize + delta, y * squareSize + delta, squareSize - 2 * delta, squareSize -  2 * delta);
    }
}
