package core.menu.records;

import utils.ComponentCreator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RecordsItem extends JComponent {
    JLabel numberLabel;
    private JLabel scoreLabel;

    {
        setLayout(null);
        setPreferredSize(new Dimension(150, 25));
    }

    public RecordsItem(int number, int score) {
        setupNumberLabel(number);
        setupScoreLabel(score);
    }

    public void setScore(int score) {
        scoreLabel.setText(String.valueOf(score));
        numberLabel.setVisible(score >= 0);
        scoreLabel.setVisible(score >= 0);
    }

    private void setupNumberLabel(int number) {
        numberLabel = ComponentCreator.createLabel(number + ".", 0, 18);
        numberLabel.setOpaque(true);
        numberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        numberLabel.setBorder(new EmptyBorder(0, 0, 0, 5));
        numberLabel.setBounds(0, 0, 35, 25);
        this.add(numberLabel);
    }

    private void setupScoreLabel(int score) {
        scoreLabel = ComponentCreator.createLabel("", 0, 18);
        scoreLabel.setBounds(50, 0, 100, 25);
        scoreLabel.setOpaque(true);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        setScore(score);
        this.add(scoreLabel);
    }
}
