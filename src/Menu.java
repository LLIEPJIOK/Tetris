import javax.swing.*;
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
    private final Color buttonColor;

    {
        actionListeners = new ArrayList<>();
        buttonColor = new Color(0xFFE3C755, true);

        createMainPanel();
        this.add(mainPanel);
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    private void createMainPanel() {
        createStartButton();
        createRecordsButton();
        createExitButton();

        mainPanel = new MenuBackgroundPanel();
        mainPanel.add(start, 1, 1);
        mainPanel.add(records, 1, 2);
        mainPanel.add(exit, 1, 3);
        mainPanel.setPreferredSize(new Dimension(420, 500));
    }

    private void createStartButton() {
        start = ObjectCreator.createButton("Start", buttonColor, 4, 26);
        start.setPreferredSize(new Dimension(150, 50));
        start.addActionListener(e -> {
            CommandEvent commandEvent = new CommandEvent(this, "start game");
            for (ActionListener actionListener : actionListeners) {
                actionListener.actionPerformed(commandEvent);
            }
        });
    }

    private void createRecordsButton() {
        records = ObjectCreator.createButton("Records", buttonColor, 4, 26);
        records.setPreferredSize(new Dimension(150, 50));
        records.addActionListener(e -> {
            //TODO: Create Records
        });
    }

    private void createExitButton() {
        exit = ObjectCreator.createButton("Exit", buttonColor, 4, 26);
        exit.setPreferredSize(new Dimension(150, 50));
        exit.addActionListener(e -> {
            System.exit(0);
        });
    }
}
