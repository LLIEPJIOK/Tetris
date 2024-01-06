package core;

import utils.ApplicationConstants;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MenuBackgroundPanel extends JPanel {
    private final Image backgroundImage;

    public MenuBackgroundPanel() {
        setDoubleBuffered(true);
        backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                ApplicationConstants.getMenuGifPath()))).getImage();
        setLayout(new GridBagLayout());
    }

    public void add(Component comp, int gridx, int gridy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.insets = new Insets(10, 20, 30, 0);
        super.add(comp, gbc);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
