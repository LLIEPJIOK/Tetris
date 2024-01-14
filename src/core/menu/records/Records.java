package core.menu.records;

import core.menu.MenuBackgroundPanel;
import org.jetbrains.annotations.NotNull;
import config.ApplicationData;
import utils.ComponentCreator;
import utils.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Records extends JPanel implements ActionListener {
    private MenuBackgroundPanel mainPanel;
    private JButton backButton;
    private final List<RecordsItem> recordsItems;
    private final List<Integer> records;
    private final List<ActionListener> actionListeners;

    {
        // to remove empty space in the top
        setBorder(BorderFactory.createEmptyBorder(-5, 0, 0, 0));

        setupMainPanel();

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 0, 10, 0);

        recordsItems = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            recordsItems.add(new RecordsItem(i + 1, -1));
            if (i == 0) {
                GridBagConstraints constr = new GridBagConstraints();
                constr.insets = new Insets(30, 0, 10, 0);
                mainPanel.add(recordsItems.getFirst(), constr);
            } else {
                constraints.gridy = i;
                mainPanel.add(recordsItems.getLast(), constraints);
            }
        }

        records = ApplicationData.getRecords();
        actionListeners = new ArrayList<>();

        this.add(mainPanel);
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    private void setupMainPanel() {
        mainPanel = new MenuBackgroundPanel("Records");
        mainPanel.setPreferredSize(ApplicationData.getApplicationDimension());

        setupBackButton();

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 10;
        constraints.anchor = GridBagConstraints.SOUTHWEST;
        constraints.insets = new Insets(0, -110, 4, 0);
        mainPanel.add(backButton, constraints);
    }

    private void setupBackButton() {
        backButton = ComponentCreator.createButton("Back", new Color(0xFFE3C755, true), 2, 16);
        backButton.setPreferredSize(new Dimension(55, 25));
        backButton.addActionListener(this);
        backButton.removeActionListener(ApplicationData.getButtonClickPlayer());
    }

    public void setupRecords() {
        for (int i = 0; i < records.size(); ++i) {
            recordsItems.get(i).setScore(records.get(i));
        }
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
