import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Menu extends JPanel {
    private MenuBackgroundPanel mainPanel;
    private JButton start;
    private JButton records;
    private JButton exit;
    private final List<ActionListener> actionListeners;

    {
        createStartButton();
        createExitButton();
        createRecordsButton();
        createMainPanel();

        this.add(mainPanel);

        actionListeners = new ArrayList<>();
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    private void createMainPanel() {
        mainPanel = new MenuBackgroundPanel();
        mainPanel.add(start, 1, 1);
        mainPanel.add(records, 1, 2);
        mainPanel.add(exit, 1, 3);
        mainPanel.setPreferredSize(new Dimension(420, 500));
    }

    private JButton createButton(String name) {
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

    private void createStartButton() {
        start = createButton("Start");
        start.addActionListener(e -> {
            CommandEvent commandEvent = new CommandEvent(this, "start game");
            for (ActionListener actionListener : actionListeners) {
                actionListener.actionPerformed(commandEvent);
            }
        });
    }

    private void createExitButton() {
        exit = createButton("Exit");
        exit.addActionListener(e -> {
            System.exit(0);
        });
    }

    private void createRecordsButton() {
        records = createButton("Records");
        exit.addActionListener(e -> {
            //TODO: Create Records
        });
    }
}
