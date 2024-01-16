package core.menu.settings;

import config.ApplicationData;
import org.jetbrains.annotations.NotNull;
import utils.ComponentCreator;
import utils.GamePainter;
import utils.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SettingsPanel extends JPanel implements ActionListener {
    private JButton soundButton;
    private JButton controlsButton;
    private JButton backButton;
    private final Color buttonColor;
    private final List<ActionListener> actionListeners;

    {
        setPreferredSize(new Dimension(407, 470));
        setDoubleBuffered(true);
        setLayout(null);

        buttonColor = new Color(0xFFE3C755, true);
        actionListeners = new ArrayList<>();

        setupSoundButton();
        setupControlsButton();
        setupBackButton();
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    private void setupSoundButton() {
        soundButton = ComponentCreator.createButton("Sound", buttonColor, 4, 26);
        soundButton.setBounds(135, 163, 150, 50);
        soundButton.setPreferredSize(new Dimension(150, 50));
        soundButton.addActionListener(this);
        this.add(soundButton);
    }

    private void setupControlsButton() {
        controlsButton = ComponentCreator.createButton("Controls", buttonColor, 4, 26);
        controlsButton.setBounds(135, 243, 150, 50);
        controlsButton.addActionListener(this);
        this.add(controlsButton);
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
        GamePainter.paintMenuBackground(g, getWidth(), getHeight(), "Settings", this);
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
