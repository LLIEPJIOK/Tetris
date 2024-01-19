package core.menu;

import config.ApplicationData;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MenuButton extends CustomButton {

    private static BufferedImage image;
    private static BufferedImage hoveredImage;
    private static BufferedImage clickedImage;

    public MenuButton(String text, float fontSize) {
        super(text);
        Font font = ApplicationData.getFont(Font.BOLD, fontSize);
        setFont(font);
        setForeground(new Color(0x003495));
        addActionListener(ApplicationData.getButtonClickPlayer());
    }

    public static void load() {
        try {
            image = ImageIO.read(new File(Objects.requireNonNull(MenuButton.class.getResource("MenuButton.png")).getFile()));
            RescaleOp rescaleOp = new RescaleOp(1.1f, 0, null);
            hoveredImage = rescaleOp.filter(image, null);
            rescaleOp = new RescaleOp(2f, 0, null);
            clickedImage = rescaleOp.filter(image, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(@NotNull Graphics g) {
        if (isPressed) {
            g.drawImage(clickedImage, 0, 0, getWidth(), getHeight(), this);
        } else if (isHover) {
            g.drawImage(hoveredImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }

        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics fontMetrics = g.getFontMetrics();
        int textX = (getWidth() - fontMetrics.stringWidth(getText())) / 2;
        int textY = (getHeight() - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent() + getFont().getSize() / 25;
        g.setColor(getForeground());
        g.drawString(getText(), textX, textY);
    }
}
