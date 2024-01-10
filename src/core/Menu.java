package core;

import dto.ApplicationData;
import utils.ObjectCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Menu extends JPanel {
    private MenuBackgroundPanel mainPanel;
    private JButton start;
    private JButton records;
    private JButton exit;
    private JButton settings;
    private final List<ActionListener> actionListeners;
    private final Color buttonColor;

    {
        // to remove empty space in the top
        setBorder(BorderFactory.createEmptyBorder(-5, 0, 0, 0));

        actionListeners = new ArrayList<>();
        buttonColor = new Color(0xFFE3C755, true);

        createMainPanel();
        this.add(mainPanel);
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    private void createMainPanel() {
        createStartButton();
        createRecordsButton();
        createSettingsButton();
        createExitButton();

        mainPanel = new MenuBackgroundPanel();
        mainPanel.add(start, 1, 1);
        mainPanel.add(records, 1, 2);
        mainPanel.add(settings, 1, 3);
        mainPanel.add(exit, 1, 4);
        mainPanel.setPreferredSize(ApplicationData.getApplicationDimension());
    }

    private void createStartButton() {
        start = ObjectCreator.createButton("Start", buttonColor, 4, 26);
        start.setPreferredSize(new Dimension(150, 50));
        start.addActionListener(e -> {
            ActionEvent actionEvent = new ActionEvent(this, 1, "start game");
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
        });
    }

    private void createRecordsButton() {
        records = ObjectCreator.createButton("Records", buttonColor, 4, 26);
        records.setPreferredSize(new Dimension(150, 50));
        records.addActionListener(e -> {
            //TODO: Create Records
        });
    }

    private void createSettingsButton() {
        settings = ObjectCreator.createButton("Settings", buttonColor, 4, 26);
        settings.setPreferredSize(new Dimension(150, 50));
        settings.addActionListener(e -> {
            ActionEvent actionEvent = new ActionEvent(this, 1, "open settings");
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
        });
    }

    private void createExitButton() {
        exit = ObjectCreator.createButton("Exit", buttonColor, 4, 26);
        exit.setPreferredSize(new Dimension(150, 50));
        exit.addActionListener(e -> System.exit(0));
    }
}
