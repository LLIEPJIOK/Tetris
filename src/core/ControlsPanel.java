package core;

import dto.ApplicationData;
import org.jetbrains.annotations.NotNull;
import utils.ComponentCreator;
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

public class ControlsPanel extends JPanel implements ActionListener, KeyListener {
    private MenuBackgroundPanel mainPanel;
    private ChangeControlPanel changeControlPanel;
    private final String[] itemNames;
    private List<ControlsItem> items;
    private JButton backButton;
    private ControlsItem item;
    private final HashMap<String, Integer> commandsKeys;
    private final HashMap<Integer, String> keysCommands;
    private final List<ActionListener> actionListeners;

    {
        setPreferredSize(ApplicationData.getApplicationDimension());
        // to remove empty space in the top
        setBorder(BorderFactory.createEmptyBorder(-5, 0, 0, 0));
        setLayout(null);

        itemNames = new String[]{"Move left", "Move right", "Move down", "Rotate right", "Rotate left",
                "Drop", "Pause/Resume"};
        commandsKeys = ApplicationData.getCommandsKeys();
        keysCommands = ApplicationData.getKeysCommands();

        setupMainPanel();
        setupChangeControlPanel();

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(ApplicationData.getApplicationDimension());
        layeredPane.setSize(ApplicationData.getApplicationDimension());

        layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(changeControlPanel, JLayeredPane.MODAL_LAYER);

        this.add(layeredPane, BorderLayout.CENTER);

        item = null;
        actionListeners = new ArrayList<>();
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    private void setupMainPanel() {
        mainPanel = new MenuBackgroundPanel("Controls");
        mainPanel.setSize(ApplicationData.getApplicationDimension());

        setupItems();
        setupBackButton();

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 0, 20, 0);
        constraints.gridx = 0;

        for (int i = 0; i < items.size(); ++i) {
            constraints.gridy = i;
            mainPanel.add(items.get(i), constraints);
        }

        constraints.gridy = items.size();
        constraints.anchor = GridBagConstraints.SOUTHWEST;
        constraints.insets = new Insets(0, -60, -43, 0);
        mainPanel.add(backButton, constraints);
    }

    public void setupChangeControlPanel() {
        changeControlPanel = new ChangeControlPanel();
        changeControlPanel.addActionListener(this);
        changeControlPanel.setVisible(false);
    }

    private void setupItems() {
        items = new ArrayList<>();
        for (int i = 0; i < itemNames.length; ++i) {
            items.add(new ControlsItem(itemNames[i], commandsKeys.get(itemNames[i])));
            items.get(i).addActionListener(this);
        }
    }

    private void setupBackButton() {
        backButton = ComponentCreator.createButton("Back", new Color(0xFFE3C755, true), 2, 16);
        backButton.setPreferredSize(new Dimension(55, 25));
        backButton.addActionListener(this);
        backButton.removeActionListener(ApplicationData.getButtonClickPlayer());
    }

    private void disableAllButtons(@NotNull Container container) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof JButton button) {
                button.setEnabled(false);
            } else if (component instanceof Container cont) {
                disableAllButtons(cont);
            }
        }
    }

    private void enableAllButtons(@NotNull Container container) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof JButton button) {
                button.setEnabled(true);
            } else if (component instanceof Container cont) {
                enableAllButtons(cont);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (changeControlPanel.isVisible()) {
            if (e.getKeyCode() != item.getKey() && keysCommands.containsKey(e.getKeyCode())) {
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
            enableAllButtons(mainPanel);
            if (!e.getActionCommand().isEmpty()) {
                int key = Integer.parseInt(e.getActionCommand());
                String command = item.getName();
                item.setKey(key);
                keysCommands.remove(commandsKeys.get(command));
                keysCommands.put(key, command);
                commandsKeys.put(command, key);
            }
        }
        if (changeControlPanel.isVisible()) {
            return;
        }
        if (e.getSource() == backButton) {
            SoundPlayer.playPressedBackButtonMusic();
            ActionEvent actionEvent = new ActionEvent(this, 1, "show setting");
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
            return;
        }
        if (e.getSource() instanceof ControlsItem controlsItem) {
            item = controlsItem;
            changeControlPanel.setKey(item.getKey());
            changeControlPanel.setName(item.getName());
            changeControlPanel.setVisible(true);
            disableAllButtons(mainPanel);
            this.setEnabled(false);
        }
    }
}
