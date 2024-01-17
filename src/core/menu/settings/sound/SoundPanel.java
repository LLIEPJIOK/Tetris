package core.menu.settings.sound;

import org.jetbrains.annotations.NotNull;
import config.ApplicationData;
import utils.ComponentCreator;
import utils.GamePainter;
import utils.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SoundPanel extends JPanel implements ActionListener {
    private SoundItem menuSound;
    private SoundItem gameSound;
    private SoundItem effectsSound;
    private JButton backButton;
    private final List<ActionListener> actionListeners;

    {
        setDoubleBuffered(true);
        setLayout(null);

        setupMenuSound();
        setupGameSound();
        setupEffectsSound();
        setupBackButton();

        actionListeners = new ArrayList<>();
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    private void setupMenuSound() {
        menuSound = new SoundItem("Menu", ApplicationData.getMenuVolume(), SoundPlayer::setMenuVolume);
        menuSound.setLocation(38, 175);
        this.add(menuSound);
    }

    private void setupGameSound() {
        gameSound = new SoundItem("Game", ApplicationData.getGameVolume(), SoundPlayer::setGameVolume);
        gameSound.setLocation(38, 225);
        this.add(gameSound);
    }

    private void setupEffectsSound() {
        effectsSound = new SoundItem("Effects", ApplicationData.getEffectsVolume(), SoundPlayer::setEffectsVolume);
        effectsSound.setLocation(38, 275);
        this.add(effectsSound);
    }

    private void setupBackButton() {
        backButton = ComponentCreator.createButton("Back", new Color(0xFFE3C755, true), 2, 16);
        backButton.setBounds(10, 430, 55, 25);
        backButton.addActionListener(this);
        backButton.removeActionListener(ApplicationData.getButtonClickPlayer());
        this.add(backButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        GamePainter.paintMenuBackground(g, getWidth(), getHeight(), this);
        GamePainter.paintTextWithShadow(g, 40, "Sound", new Color(206, 27, 92), new Color(82, 13, 48), 50);
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (e.getSource() == backButton) {
            SoundPlayer.playPressedBackButtonMusic();

            ApplicationData.setMenuVolume(menuSound.getValue());
            ApplicationData.setGameVolume(gameSound.getValue());
            ApplicationData.setEffectsVolume(effectsSound.getValue());

            ActionEvent actionEvent = new ActionEvent(this, 1, "open settings");
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
        }
    }
}
