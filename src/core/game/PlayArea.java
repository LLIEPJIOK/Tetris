package core.game;

import core.game.end.EndGamePanel;
import core.game.pause.PauseButton;
import core.game.pause.PausePanel;
import config.ApplicationData;
import dto.ScoreEvent;
import org.jetbrains.annotations.NotNull;
import utils.GamePainter;
import utils.ComponentCreator;
import utils.SoundPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class PlayArea extends JPanel implements KeyListener, ActionListener {
    private Field field;
    private JLabel scoreNumberLabel;
    private JLabel linesNumberLabel;
    private PauseButton pauseButton;
    private PausePanel pausePanel;
    private EndGamePanel endGamePanel;
    private static BufferedImage image;
    private static HashMap<Integer, String> keyCommands;
    private final List<ActionListener> actionListeners;
    private Timer pauseKeyTimer;
    private boolean handleKeys;

    {
        setupMainPanel();

        actionListeners = new ArrayList<>();
        pauseKeyTimer = new Timer(50, e -> pauseKeyTimer.stop());
        handleKeys = false;
    }

    public static void load() {
        try {
            image = ImageIO.read(new File(Objects.requireNonNull(PlayArea.class.getResource("PlayAreaBackground.png")).getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        keyCommands = ApplicationData.getKeysCommands();
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    private void setupMainPanel() {
        setupPauseButton();
        setupScoreLabels();
        setupLinesLabels();
        setupField();
        setupPausePanel();
        setupEngGamePanel();

        setLayout(null);
    }

    private void setupPauseButton() {
        pauseButton = new PauseButton();
        pauseButton.setLocation(3, 3);
        pauseButton.addActionListener(this);
        this.add(pauseButton);
    }

    private void setupScoreLabels() {
        JLabel scoreLabel = ComponentCreator.createLabel("Score", 0, 18);
        scoreLabel.setBounds(293, 160, 100, 20);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setForeground(new Color(0xbbdadf));

        scoreNumberLabel = ComponentCreator.createLabel("0", 0, 18);
        scoreNumberLabel.setBounds(293, 184, 100, 20);
        scoreNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreNumberLabel.setForeground(new Color(0xbbdadf));

        this.add(scoreLabel);
        this.add(scoreNumberLabel);
    }

    private void setupLinesLabels() {
        JLabel linesLabel = ComponentCreator.createLabel("Lines", 0, 18);
        linesLabel.setBounds(293, 250, 100, 20);
        linesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        linesLabel.setForeground(new Color(0xbbdadf));

        linesNumberLabel = ComponentCreator.createLabel("0", 0, 18);
        linesNumberLabel.setBounds(293, 274, 100, 20);
        linesNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        linesNumberLabel.setForeground(new Color(0xbbdadf));

        this.add(linesLabel);
        this.add(linesNumberLabel);
    }

    private void setupField() {
        field = new Field();
        field.setLocation(59, 30);
        field.setPreferredSize(new Dimension(100, 100));
        field.addActionListener(this);
        this.add(field);
    }

    private void setupPausePanel() {
        pausePanel = new PausePanel();
        pausePanel.setVisible(false);
        pausePanel.addActionListener(this);
        this.add(pausePanel);
    }

    private void setupEngGamePanel() {
        endGamePanel = new EndGamePanel();
        endGamePanel.setVisible(false);
        endGamePanel.addActionListener(this);
        this.add(endGamePanel);
    }

    public void startNewGame() {
        linesNumberLabel.setText("0");
        scoreNumberLabel.setText("0");
        field.startNewGame();
        handleKeys = true;
        pauseButton.setEnabled(true);
        pausePanel.setVisible(false);
        SoundPlayer.playGameMusic();
    }

    public void resumeGame() {
        pauseButton.setEnabled(true);
        pausePanel.setVisible(false);
        field.resumeGame();
    }

    public void returnToGame() {
        handleKeys = true;
    }

    public int getScore() {
        return Integer.parseInt(scoreNumberLabel.getText());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        GamePainter.paintNextFigure(g, field.getNextFigure(), 342, 70);
        super.paintChildren(g);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void keyPressed(@NotNull KeyEvent e) {
        if (!handleKeys || !keyCommands.containsKey(e.getKeyCode())) {
            return;
        }
        String command = keyCommands.get(e.getKeyCode());
        if (command.equals("Pause/Resume")) {
            if (!pauseKeyTimer.isRunning()) {
                if (pausePanel.isVisible()) {
                    resumeGame();
                } else {
                    pauseButton.doClick();
                }
                pauseKeyTimer.start();
            }
        } else if (field.isHandleMoves()) {
            switch (command) {
                case "Move left" -> field.moveLeft();
                case "Move down" -> field.moveDown();
                case "Move right" -> field.moveRight();
                case "Rotate left" -> field.rotateLeft();
                case "Rotate right" -> field.rotateRight();
                case "Drop" -> {
                    field.drop();
                    SoundPlayer.playDropMusic();
                }
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
            pausePanel.setVisible(true);
            field.pauseGame();
            pauseButton.setEnabled(false);
            this.setComponentZOrder(pausePanel, 0);
            return;
        }
        if (e instanceof ScoreEvent scoreEvent) {
            int curScore = Integer.parseInt(scoreNumberLabel.getText());
            scoreNumberLabel.setText(Integer.toString(curScore + scoreEvent.getScore()));
            int curLines = Integer.parseInt(linesNumberLabel.getText());
            linesNumberLabel.setText(Integer.toString(curLines + scoreEvent.getLines()));
            return;
        }
        switch (e.getActionCommand()) {
            case "open menu" -> {
                actionListeners.forEach(actionListener -> actionListener.actionPerformed(e));
                pauseButton.doClick();
                SoundPlayer.stopGameMusic();
                SoundPlayer.playMenuMusic();
            }
            case "open settings" -> {
                actionListeners.forEach(actionListener -> actionListener.actionPerformed(e));
                handleKeys = false;
            }
            case "resume game" -> resumeGame();
            case "repaint" -> repaint();
            case "end game" -> {
                pauseButton.setEnabled(false);

                endGamePanel.setScore(getScore());
                endGamePanel.setLines(Integer.parseInt(linesNumberLabel.getText()));
                endGamePanel.setVisible(true);
                this.setComponentZOrder(endGamePanel, 0);

                ApplicationData.addRecord(getScore());
            }
            case "new game" -> startNewGame();
        }
    }
}
