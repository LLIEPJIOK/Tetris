package core;

import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import dto.ApplicationData;
import utils.ObjectCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Settings extends JPanel implements ActionListener {
    private MenuBackgroundPanel mainPanel;
    private JButton soundButton;
    private JButton controlsButton;
    private JButton backButton;
    private SoundPanel soundPanel;
    private ControlsPanel controlsPanel;
    private final JPanel cardPanel;
    private final Color buttonColor;
    @Setter
    private String command;
    private final List<ActionListener> actionListeners;

    {
        // to remove empty space in the top
        setBorder(BorderFactory.createEmptyBorder(-5, 0, 0, 0));

        buttonColor = new Color(0xFFE3C755, true);
        command = "open menu";
        actionListeners = new ArrayList<>();

        setupMainPanel();
        setupSoundPanel();
        setupControlsPanel();

        cardPanel = new JPanel(new CardLayout());
        cardPanel.add(mainPanel, "Main");
        cardPanel.add(soundPanel, "Sound");
        cardPanel.add(controlsPanel, "Controls");
        this.add(cardPanel);
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    private void setupMainPanel() {
        mainPanel = new MenuBackgroundPanel("Settings");
        mainPanel.setPreferredSize(ApplicationData.getApplicationDimension());

        setupSoundButton();
        setupControlsButton();
        setupBackButton();

        mainPanel.add(soundButton, 1, 1);
        mainPanel.add(controlsButton, 1, 2);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.SOUTHWEST;
        constraints.insets = new Insets(0, -105, -120, 0);
        mainPanel.add(backButton, constraints);
    }

    private void setupSoundButton() {
        soundButton = ObjectCreator.createButton("Sound", buttonColor, 4, 26);
        soundButton.setPreferredSize(new Dimension(150, 50));
        soundButton.addActionListener(this);
    }

    private void setupControlsButton() {
        controlsButton = ObjectCreator.createButton("Controls", buttonColor, 4, 26);
        controlsButton.setPreferredSize(new Dimension(150, 50));
        controlsButton.addActionListener(this);
    }

    private void setupBackButton() {
        backButton = ObjectCreator.createButton("Back", new Color(0xFFE3C755, true), 2, 16);
        backButton.setPreferredSize(new Dimension(55, 25));
        backButton.addActionListener(this);
    }

    private void setupSoundPanel() {
        soundPanel = new SoundPanel();
        soundPanel.addActionListener(this);
    }

    private void setupControlsPanel() {
        controlsPanel = new ControlsPanel();
        controlsPanel.addActionListener(this);
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (e.getSource() == soundButton) {
            CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
            cardLayout.show(cardPanel, "Sound");
            return;
        }
        if (e.getSource() == controlsButton) {
            CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
            cardLayout.show(cardPanel, "Controls");
            return;
        }
        if (e.getSource() == backButton) {
            ApplicationData.save();
            ActionEvent actionEvent = new ActionEvent(this, 1, command);
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
            return;
        }
        if (e.getActionCommand().equals("show setting")) {
            CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
            cardLayout.show(cardPanel, "Main");
        }
    }
}
