import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Objects;

public class Menu extends JFrame {
    private AnimatedPanel mainPanel;
    private JButton start;

    private JButton records;
    private JButton exit;




    Menu() {
        ConfigurateWindow();
        CreateStartButton();
        CreateExitButton();
        CreateRecordsButton();
        CreateMainPanel();
        SoundPlayer.loadMenuMusic("BackMusic.wav");
        SoundPlayer.loadGameMusic("GameMusic.wav");
        add(mainPanel);
        SoundPlayer.playMenuMusic();
    }

    private void CreateMainPanel() {
        mainPanel = new AnimatedPanel();
        mainPanel.add(start, 1, 1);
        mainPanel.add(records, 1, 2);
        mainPanel.add(exit, 1, 3);
    }

    private void ConfigurateWindow() {
        setTitle("Tetris");
        setIconImage(new ImageIcon(Objects.requireNonNull(PlayArea.class.getResource("tetris.png"))).getImage());
        setSize(420, 500);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private JButton CreateButton(String name) {
        JButton button = new JButton(name);
        button.setBackground(new Color(0xFFE3C755, true));
        button.setForeground(Color.WHITE);
        Border border = new LineBorder(new Color(0, 0, 0), 4, false);
        button.setBorder(border);
        Font font = new Font("Arial", Font.BOLD, 26);
        button.setFont(font);
        button.setForeground(new Color(0, 0, 0, 255));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 50));
        return button;
    }

    private void CreateStartButton() {
        start = CreateButton("Start");
        start.addActionListener(e -> {
            this.setVisible(false);
            SoundPlayer.stopMenuMusic();
            SoundPlayer.playGameMusic();
            PlayArea playArea = new PlayArea();
            playArea.setVisible(true);
        });
    }

    private void CreateExitButton() {
        exit = CreateButton("Exit");
        exit.addActionListener(e -> {
            System.exit(0);
        });
    }

    private void CreateRecordsButton() {
        records = CreateButton("Records");
        exit.addActionListener(e -> {
            //TODO: Create Records
        });
    }
}
