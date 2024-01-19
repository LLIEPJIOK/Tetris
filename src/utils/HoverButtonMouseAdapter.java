package utils;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HoverButtonMouseAdapter extends MouseAdapter {
    private final JButton button;

    public HoverButtonMouseAdapter(JButton button) {
        this.button = button;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (button.isEnabled()) {
            SoundPlayer.playHoverButtonMusic();
        }
    }
}
