package core.menu;

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

public class Menu extends JPanel {
    private final List<ActionListener> actionListeners;
    private final Color buttonColor;

    {
        setLayout(null);
        setDoubleBuffered(true);

        actionListeners = new ArrayList<>();
        buttonColor = new Color(0x99FCFC);

        createMainPanel();
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    private void createMainPanel() {
        createStartButton();
        createRecordsButton();
        createSettingsButton();
        createAboutButton();
        createExitButton();
    }

    private void createStartButton() {
        JButton startButton = ComponentCreator.createButton("Start", buttonColor, 4, 26);
        startButton.setBounds(128, 90, 150, 50);
        startButton.addActionListener(e -> {
            SoundPlayer.playPressedStartButtonMusic();
            ActionEvent actionEvent = new ActionEvent(this, 1, "start game");
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
        });
        startButton.removeActionListener(ApplicationData.getButtonClickPlayer());
        this.add(startButton);
    }

    private void createRecordsButton() {
        JButton recordsButton = ComponentCreator.createButton("Records", buttonColor, 4, 26);
        recordsButton.setBounds(128, 160, 150, 50);
        recordsButton.addActionListener(e -> {
            ActionEvent actionEvent = new ActionEvent(this, 1, "open records");
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
        });
        this.add(recordsButton);
    }

    private void createSettingsButton() {
        JButton settingsButton = ComponentCreator.createButton("Settings", buttonColor, 4, 26);
        settingsButton.setBounds(128, 230, 150, 50);
        settingsButton.addActionListener(e -> {
            ActionEvent actionEvent = new ActionEvent(this, 1, "open settings");
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
        });
        this.add(settingsButton);
    }

    private void createAboutButton() {
        JButton aboutButton = ComponentCreator.createButton("About", buttonColor, 4, 26);
        aboutButton.setBounds(128, 300, 150, 50);
        aboutButton.addActionListener(e -> {
            // TODO : about window
        });
        this.add(aboutButton);
    }

    private void createExitButton() {
        JButton exitButton = ComponentCreator.createButton("Exit", buttonColor, 4, 26);
        exitButton.setBounds(128, 370, 150, 50);
        exitButton.addActionListener(e -> System.exit(0));
        this.add(exitButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        GamePainter.paintMenuBackground(g, getWidth(), getHeight(), this);
        GamePainter.paintTextWithShadow(g, 40, "Menu", new Color(206, 27, 92), new Color(82, 13, 48), 50);
    }
}
