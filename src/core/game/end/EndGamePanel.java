package core.game.end;

import config.ApplicationData;
import core.menu.MenuButton;
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
    private MenuButton menuButton;
    private MenuButton newGameButton;
    private JLabel scoreNumberLabel;
    private JLabel linesNumberLabel;
    private final List<ActionListener> actionListeners;

    {
        setSize(407, 464);
        setLayout(null);
        setOpaque(false);
        setFocusable(false);

        setupMenuButton();
        setupNewGameButton();
        setupScoreLabels();
        setupLinesLabels();

        actionListeners = new ArrayList<>();
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    public void setScore(int score) {
        scoreNumberLabel.setText(String.valueOf(score));
    }

    public void setLines(int lines) {
        linesNumberLabel.setText(String.valueOf(lines));
    }

    private void setupMenuButton() {
        menuButton = new MenuButton("Menu", 16);
        menuButton.setBounds(98, 310, 90, 25);
        menuButton.addActionListener(this);
        this.add(menuButton);
    }

    private void setupNewGameButton() {
        newGameButton = new MenuButton("New Game", 16);
        newGameButton.setBounds(218, 310, 90, 25);
        newGameButton.addActionListener(this);
        this.add(newGameButton);
    }

    private void setupScoreLabels() {
        JLabel scoreLabel = ComponentCreator.createLabel("Score", 0, 18);
        scoreLabel.setBounds(113, 195, 80, 20);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setOpaque(true);

        scoreNumberLabel = ComponentCreator.createLabel("0", 0, 18);
        scoreNumberLabel.setBounds(213, 195, 100, 20);
        scoreNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreNumberLabel.setForeground(new Color(0xbbdadf));

        this.add(scoreLabel);
        this.add(scoreNumberLabel);
    }

    private void setupLinesLabels() {
        JLabel linesLabel = ComponentCreator.createLabel("Lines", 0, 18);
        linesLabel.setBounds(113, 235, 80, 20);
        linesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        linesLabel.setOpaque(true);

        linesNumberLabel = ComponentCreator.createLabel("0", 0, 18);
        linesNumberLabel.setBounds(213, 235, 100, 20);
        linesNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        linesNumberLabel.setForeground(new Color(0xbbdadf));

        this.add(linesLabel);
        this.add(linesNumberLabel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        GamePainter.paintUnderFrame(g, getWidth(), getHeight(), 300, 300);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Font font = ApplicationData.getFont(Font.BOLD, 30);
        g2d.setFont(font);
        g2d.setColor(new Color(0xbbdadf));
        g2d.drawString("Game over!", (getWidth() - getFontMetrics(font).stringWidth("Game over!")) / 2, 150);
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
