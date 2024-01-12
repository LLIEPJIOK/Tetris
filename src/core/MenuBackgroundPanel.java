package core;

import dto.ApplicationData;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Objects;

public class MenuBackgroundPanel extends JPanel {
    private final Image backgroundImage;
    private final String text;
    private final Font font;
    private final int width;

    {
        setDoubleBuffered(true);
        setLayout(new GridBagLayout());

        font = new Font("Arial", Font.BOLD, 50);
    }

    public MenuBackgroundPanel(String text) {
        this.text = text;
        width = getFontMetrics(font).stringWidth(text);
        backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                ApplicationData.getMenuGifPath()))).getImage();
    }

    public void add(Component comp, int gridx, int gridy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.insets = new Insets(0, 5, 30, 5);
        super.add(comp, gbc);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        Graphics2D g2d = (Graphics2D) g;
        AffineTransform oldTransform = g2d.getTransform();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.translate((ApplicationData.getApplicationDimension().width - width) / 2, 40);
        g2d.rotate(-Math.PI / 20);
        g2d.setFont(font);
        g2d.setColor(new Color(82, 13, 48));
        g2d.drawString(text, 3, 33);
        g2d.setColor(new Color(206, 27, 92));
        g2d.drawString(text, 0, 30);
        g2d.setTransform(oldTransform);
    }
}
