package core;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Getter
public class PauseButton extends JButton {

    private int type;
    private double degrees;
    private double d;
    private Timer timer;

    {
        setSize(24, 24);
        setOpaque(false);
        setFocusable(false);

        type = 0;
        d = 1;
        degrees = 0;
        timer = new Timer(50, e -> {
            degrees += d;
            repaint();
            if (degrees >= Math.PI / 2) {
                degrees = Math.PI / 2;
                timer.stop();
            } else if (degrees <= 0) {
                degrees = 0;
                timer.stop();
            }
        });
    }

    public void changeType() {
        if (type == 0) {
            type = 1;
            d = 0.5;
            timer.start();
        } else {
            type = 0;
            d = -0.5;
            timer.start();
        }
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.translate(12, 12);
        g2d.rotate(degrees);
        g2d.fillRoundRect(-12, -9, 24, 4, 4, 4);
        g2d.fillRoundRect(-12, -1, 24, 4, 4, 4);
        g2d.fillRoundRect(-12, 7, 24, 4, 4, 4);
    }
}
