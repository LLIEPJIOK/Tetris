package core.menu;

import utils.SoundPlayer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomMouseAdapter extends MouseAdapter {
    private final CustomButton customButton;

    public CustomMouseAdapter(CustomButton customButton) {
        this.customButton = customButton;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (customButton.isEnabled()) {
            SoundPlayer.playHoverButtonMusic();
        }
        customButton.setHover(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        customButton.setHover(false);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (customButton.isEnabled()) {
            customButton.setPressed(true);
            customButton.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        customButton.setPressed(false);
        customButton.repaint();
    }
}
