package core;

import dto.ApplicationData;
import org.jetbrains.annotations.NotNull;
import utils.ObjectCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class ControlsPanel extends JPanel implements ActionListener {
    private MenuBackgroundPanel mainPanel;
    private final String[] itemNames;
    private final int[] keyCodes;
    private List<ControlsItem> items;
    private JButton backButton;
    private final List<ActionListener> actionListeners;

    {
        setPreferredSize(ApplicationData.getApplicationDimension());
        // to remove empty space in the top
        setBorder(BorderFactory.createEmptyBorder(-5, 0, 0, 0));

        itemNames = new String[]{"Move left", "Move right", "Move down", "Rotate right", "Rotate left",
                "Drop", "Pause/Resume"};
        keyCodes = new int[]{KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_S, KeyEvent.VK_E, KeyEvent.VK_R,
                KeyEvent.VK_SPACE, KeyEvent.VK_ESCAPE};

        setupMainPanel();

        actionListeners = new ArrayList<>();

        this.add(mainPanel);
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    private void setupMainPanel() {
        mainPanel = new MenuBackgroundPanel("Controls");
        mainPanel.setPreferredSize(ApplicationData.getApplicationDimension());

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

    private void setupItems() {
        items = new ArrayList<>();
        for (int i = 0; i < itemNames.length; ++i) {
            items.add(new ControlsItem(itemNames[i], KeyEvent.getKeyText(keyCodes[i])));
        }
    }

    private void setupBackButton() {
        backButton = ObjectCreator.createButton("Back", new Color(0xFFE3C755, true), 2, 16);
        backButton.setPreferredSize(new Dimension(55, 25));
        backButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (e.getSource() == backButton) {
            ActionEvent actionEvent = new ActionEvent(this, 1, "show setting");
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
        }
    }
}
