package core;

import dto.ScoreEvent;
import utils.ApplicationConstants;
import utils.ObjectCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class PlayArea extends JPanel implements KeyListener, ActionListener {
    private Field field;
    private JLabel score;
    private JLabel lines;
    private JLabel linesLabel;
    private JLabel scoreLabel;
    private JButton pauseButton;
    private JButton resumeButton;
    private JButton menuButton;
    private final List<ActionListener> actionListeners;

    {
        setupMainPanel();
        actionListeners = new ArrayList<>();
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    private void setupMainPanel() {
        setupPauseButton();
        setupResumeButton();
        setupMenuButton();
        setupTextScore();
        setupTextLines();
        setupScore();
        setupLines();
        setupField();

        setBackground(new Color(253, 208, 59));
        setLayout(null);
        setPreferredSize(ApplicationConstants.getApplicationDimension());

        this.add(lines);
        this.add(score);
        this.add(linesLabel);
        this.add(scoreLabel);
        this.add(pauseButton);
        this.add(resumeButton);
        this.add(menuButton);
        this.add(field);
    }

    private void setupField() {
        field = new Field();
        field.setLocation(20, 20);
        field.setPreferredSize(new Dimension(100, 100));
        field.addActionListener(this);
    }

    private void setupPauseButton() {
        pauseButton = ObjectCreator.createButton("Pause", new Color(128, 215, 84), 2, 16);
        pauseButton.setBounds(235, 345, 70, 30);
        pauseButton.addActionListener(this);
    }

    private void setupResumeButton() {
        resumeButton = ObjectCreator.createButton("Resume", new Color(128, 215, 84), 2, 16);
        resumeButton.setBounds(235, 345, 70, 30);
        resumeButton.setVisible(false);
        resumeButton.addActionListener(this);
    }

    private void setupMenuButton() {
        menuButton = ObjectCreator.createButton("Menu", new Color(253, 58, 58), 2, 16);
        menuButton.setBounds(235, 390, 70, 30);
        menuButton.addActionListener(this);
    }

    private void setupTextScore()
    {
        scoreLabel = ObjectCreator.createLabel("Score", 2, 20);
        scoreLabel.setOpaque(true);
        scoreLabel.setBounds(240,200,60,25);
    }


    private void setupTextLines()
    {
        linesLabel = ObjectCreator.createLabel("Lines", 2, 20);
        linesLabel.setOpaque(true);
        linesLabel.setBounds(243,270,53,25);
    }

    private void setupScore()
    {
        score = ObjectCreator.createLabel("0", 0, 18);
        score.setBounds(240,225,60,25);
    }

    private void setupLines()
    {
        lines = ObjectCreator.createLabel("0", 0, 18);
        lines.setBounds(243,295,60,25);
    }

    public void startGame() {
        field.startNewGame();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ESCAPE) {
            if (pauseButton.isVisible()) {
                pauseButton.doClick();
            } else {
                resumeButton.doClick();
            }
        } else if (pauseButton.isVisible()) {
            switch (keyCode) {
                case KeyEvent.VK_A -> field.moveLeft();
                case KeyEvent.VK_S -> field.moveDown();
                case KeyEvent.VK_D -> field.moveRight();
                case KeyEvent.VK_E -> field.rotateLeft();
                case KeyEvent.VK_R -> field.rotateRight();
                case KeyEvent.VK_SPACE -> field.fallDown();
            }
            repaint();
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
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuButton) {
            field.pauseGame();
            ActionEvent actionEvent = new ActionEvent(this, 1, "open menu");
            for (ActionListener actionListener : actionListeners) {
                actionListener.actionPerformed(actionEvent);
            }
            return;
        }
        if (e.getSource() == pauseButton) {
            field.pauseGame();
            pauseButton.setVisible(false);
            resumeButton.setVisible(true);
            return;
        }
        if (e.getSource() == resumeButton) {
            field.resumeGame();
            pauseButton.setVisible(true);
            resumeButton.setVisible(false);
            return;
        }
        if (e instanceof ScoreEvent scoreEvent) {
            int curScore = Integer.parseInt(score.getText());
            score.setText(Integer.toString(curScore + scoreEvent.getScore()));
            int curLines = Integer.parseInt(lines.getText());
            lines.setText(Integer.toString(curLines + scoreEvent.getLines()));
        }
    }
}
