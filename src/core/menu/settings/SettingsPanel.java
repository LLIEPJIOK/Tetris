package core.menu.settings;

import config.ApplicationData;
import core.menu.BackButton;
import core.menu.MenuButton;
import org.jetbrains.annotations.NotNull;
import utils.GamePainter;
import utils.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SettingsPanel extends JPanel implements ActionListener {
    private MenuButton soundButton;
    private MenuButton controlsButton;
    private BackButton backButton;
    private final List<ActionListener> actionListeners;

    {
        setDoubleBuffered(true);
        setLayout(null);

        actionListeners = new ArrayList<>();

        setupSoundButton();
        setupControlsButton();
        setupBackButton();
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    private void setupSoundButton() {
        soundButton = new MenuButton("Sound", 26);
        soundButton.setBounds(128, 163, 150, 50);
        soundButton.setPreferredSize(new Dimension(150, 50));
        soundButton.addActionListener(this);
        this.add(soundButton);
    }

    private void setupControlsButton() {
        controlsButton = new MenuButton("Controls", 26);
        controlsButton.setBounds(128, 243, 150, 50);
        controlsButton.addActionListener(this);
        this.add(controlsButton);
    }

    private void setupBackButton() {
        backButton = new BackButton();
        backButton.setLocation(10, 430);
        backButton.addActionListener(this);
        backButton.removeActionListener(ApplicationData.getButtonClickPlayer());
        this.add(backButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        GamePainter.paintMenuBackground(g, getWidth(), getHeight(), this);
        GamePainter.paintTextWithShadow(g, 40, "Settings", new Color(0xbbdadf), new Color(0x00b3d4), 50);
    }


    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (e.getSource() == soundButton) {
            ActionEvent actionEvent = new ActionEvent(this, 1, "open sound");
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
            return;
        }
        if (e.getSource() == controlsButton) {
            ActionEvent actionEvent = new ActionEvent(this, 1, "open controls");
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
            return;
        }
        if (e.getSource() == backButton) {
            SoundPlayer.playPressedBackButtonMusic();

            ApplicationData.save();
            ActionEvent actionEvent = new ActionEvent(this, 1, "return");
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
        }
    }
}
