package core.menu.records;

import org.jetbrains.annotations.NotNull;
import config.ApplicationData;
import utils.ComponentCreator;
import utils.GamePainter;
import utils.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Records extends JPanel implements ActionListener {
    private JButton backButton;
    private final List<RecordsItem> recordsItems;
    private final List<Integer> records;
    private final List<ActionListener> actionListeners;

    {
        // to remove empty space in the top
        setBorder(BorderFactory.createEmptyBorder(-5, 0, 0, 0));
        setLayout(null);

        setupBackButton();

        recordsItems = new ArrayList<>();
        records = ApplicationData.getRecords();
        actionListeners = new ArrayList<>();
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    private void setupBackButton() {
        backButton = ComponentCreator.createButton("Back", new Color(0xFFE3C755, true), 2, 16);
        backButton.setBounds(10, 430, 55, 25);
        backButton.addActionListener(this);
        backButton.removeActionListener(ApplicationData.getButtonClickPlayer());
        this.add(backButton);
    }

    public void setupRecords() {
//        for (int i = 0; i < records.size(); ++i) {
//            recordsItems.get(i).setScore(records.get(i));
//        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        GamePainter.paintMenuBackground(g, getWidth(), getHeight(), "Records", this);
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (e.getSource() == backButton) {
            SoundPlayer.playPressedBackButtonMusic();
            ActionEvent actionEvent = new ActionEvent(this, 1, "open menu");
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
        }
    }
}
