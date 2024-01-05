import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
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
        setBackground(new Color(253, 208, 59));
        setLayout(null);
        setPreferredSize(new Dimension(420, 500));
        setupPauseButton();
        setupResumeButton();
        setupMenuButton();
        setupTextScore();
        setupTextLines();
        setupScore();
        setupLines();
        setupField();
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

    private JButton setupButton(String name, Color color) {
        JButton button = new JButton(name);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        Border border = new LineBorder(new Color(0, 0, 0), 2, false);
        button.setBorder(border);
        Font font = new Font("Arial", Font.BOLD, 16);
        button.setFont(font);
        button.setForeground(new Color(0, 0, 0, 255));
        button.setFocusPainted(false);
        return button;
    }

    private void setupPauseButton() {
        pauseButton = setupButton("Pause", new Color(128, 215, 84));
        pauseButton.setBounds(235, 345, 70, 30);
        pauseButton.addActionListener(this);
    }

    private void setupResumeButton() {
        resumeButton = setupButton("Resume", new Color(128, 215, 84));
        resumeButton.setBounds(235, 345, 70, 30);
        resumeButton.setVisible(false);
        resumeButton.addActionListener(this);
    }

    private void setupMenuButton() {
        menuButton = setupButton("Menu", new Color(253, 58, 58, 255));
        menuButton.setBounds(235, 390, 70, 30);
        menuButton.addActionListener(this);
    }

    private void setupTextScore()
    {
        scoreLabel = new JLabel("Score");
        Font font = new Font("Arial",  Font.PLAIN, 20);
        scoreLabel.setFont(font);
        scoreLabel.setForeground(new Color(0, 0, 0, 200));
        scoreLabel.setBounds(240,200,60,25);
        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(new Color(255, 164, 60));
        Border border = new LineBorder(new Color(0, 0, 0, 200), 2, false);
        scoreLabel.setBorder(border);
    }


    private void setupTextLines()
    {
        linesLabel = new JLabel("Lines");
        Font font = new Font("Arial",  Font.PLAIN, 20);
        linesLabel.setFont(font);
        linesLabel.setForeground(new Color(0, 0, 0, 200));
        linesLabel.setBounds(243,270,53,25);
        linesLabel.setOpaque(true);
        linesLabel.setBackground(new Color(255, 164, 60));
        Border border = new LineBorder(new Color(0, 0, 0, 200), 2, false);
        linesLabel.setBorder(border);
    }

    private void setupScore()
    {
        score = new JLabel("0");
        Font font = new Font("Arial",  Font.PLAIN, 18);
        score.setFont(font);
        score.setForeground(new Color(0, 0, 0, 200));
        score.setBounds(240,225,60,25);
    }

    private void setupLines()
    {
        lines = new JLabel("0");
        Font font = new Font("Arial",  Font.PLAIN, 18);
        lines.setFont(font);
        lines.setForeground(new Color(0, 0, 0, 200));
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
            CommandEvent commandEvent = new CommandEvent(this, "open menu");
            for (ActionListener actionListener : actionListeners) {
                actionListener.actionPerformed(commandEvent);
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
