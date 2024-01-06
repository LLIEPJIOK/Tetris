package core;

import utils.ApplicationConstants;
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

    private final JPanel cardPanel;

    {
        menu = new Menu();
        menu.addActionListener(this);
        // to remove empty space in the top
        menu.setBorder(BorderFactory.createEmptyBorder(-5, 0, 0, 0));

        playArea = new PlayArea();
        playArea.addActionListener(this);

        cardPanel = new JPanel(new CardLayout());
        cardPanel.add(menu, "Menu");
        cardPanel.add(playArea, "PlayArea");
        this.add(cardPanel);

        setTitle("Tetris");
        setIconImage(new ImageIcon(Objects.requireNonNull(PlayArea.class.getResource(
                ApplicationConstants.getTetrisIconPath()))).getImage());
        setSize(ApplicationConstants.getApplicationDimension());
        setLocationRelativeTo(null);
        setResizable(false);
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(playArea);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        SoundPlayer.loadMusic();
        SoundPlayer.playMenuMusic();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "start game" -> {
                playArea.startGame();
                CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
                cardLayout.show(cardPanel, "PlayArea")  ;
                SoundPlayer.stopMenuMusic();
                SoundPlayer.playGameMusic();
            }
            case "open menu" -> {
                CardLayout cardLayout = (CardLayout) cardPanel.getLayout();
                cardLayout.show(cardPanel, "Menu");
                SoundPlayer.stopGameMusic();
                SoundPlayer.playMenuMusic();
            }
        }
    }
}
