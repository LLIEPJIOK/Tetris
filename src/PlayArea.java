import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class PlayArea extends JFrame implements KeyListener, ActionListener {
    private Field field;
    private Panel backgroundPanel;

    private JLabel score;
    private JLabel lines;
    private JLabel linesLabel;
    private JLabel scoreLabel;
    private JButton pauseButton;
    private JButton resumeButton;
    private JButton exitButton;


    PlayArea() {
        setupMainPanel();
        setupPlayPanel();
        setupBackgroundPanel();
        this.add(field);
        this.add(backgroundPanel);
    }

    private void setupMainPanel() {
        setTitle("Tetris");
        setIconImage(new ImageIcon(Objects.requireNonNull(PlayArea.class.getResource("tetris.png"))).getImage());
        setSize(335, 480);
        setLocationRelativeTo(null);
        setResizable(false);
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                field.pauseGame();
                System.exit(0);
            }
        });
    }

    private void setupPlayPanel() {
        field = new Field();
        field.setLocation(20, 20);
        field.addActionListener(this);
    }

    private void setupBackgroundPanel() {
        backgroundPanel = new Panel();
        backgroundPanel.setBackground(new Color(253, 208, 59));
        backgroundPanel.setLayout(null);
        setupPauseButton();
        setupResumeButton();
        setupExitButton();
        SetupTextScore();
        SetupTextLines();
        SetupScore();
        SetupLines();
        backgroundPanel.add(lines);
        backgroundPanel.add(score);
        backgroundPanel.add(linesLabel);
        backgroundPanel.add(scoreLabel);
        backgroundPanel.add(pauseButton);
        backgroundPanel.add(resumeButton);
        backgroundPanel.add(exitButton);
    }

    private JButton SetupButton(String name, Color color) {
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
        pauseButton = SetupButton("Pause", new Color(128, 215, 84));
        pauseButton.setBounds(235, 345, 70, 30);
        pauseButton.addActionListener(this);
    }

    private void setupResumeButton() {
        resumeButton = SetupButton("Resume", new Color(128, 215, 84));
        resumeButton.setBounds(235, 345, 70, 30);
        resumeButton.setVisible(false);
        resumeButton.addActionListener(this);
    }

    private void setupExitButton() {
        exitButton = SetupButton("Exit", new Color(253, 58, 58, 255));
        exitButton.setBounds(235, 390, 70, 30);
        exitButton.addActionListener(this);
    }

    private void SetupTextScore()
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


    private void SetupTextLines()
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

    private void SetupScore()
    {
        score = new JLabel("0");
        Font font = new Font("Arial",  Font.PLAIN, 18);
        score.setFont(font);
        score.setForeground(new Color(0, 0, 0, 200));
        score.setBounds(240,225,60,25);
    }

    private void SetupLines()
    {
        lines = new JLabel("0");
        Font font = new Font("Arial",  Font.PLAIN, 18);
        lines.setFont(font);
        lines.setForeground(new Color(0, 0, 0, 200));
        lines.setBounds(243,295,60,25);
    }

//    private void SetupTextScoreInt()
//    {
//        scoreLabel = new JLabel("Score:");
//        Font font = new Font("Arial", Font.ITALIC | Font.BOLD, 16);
//        scoreLabel.setFont(font);
//        scoreLabel.setBounds(240,150,60,20);
//    }


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
        if (e.getSource() == exitButton) {
            field.pauseGame();
            System.exit(0);
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
            System.out.println(scoreEvent.getScore());
        }
    }
}
