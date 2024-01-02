import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Painter {
    private final static int SQUARE_SIZE;
    private final static int DELTA;

    static {
        SQUARE_SIZE = 20;
        DELTA = 6;
    }

    public static void paintSquare(@NotNull Graphics g, int x, int y) {
        g.drawRect(x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
        g.fillRect(x * SQUARE_SIZE + DELTA, y * SQUARE_SIZE + DELTA, SQUARE_SIZE - 2 * DELTA, SQUARE_SIZE -  2 * DELTA);
    }
}
