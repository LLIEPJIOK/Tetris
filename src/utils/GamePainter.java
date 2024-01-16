package utils;

import config.ApplicationData;
import dto.Figure;
import dto.Square;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class GamePainter {
    private static int squareSize;
    private static int nextFigureSquareSize;
    private static BufferedImage[] images;
    private static Color[] colors;
    private static Image menuBackgroundImage;

    public static void load() {
        squareSize = ApplicationData.getSquareSize();
        nextFigureSquareSize = ApplicationData.getNextFigureSquareSize();
        colors = new Color[]{new Color(0x059b48), new Color(0xfaaa00), new Color(0xab5ec4),
                new Color(0x114ec9), new Color(0xce5d25), new Color(0x77b8bf)};

        String[] cubeColors = new String[]{"Green", "Yellow", "Purple", "Blue", "Orange", "Turquoise", "Error"};
        try {
            images = new BufferedImage[cubeColors.length];
            for (int i = 0; i < images.length; ++i) {
                images[i] = ImageIO.read(new File(Objects.requireNonNull(GamePainter.class.getResource("cubes/" + cubeColors[i] + ".png")).getFile()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        menuBackgroundImage = new ImageIcon(Objects.requireNonNull(GamePainter.class.getResource(
                "MenuGif.gif"))).getImage();
    }

    public static void paintSquare(@NotNull Graphics g, int x, int y, int color) {
        g.drawImage(images[color - 1], x * squareSize, y * squareSize, squareSize, squareSize, null);
    }

    public static void paintSquare(@NotNull Graphics g, int x, int y, int dx, int dy, int size, int color) {
        g.drawImage(images[color - 1], x * size + dx, y * size + dy, size, size, null);
    }

    public static void paintCurFigure(Graphics g, @NotNull Figure figure, int dx, int dy) {
        Arrays.stream(figure.getSquares()).forEach(square ->
                paintSquare(g, square.getX(), square.getY(), dx, dy, squareSize, figure.getColor()));
    }

    public static void paintNextFigure(Graphics g, @NotNull Figure figure, int dx, int dy) {
        int mnY = Integer.MAX_VALUE;
        int mxY = Integer.MIN_VALUE;
        int mnX = Integer.MAX_VALUE;
        int mxX = Integer.MIN_VALUE;
        for (Square square : figure.getSquares()) {
            mnY = Math.min(mnY, square.getY());
            mxY = Math.max(mxY, square.getY());
            mnX = Math.min(mnX, square.getX());
            mxX = Math.max(mxX, square.getX());
        }
        dx -= (mxX - mnX + 1) * nextFigureSquareSize / 2;
        dy -= (mxY - mnY + 1) * nextFigureSquareSize / 2;
        for (Square square : figure.getSquares()) {
            paintSquare(g, square.getX() - mnX, square.getY(), dx, dy, nextFigureSquareSize, figure.getColor());
        }
    }

    public static void paintLastFigure(Graphics g, @NotNull Figure figure, float transparency) {
        Graphics2D graphics = (Graphics2D) g;
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
        for (Square square : figure.getSquares()) {
            graphics.drawImage(images[6], square.getX() * squareSize, square.getY() * squareSize, squareSize, squareSize, null);
        }
    }

    public static void paintLandingFigure(@NotNull Graphics g, @NotNull Figure figure, Square left, Square right) {
        Color color = colors[figure.getColor() - 1];
        Color transparentColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 70);

        g.setColor(colors[figure.getColor() - 1]);
        for (Square square : figure.getSquares()) {
            g.drawRect(square.getX() * squareSize, square.getY() * squareSize, squareSize, squareSize);
        }

        g.setColor(transparentColor);
        Square l = figure.getLeftSquare();
        Square r = figure.getRightSquare();
        g.drawLine(left.getX() * squareSize, (left.getY() + 1) * squareSize, left.getX() * squareSize, l.getY() * squareSize);
        g.drawLine((right.getX() + 1) * squareSize, (right.getY() + 1) * squareSize, (right.getX() + 1) * squareSize, r.getY() * squareSize);
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

    public static void paintMenuBackground(@NotNull Graphics g, int width, int height, String text, ImageObserver observer) {
        g.drawImage(menuBackgroundImage, 0, 0, width, height, observer);

        Font font = new Font("Arial", Font.BOLD, 50);
        int textWidth = g.getFontMetrics(font).stringWidth(text);

        Graphics2D g2d = (Graphics2D) g;
        AffineTransform oldTransform = g2d.getTransform();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.translate((ApplicationData.getApplicationDimension().width - textWidth) / 2, 40);
        g2d.rotate(-Math.PI / 20);

        g2d.setFont(font);
        g2d.setColor(new Color(82, 13, 48));
        g2d.drawString(text, 3, 33);
        g2d.setColor(new Color(206, 27, 92));
        g2d.drawString(text, 0, 30);
        g2d.setTransform(oldTransform);
    }
}
