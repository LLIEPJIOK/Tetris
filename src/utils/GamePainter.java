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
    private static BufferedImage[] images;
    private static final String[] cubeColors;

    static {
        squareSize = ApplicationData.getSquareSize();
        cubeColors = new String[]{"Green", "Red", "Yellow", "Purple", "Blue", "Orange", "Turquoise"};
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

    public static void paintUnderFrame(@NotNull Graphics g, int frameWidth, int frameHeight,
                                       int componentWidth, int componentHeight) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, frameWidth, frameHeight);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(220, 220, 220));
        g2d.fillRect((frameWidth - componentWidth) / 2, (frameHeight - componentHeight - 40) / 2,
                componentWidth, componentHeight);

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRect((frameWidth - componentWidth) / 2, (frameHeight - componentHeight - 40) / 2,
                componentWidth, componentHeight);
    }
}
