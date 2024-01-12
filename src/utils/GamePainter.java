package utils;

import dto.ApplicationData;
import dto.Figure;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class GamePainter {
    private final static int squareSize;
    private final static int delta;
    private static BufferedImage[] images;
    private static final String[] cubeColors;

    static {
        squareSize = ApplicationData.getSquareSize();
        delta = ApplicationData.getDelta();
        cubeColors = new String[]{"Green", "Red", "Yellow"};
        try {
            images = new BufferedImage[cubeColors.length];
            for (int i = 0; i < images.length; ++i) {
                images[i] = ImageIO.read(new File(Objects.requireNonNull(GamePainter.class.getResource("../Cubes/" + cubeColors[i] + ".png")).getFile()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void paintSquare(@NotNull Graphics g, int x, int y, int color) {
        g.drawImage(images[color - 1],x * squareSize, y * squareSize, squareSize, squareSize, null);
    }

    public static void paintSquare(@NotNull Graphics g, int x, int y, int dx, int dy, int color) {
        g.drawImage(images[color - 1],x * squareSize + dx, y * squareSize + dy, squareSize, squareSize, null);
    }

    public static void paintFigure(Graphics g, @NotNull Figure figure, int dx, int dy) {
        Arrays.stream(figure.getSquares()).forEach(square ->
                paintSquare(g, square.getX(), square.getY(), dx, dy, figure.getColor()));
    }
}
