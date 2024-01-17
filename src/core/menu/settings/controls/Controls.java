package core.menu.settings.controls;

import config.ApplicationData;
import org.jetbrains.annotations.NotNull;
import utils.ButtonUtil;
import utils.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Controls extends JLayeredPane implements ActionListener, KeyListener {
    private ControlsPanel controlsPanel;
    private ChangeControlPanel changeControlPanel;
    private ControlsItem curItem;
    private final HashMap<Integer, String> keysCommands;
    private final HashMap<String, Integer> commandsKeys;
    private final List<ActionListener> actionListeners;

    {
        setDoubleBuffered(true);

        setupControlsPanel();
        setupChangeControlPanel();

        this.add(controlsPanel, JLayeredPane.DEFAULT_LAYER);
        this.add(changeControlPanel, JLayeredPane.MODAL_LAYER);

        keysCommands = ApplicationData.getKeysCommands();
        commandsKeys = ApplicationData.getCommandsKeys();
        actionListeners = new ArrayList<>();
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    private void setupControlsPanel() {
        controlsPanel = new ControlsPanel();
        controlsPanel.addActionListener(this);
    }

    public void setupChangeControlPanel() {
        changeControlPanel = new ChangeControlPanel();
        changeControlPanel.addActionListener(this);
        changeControlPanel.setVisible(false);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (changeControlPanel.isVisible()) {
            if (e.getKeyCode() != curItem.getKey() && keysCommands.containsKey(e.getKeyCode())) {
                SoundPlayer.playUsedKeyMusic();
                changeControlPanel.usedButtonClicked();
            } else {
                changeControlPanel.setKey(e.getKeyCode());
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // empty body
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // empty body
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (e.getSource() == changeControlPanel) {
            changeControlPanel.setVisible(false);
            ButtonUtil.enableAllButtons(controlsPanel);
            if (!e.getActionCommand().isEmpty()) {
                int key = Integer.parseInt(e.getActionCommand());
                String command = curItem.getName();
                curItem.setKey(key);
                keysCommands.remove(commandsKeys.get(command));
                keysCommands.put(key, command);
                commandsKeys.put(command, key);
            }
        }
        if (changeControlPanel.isVisible()) {
            return;
        }
        if (e.getSource() instanceof ControlsItem controlsItem) {
            curItem = controlsItem;
            changeControlPanel.setKey(curItem.getKey());
            changeControlPanel.setName(curItem.getName());
            changeControlPanel.setVisible(true);
            setComponentZOrder(changeControlPanel, 0);
            ButtonUtil.disableAllButtons(controlsPanel);
            return;
        }
        if (e.getActionCommand().equals("open settings")) {
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(e));
        }
    }
}
