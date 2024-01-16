package core.menu.settings.controls;

import config.ApplicationData;
import org.jetbrains.annotations.NotNull;
import utils.ComponentCreator;
import utils.GamePainter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ControlsPanel extends JPanel implements ActionListener {
    private final String[] itemNames;
    private JButton backButton;
    private final HashMap<String, Integer> commandsKeys;
    private final List<ActionListener> actionListeners;

    {
        setSize(new Dimension(407, 470));
        setDoubleBuffered(true);
        setLayout(null);

        itemNames = new String[]{"Move left", "Move right", "Move down", "Rotate right", "Rotate left",
                "Drop", "Pause/Resume"};
        commandsKeys = ApplicationData.getCommandsKeys();
        actionListeners = new ArrayList<>();

        setupItems();
        setupBackButton();
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    private void setupItems() {
        List<ControlsItem> items = new ArrayList<>();
        for (int i = 0; i < itemNames.length; ++i) {
            items.add(new ControlsItem(itemNames[i], commandsKeys.get(itemNames[i])));
            items.get(i).addActionListener(this);
            items.get(i).setLocation(78, 100 + i * 40);
            this.add(items.get(i));
        }
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
        GamePainter.paintMenuBackground(g, getWidth(), getHeight(), "Controls", this);
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (e.getSource() == backButton) {
            ActionEvent actionEvent = new ActionEvent(this, 1, "open settings");
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
            return;
        }
        if (e.getSource() instanceof ControlsItem) {
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(e));
        }
    }
}
