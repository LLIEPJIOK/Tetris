import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class PlayArea extends JFrame implements KeyListener, ActionListener {
    private Field field;
    private Panel backgroundPanel;

    private JButton pauseButton;
    private JButton resumeButton;
    private JButton exitButton;


    PlayArea() {
        SetupMainPanel();
        SetupPlayPanel();
        SetupBackgroundPanel();
        this.add(field);
        this.add(backgroundPanel);
    }

    private void SetupMainPanel() {
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

    private void SetupPlayPanel() {
        field = new Field();
        field.setLocation(20, 20);
    }

    private void SetupBackgroundPanel() {
        backgroundPanel = new Panel();
        backgroundPanel.setBackground(new Color(253, 208, 59));
        backgroundPanel.setLayout(null);
        SetupButtonPause();
        SetupButtonResume();
        SetupButtonExit();
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

    private void SetupButtonPause() {
        pauseButton = SetupButton("Pause", new Color(128, 215, 84));
        pauseButton.setBounds(235, 345, 70, 30);
        pauseButton.addActionListener(this);
    }

    private void SetupButtonResume() {
        resumeButton = SetupButton("Resume", new Color(128, 215, 84));
        resumeButton.setBounds(235, 345, 70, 30);
        resumeButton.setVisible(false);
        resumeButton.addActionListener(this);
    }

    private void SetupButtonExit() {
        exitButton = SetupButton("Exit", new Color(253, 58, 58, 255));
        exitButton.setBounds(235, 390, 70, 30);
        exitButton.addActionListener(this);
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
                case KeyEvent.VK_R -> field.rotateRight();
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
        }
    }
}
