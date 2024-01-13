package core;

import org.jetbrains.annotations.NotNull;
import dto.ApplicationData;
import utils.GamePainter;
import utils.ObjectCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PausePanel extends JPanel implements ActionListener {
    private final JButton resumeButton;
    private final JButton settingsButton;
    private final JButton menuButton;
    private final List<ActionListener> actionListeners;

    {
        setSize(ApplicationData.getApplicationDimension());
        setLayout(null);
        setOpaque(false);
        setFocusable(false);

        resumeButton = ObjectCreator.createButton("Resume", new Color(0xFFE3C755, true), 2, 30);
        resumeButton.setBounds(115, 130, 190, 50);
        resumeButton.addActionListener(this);

        settingsButton = ObjectCreator.createButton("Settings", new Color(0xFFE3C755, true), 2, 30);
        settingsButton.setBounds(115, 205, 190, 50);
        settingsButton.addActionListener(this);

        menuButton = ObjectCreator.createButton("Menu", new Color(0xFFE3C755, true), 2, 30);
        menuButton.setBounds(115, 285, 190, 50);
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

        GamePainter.paintUnderFrame(g, getWidth(), getHeight(), 250, 250);

        super.paintChildren(g);
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (e.getSource() == resumeButton) {
            ActionEvent actionEvent = new ActionEvent(this, 1, "resume game");
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
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
