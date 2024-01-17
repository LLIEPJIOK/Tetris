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
    private final Timer animationTimer;
    private final Timer flashingTimer;
    private int curId;
    private float brightness;
    private float dBrightness;
    private int flashingTimes;

    {
        setLayout(null);

        setupBackButton();

        recordsItems = new ArrayList<>();
        records = ApplicationData.getRecords();
        actionListeners = new ArrayList<>();
        animationTimer = new Timer(10, e -> animationTimerFunction());
        flashingTimer = new Timer(20, e -> flashingTimerFunction());
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

    private void animationTimerFunction() {
        if (!recordsItems.get(curId).nextFrame()) {
            --curId;
            if (curId < 0) {
                animationTimer.stop();
                brightness = 1;
                dBrightness = 2f;
                flashingTimes = 2;
                flashingTimer.start();
            }
        }
        repaint();
    }

    private void flashingTimerFunction() {
        brightness += dBrightness;
        if (brightness >= 21) {
            brightness = 21;
            dBrightness *= -1;
            --flashingTimes;
        } else if (brightness <= 1) {
            brightness = 1;
            dBrightness *= -1;
            --flashingTimes;
            repaint();
        }
        if (flashingTimes == 0) {
            flashingTimer.stop();
        }
        for (RecordsItem recordsItem : recordsItems) {
            recordsItem.setBrightness(brightness);
        }
        repaint();
    }

    public void setupRecords() {
        for (int i = 0; i < records.size(); ++i) {
            if (i == recordsItems.size()) {
                recordsItems.add(new RecordsItem(i + 1, records.get(i)));
            } else {
                recordsItems.get(i).setScore(records.get(i));
            }
            recordsItems.get(i).setNeededY(350 - (records.size() - i) * 20);
        }

        if (!recordsItems.isEmpty()) {
            curId = recordsItems.size() - 1;
            animationTimer.start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        GamePainter.paintMenuBackground(g, getWidth(), getHeight(), this);
        GamePainter.paintTextWithShadow(g, 40, "Records", new Color(206, 27, 92), new Color(82, 13, 48), 50);
        for (RecordsItem recordsItem : recordsItems) {
            recordsItem.paint(g);
        }
        if (recordsItems.isEmpty()) {
            GamePainter.paintTextWithShadow(g, 215, "No records", new Color(0x99FCFC), new Color(0x247373), 35);
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
