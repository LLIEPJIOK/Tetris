package core;

import org.jetbrains.annotations.NotNull;
import dto.ApplicationData;
import utils.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class MainFrame extends JFrame implements ActionListener {
    private final Menu menu;
    private final PlayArea playArea;
    private final Settings settings;
    private final JPanel cardPanel;

    {
        SoundPlayer.loadMusic();

        menu = new Menu();
        menu.addActionListener(this);

        playArea = new PlayArea();
        playArea.addActionListener(this);

        settings = new Settings();
        settings.addActionListener(this);

        cardPanel = new JPanel(new CardLayout());
        cardPanel.add(menu, "Menu");
        cardPanel.add(playArea, "PlayArea");
        cardPanel.add(settings, "Settings");
        this.add(cardPanel);

        setTitle("Tetris");
        setIconImage(new ImageIcon(Objects.requireNonNull(PlayArea.class.getResource(
                ApplicationData.getTetrisIconPath()))).getImage());
        setSize(ApplicationData.getApplicationDimension());
        setLocationRelativeTo(null);
        setResizable(false);
        setFocusable(true);
        requestFocusInWindow();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        addKeyListener(playArea);
        addKeyListener(settings.getControlsPanel());
        setFocusTraversalKeysEnabled(false);

        SoundPlayer.playMenuMusic();
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        switch (e.getActionCommand()) {
            case "start game" -> {
                playArea.startNewGame();
                CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
                cardLayout.show(cardPanel, "PlayArea");
                SoundPlayer.stopMenuMusic();
                SoundPlayer.playGameMusic();
            }
            case "open menu" -> {
                CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
                cardLayout.show(cardPanel, "Menu");
                SoundPlayer.stopGameMusic();
                SoundPlayer.playMenuMusic();
            }
            case "open settings" -> {
                CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
                cardLayout.show(cardPanel, "Settings");
                if (e.getSource() == menu) {
                    settings.setCommand("open menu");
                } else {
                    settings.setCommand("return game");
                }
            }
            case "return game" -> {
                CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
                cardLayout.show(cardPanel, "PlayArea");
                playArea.returnToGame();
            }
        }
    }
}
