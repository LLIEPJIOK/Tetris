package core.game.pause;

import config.ApplicationData;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import utils.SoundPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Getter
public class PauseButton extends JButton {

    private static BufferedImage image;

    {
        setSize(24, 25);
        setOpaque(false);
        setFocusable(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (isEnabled()) {
                    SoundPlayer.playHoverButtonMusic();
                }
            }
        });
        addActionListener(ApplicationData.getButtonClickPlayer());

        try {
            image = ImageIO.read(new File(Objects.requireNonNull(this.getClass().getResource("PauseButton.png")).getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(@NotNull Graphics g) {
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}
