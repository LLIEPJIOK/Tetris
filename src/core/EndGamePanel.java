package core;

import dto.ApplicationData;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import utils.ComponentCreator;
import utils.GamePainter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class EndGamePanel extends JPanel implements ActionListener {
    private JButton menuButton;
    private JButton newGameButton;
    @Setter
    private int score;
    @Setter
    private int lines;
    private final List<ActionListener> actionListeners;

    {
        setSize(ApplicationData.getApplicationDimension());
        setPreferredSize(ApplicationData.getApplicationDimension());
        setLayout(null);
        setOpaque(false);
        setFocusable(false);

        setupNewGameButton();
        setupMenuButton();

        actionListeners = new ArrayList<>();
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    private void setupNewGameButton() {
        newGameButton = ComponentCreator.createButton("New game", new Color(0xFFE3C755, true), 2, 16);
        newGameButton.setBounds(225, 310, 90, 25);
        newGameButton.addActionListener(this);
        this.add(newGameButton);
    }

    private void setupMenuButton() {
        menuButton = ComponentCreator.createButton("Menu", new Color(0xFFE3C755, true), 2, 16);
        menuButton.setBounds(105, 310, 90, 25);
        menuButton.addActionListener(this);
        this.add(menuButton);
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);

        GamePainter.paintUnderFrame(g, getWidth(), getHeight(), 250, 250);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Font font = new Font("Arial", Font.BOLD, 30);
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
        g2d.drawString("Game is over!", (getWidth() - getFontMetrics(font).stringWidth("Game is over!")), 50);
        super.paintChildren(g);
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (e.getSource() == newGameButton) {
            ActionEvent actionEvent = new ActionEvent(this, 1, "new game");
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
            setVisible(false);
            return;
        }
        if (e.getSource() == menuButton) {
            ActionEvent actionEvent = new ActionEvent(this, 1, "open menu");
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
            setVisible(false);
        }
    }
}
