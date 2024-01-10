package core;

import org.jetbrains.annotations.NotNull;
import dto.ApplicationData;
import utils.ObjectCreator;
import utils.SoundPlayer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SoundPanel extends JPanel implements ChangeListener, ActionListener {
    private MenuBackgroundPanel mainPanel;
    private Slider menuVolumeSlider;
    private JLabel menuVolumeLabel;
    private JLabel menuLabel;
    private Slider gameVolumeSlider;
    private JLabel gameVolumeLabel;
    private JLabel gameLabel;
    private JButton backButton;
    private final List<ActionListener> actionListeners;

    {
        setPreferredSize(ApplicationData.getApplicationDimension());
        // to remove empty space in the top
        setBorder(BorderFactory.createEmptyBorder(-5, 0, 0, 0));

        setupMainPanel();

        actionListeners = new ArrayList<>();

        this.add(mainPanel);
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    private void setupMainPanel() {
        mainPanel = new MenuBackgroundPanel();
        mainPanel.setPreferredSize(ApplicationData.getApplicationDimension());

        setupMenuSoundStuff();
        setupGameSoundStuff();
        setupBackButton();

        mainPanel.add(menuLabel, 0, 0);
        mainPanel.add(menuVolumeSlider, 1, 0);
        mainPanel.add(menuVolumeLabel, 2, 0);

        mainPanel.add(gameLabel, 0, 1);
        mainPanel.add(gameVolumeSlider, 1, 1);
        mainPanel.add(gameVolumeLabel, 2, 1);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.anchor = GridBagConstraints.SOUTHWEST;
        constraints.insets = new Insets(0, -10, -140, 0);

        mainPanel.add(backButton, constraints);
    }

    private void setupMenuSoundStuff() {
        menuLabel = ObjectCreator.createLabel("Menu", 0, 16);
        menuLabel.setOpaque(true);
        menuLabel.setPreferredSize(new Dimension(55, 20));
        menuLabel.setHorizontalAlignment(SwingConstants.CENTER);

        menuVolumeLabel = ObjectCreator.createLabel("", 0, 16);
        menuVolumeLabel.setLocation(325, 35);
        menuVolumeLabel.setPreferredSize(new Dimension(35, 20));
        menuVolumeLabel.setOpaque(true);
        menuVolumeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        menuVolumeSlider = new Slider();
        menuVolumeSlider.setSize(250, 20);
        menuVolumeSlider.addChangeListener(this);
        menuVolumeSlider.setValue(80);
    }

    private void setupGameSoundStuff() {
        gameLabel = ObjectCreator.createLabel("Game", 0, 16);
        gameLabel.setOpaque(true);
        gameLabel.setPreferredSize(new Dimension(55, 20));
        gameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gameVolumeLabel = ObjectCreator.createLabel("", 0, 16);
        gameVolumeLabel.setLocation(325, 35);
        gameVolumeLabel.setPreferredSize(new Dimension(35, 20));
        gameVolumeLabel.setOpaque(true);
        gameVolumeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gameVolumeSlider = new Slider();
        gameVolumeSlider.setSize(250, 20);
        gameVolumeSlider.addChangeListener(this);
        gameVolumeSlider.setValue(80);
    }

    private void setupBackButton() {
        backButton = ObjectCreator.createButton("Back", new Color(0xFFE3C755, true), 2, 16);
        backButton.setPreferredSize(new Dimension(55, 25));
        backButton.addActionListener(this);
    }

    @Override
    public void stateChanged(@NotNull ChangeEvent e) {
        if (e.getSource() == menuVolumeSlider) {
            menuVolumeLabel.setText(Integer.toString(menuVolumeSlider.getValue()));
            SoundPlayer.setMenuVolume(menuVolumeSlider.getValue());
            return;
        }
        if (e.getSource() == gameVolumeSlider) {
            gameVolumeLabel.setText(Integer.toString(gameVolumeSlider.getValue()));
            SoundPlayer.setGameVolume(gameVolumeSlider.getValue());
        }
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (e.getSource() == backButton) {
            ActionEvent actionEvent = new ActionEvent(this, 1, "show setting");
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
        }
    }
}
