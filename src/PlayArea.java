import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayArea extends JFrame implements KeyListener {
    private final Field field;

    {
        setTitle("Tetris");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();

        field = new Field();
        field.setLocation(0, 0);

        this.add(field);
        this.add(new Panel());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_A -> field.moveLeft();
            case KeyEvent.VK_S -> field.moveDown();
            case KeyEvent.VK_D -> field.moveRight();
            case KeyEvent.VK_R -> field.rotateRight();
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // empty body
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // empty body
    }
}
