package core.menu;

import config.ApplicationData;
import utils.GamePainter;
import utils.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Menu extends JPanel {
    private final List<ActionListener> actionListeners;

    {
        setLayout(null);
        setDoubleBuffered(true);

        createMainPanel();

        actionListeners = new ArrayList<>();
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    private void createMainPanel() {
        createStartButton();
        createRecordsButton();
        createSettingsButton();
        createExitButton();
    }

    private void createStartButton() {
        MenuButton startButton = new MenuButton("Start", 26);
        startButton.setBounds(128, 110, 150, 50);
        startButton.addActionListener(e -> {
            SoundPlayer.playPressedStartButtonMusic();
            ActionEvent actionEvent = new ActionEvent(this, 1, "start game");
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
        });
        startButton.removeActionListener(ApplicationData.getButtonClickPlayer());
        this.add(startButton);
    }

    private void createRecordsButton() {
        MenuButton recordsButton = new MenuButton("Records", 26);
        recordsButton.setBounds(128, 180, 150, 50);
        recordsButton.addActionListener(e -> {
            ActionEvent actionEvent = new ActionEvent(this, 1, "open records");
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
        });
        this.add(recordsButton);
    }

    private void createSettingsButton() {
        MenuButton settingsButton = new MenuButton("Settings", 26);
        settingsButton.setBounds(128, 250, 150, 50);
        settingsButton.addActionListener(e -> {
            ActionEvent actionEvent = new ActionEvent(this, 1, "open settings");
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
        });
        this.add(settingsButton);
    }

    private void createExitButton() {
        MenuButton exitButton = new MenuButton("Exit", 26);
        exitButton.setBounds(128, 320, 150, 50);
        exitButton.addActionListener(e -> System.exit(0));
        this.add(exitButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        GamePainter.paintMenuBackground(g, getWidth(), getHeight(), this);
        GamePainter.paintTextWithShadow(g, 40, "Menu", new Color(0xbbdadf), new Color(0x00b3d4), 50);
    }
}
