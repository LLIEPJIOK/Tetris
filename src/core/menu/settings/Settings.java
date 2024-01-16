package core.menu.settings;

import core.menu.settings.controls.Controls;
import core.menu.settings.sound.SoundPanel;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Settings extends JPanel implements ActionListener {
    private SettingsPanel settingsPanel;
    private SoundPanel soundPanel;
    @Getter
    private Controls controls;
    @Setter
    private String command;
    private final List<ActionListener> actionListeners;

    {
        command = "open menu";
        actionListeners = new ArrayList<>();

        setupSettingsPanel();
        setupSoundPanel();
        setupControlsPanel();

        setLayout(new CardLayout());
        this.add(settingsPanel, "Settings");
        this.add(soundPanel, "Sound");
        this.add(controls, "Controls");
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    private void setupSettingsPanel() {
        settingsPanel = new SettingsPanel();
        settingsPanel.addActionListener(this);
    }

    private void setupSoundPanel() {
        soundPanel = new SoundPanel();
        soundPanel.addActionListener(this);
    }

    private void setupControlsPanel() {
        controls = new Controls();
        controls.addActionListener(this);
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        switch (e.getActionCommand()) {
            case "return" -> {
                ActionEvent actionEvent = new ActionEvent(this, 1, command);
                actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
            }
            case "open sound" -> {
                CardLayout cardLayout = (CardLayout) this.getLayout();
                cardLayout.show(this, "Sound");
            }
            case "open controls" -> {
                CardLayout cardLayout = (CardLayout) this.getLayout();
                cardLayout.show(this, "Controls");
            }
            case "open settings" -> {
                CardLayout cardLayout = (CardLayout) this.getLayout();
                cardLayout.show(this, "Settings");
            }
        }
    }
}
