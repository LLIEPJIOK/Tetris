package utils;

import utils.SoundPlayer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonClickPlayer implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        SoundPlayer.playPressedButtonMusic();
    }
}
