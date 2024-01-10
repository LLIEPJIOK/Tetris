package core;

import org.jetbrains.annotations.NotNull;
import dto.ApplicationData;
import utils.ObjectCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Settings extends JPanel implements ActionListener {
    private MenuBackgroundPanel mainPanel;
    private JButton soundButton;
    private JButton controlsButton;
    private SoundPanel soundPanel;
    private final JPanel cardPanel;
    private final Color buttonColor;

    {
        // to remove empty space in the top
        setBorder(BorderFactory.createEmptyBorder(-5, 0, 0, 0));

        buttonColor = new Color(0xFFE3C755, true);

        setupMainPanel();
        setupMusicVolumePanel();

        cardPanel = new JPanel(new CardLayout());
        cardPanel.add(mainPanel, "Main");
        cardPanel.add(soundPanel, "Sound");
        this.add(cardPanel);
    }

    private void setupMainPanel() {
        mainPanel = new MenuBackgroundPanel();
        mainPanel.setPreferredSize(ApplicationData.getApplicationDimension());

        setupSoundButton();
        setupControlsButton();

        mainPanel.add(soundButton, 1, 1);
        mainPanel.add(controlsButton, 1, 2);
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

    private void setupMusicVolumePanel() {
        soundPanel = new SoundPanel();
        soundPanel.setVisible(false);
        soundPanel.addActionListener(this);
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (e.getSource() == soundButton) {
            CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
            cardLayout.show(cardPanel, "Sound");
            return;
        }
        if (e.getActionCommand().equals("show setting")) {
            CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
            cardLayout.show(cardPanel, "Main");
        }
    }
}
