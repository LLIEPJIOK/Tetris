import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PlayArea extends JFrame {
    private final Field field;

    {
        setTitle("Tetris");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        field = new Field();
        field.setLocation(0, 0);

        this.add(field);
        this.add(new Panel());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }


}
