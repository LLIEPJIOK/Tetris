package core.game.pause;

import core.menu.MenuButton;
import org.jetbrains.annotations.NotNull;
import utils.GamePainter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PausePanel extends JPanel implements ActionListener {
    private final MenuButton resumeButton;
    private final MenuButton settingsButton;
    private final MenuButton menuButton;
    private final List<ActionListener> actionListeners;

    {
        setSize(407, 464);
        setLayout(null);
        setOpaque(false);
        setFocusable(false);

        resumeButton = new MenuButton("Resume", 26);
        resumeButton.setBounds(128, 130, 150, 50);
        resumeButton.addActionListener(this);

        settingsButton = new MenuButton("Settings", 26);
        settingsButton.setBounds(128, 205, 150, 50);
        settingsButton.addActionListener(this);

        menuButton = new MenuButton("Menu", 26);
        menuButton.setBounds(128, 285, 150, 50);
        menuButton.addActionListener(this);

        actionListeners = new ArrayList<>();

        this.add(resumeButton);
        this.add(settingsButton);
        this.add(menuButton);
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);

        GamePainter.paintUnderFrame(g, getWidth(), getHeight(), 300, 300);

        super.paintChildren(g);
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (e.getSource() == resumeButton) {
            ActionEvent actionEvent = new ActionEvent(this, 1, "resume game");
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
            setVisible(false);
            return;
        }
        if (e.getSource() == settingsButton) {
            ActionEvent actionEvent = new ActionEvent(this, 1, "open settings");
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
            return;
        }
        if (e.getSource() == menuButton) {
            ActionEvent actionEvent = new ActionEvent(this, 1, "open menu");
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
        }
    }
}
