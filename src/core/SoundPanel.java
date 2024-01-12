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
    private JSlider menuVolumeSlider;
    private JLabel menuVolumeLabel;
    private JLabel menuLabel;
    private JSlider gameVolumeSlider;
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
        mainPanel = new MenuBackgroundPanel("Sound");
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
        constraints.insets = new Insets(0, -25, -150, 0);
        mainPanel.add(backButton, constraints);
    }

    private @NotNull JLabel createLabel(String text) {
        JLabel label = ObjectCreator.createLabel(text, 0, 16);
        label.setOpaque(true);
        label.setPreferredSize(new Dimension(55, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private @NotNull JLabel createVolumeLabel() {
        JLabel label = ObjectCreator.createLabel("", 0, 16);
        label.setLocation(325, 35);
        label.setPreferredSize(new Dimension(35, 20));
        label.setOpaque(true);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    public JSlider createSlider() {
        JSlider slider = new JSlider();
        slider.setPreferredSize(new Dimension(200, 20));
        slider.setUI(new SliderUI(slider));
        slider.setBackground(new Color(180, 180, 180));
        slider.setForeground(new Color(0xFFE3C755, true));
        slider.setOpaque(false);
        return slider;
    }

    private void setupMenuSoundStuff() {
        menuLabel = createLabel("Menu");
        menuVolumeLabel = createVolumeLabel();
        menuVolumeSlider = createSlider();
        menuVolumeSlider.addChangeListener(this);
        menuVolumeSlider.setValue(ApplicationData.getMenuVolume());
    }

    private void setupGameSoundStuff() {
        gameLabel = createLabel("Game");
        gameVolumeLabel = createVolumeLabel();
        gameVolumeSlider = createSlider();
        gameVolumeSlider.addChangeListener(this);
        gameVolumeSlider.setValue(ApplicationData.getGameVolume());
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
            ApplicationData.setMenuVolume(menuVolumeSlider.getValue());
            ApplicationData.setGameVolume(gameVolumeSlider.getValue());

            ActionEvent actionEvent = new ActionEvent(this, 1, "show setting");
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
        }
    }
}
