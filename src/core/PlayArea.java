package core;

import dto.ScoreEvent;
import org.jetbrains.annotations.NotNull;
import utils.GamePainter;
import utils.ObjectCreator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayArea extends JPanel implements KeyListener, ActionListener {
    private Field field;
    private JLabel score;
    private JLabel lines;
    private JLabel linesLabel;
    private JLabel scoreLabel;
    private PauseButton pauseButton;
    private PausePanel pausePanel;
    private static BufferedImage image;
    private final List<ActionListener> actionListeners;
    private final Timer pauseKeyTimer;
    private boolean handleKeys;

    {
        setupMainPanel();
        actionListeners = new ArrayList<>();
        try {
            image = ImageIO.read(new File(Objects.requireNonNull(GamePainter.class.getResource("../BetaBackground.jpg")).getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pauseKeyTimer = new Timer(50, e -> {
            Timer timer = (Timer) e.getSource();
            timer.stop();
        });
        handleKeys = false;
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    private void setupMainPanel() {
        setupPauseButton();
        setupTextScore();
        setupTextLines();
        setupScore();
        setupLines();
        setupField();
        setupPauseFrame();

        setLayout(null);
    }

    private void setupPauseButton() {
        pauseButton = new PauseButton();
        pauseButton.setLocation(3, 3);
        pauseButton.addActionListener(this);
        this.add(pauseButton);
    }

    private void setupTextScore() {
        scoreLabel = ObjectCreator.createLabel("Score", 2, 20);
        scoreLabel.setOpaque(true);
        scoreLabel.setBounds(280, 200, 60, 25);
        this.add(scoreLabel);
    }


    private void setupTextLines() {
        linesLabel = ObjectCreator.createLabel("Lines", 2, 20);
        linesLabel.setOpaque(true);
        linesLabel.setBounds(283, 270, 53, 25);
        this.add(linesLabel);
    }

    private void setupScore() {
        score = ObjectCreator.createLabel("0", 0, 18);
        score.setBounds(280, 225, 60, 25);
        this.add(score);
    }

    private void setupLines() {
        lines = ObjectCreator.createLabel("0", 0, 18);
        lines.setBounds(283, 295, 60, 25);
        this.add(lines);
    }

    private void setupField() {
        field = new Field();
        field.setLocation(60, 30);
        field.setPreferredSize(new Dimension(100, 100));
        field.addActionListener(this);
        this.add(field);
    }

    private void setupPauseFrame() {
        pausePanel = new PausePanel();
        pausePanel.setVisible(false);
        pausePanel.addActionListener(this);
        setComponentZOrder(pausePanel, 0);
        this.add(pausePanel);
    }

    public void startGame() {
        field.startNewGame();
        handleKeys = true;
    }

    public void returnToGame() {
        handleKeys = true;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        GamePainter.paintFigure(g, field.getNextFigure(), 220, 30);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void keyPressed(@NotNull KeyEvent e) {
        if (!handleKeys) {
            return;
        }
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ESCAPE) {
            if (!pauseKeyTimer.isRunning()) {
                pauseButton.doClick();
                pauseKeyTimer.start();
            }
        } else if (pauseButton.getType() == 0) {
            switch (keyCode) {
                case KeyEvent.VK_A -> field.moveLeft();
                case KeyEvent.VK_S -> field.moveDown();
                case KeyEvent.VK_D -> field.moveRight();
                case KeyEvent.VK_E -> field.rotateLeft();
                case KeyEvent.VK_R -> field.rotateRight();
                case KeyEvent.VK_SPACE -> field.drop();
            }
            field.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // empty body
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // empty body
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (e.getSource() == pauseButton) {
            pauseButton.changeType();
            pausePanel.setVisible(pauseButton.getType() == 1);
            if (pauseButton.getType() == 0) {
                field.resumeGame();
            } else {
                field.pauseGame();
            }
            setComponentZOrder(pausePanel, 0);
            setComponentZOrder(pauseButton, 0);
            return;
        }
        if (e instanceof ScoreEvent scoreEvent) {
            int curScore = Integer.parseInt(score.getText());
            score.setText(Integer.toString(curScore + scoreEvent.getScore()));
            int curLines = Integer.parseInt(lines.getText());
            lines.setText(Integer.toString(curLines + scoreEvent.getLines()));
            return;
        }
        switch (e.getActionCommand()) {
            case "open menu" -> {
                actionListeners.forEach(actionListener -> actionListener.actionPerformed(e));
                pauseButton.doClick();
            }
            case "open settings" -> {
                actionListeners.forEach(actionListener -> actionListener.actionPerformed(e));
                handleKeys = false;
            }
            case "resume game" -> pauseButton.doClick();
            case "repaint" -> repaint();
        }
    }
}
