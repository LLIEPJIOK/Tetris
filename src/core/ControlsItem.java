package core;

import org.jetbrains.annotations.NotNull;
import utils.ObjectCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlsItem extends JComponent implements ActionListener {
    private final JButton button;

    {
        setLayout(null);
        setPreferredSize(new Dimension(250, 25));
    }

    public ControlsItem(String name, String key) {
        JLabel label = ObjectCreator.createLabel(name, 0, 16);
        label.setOpaque(true);
        label.setPreferredSize(new Dimension(50, 20));
        label.setBounds(0, 0, 130, 25);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        button = ObjectCreator.createButton(key, new Color(0xFFE3C755, true), 2, 16);
        button.setPreferredSize(new Dimension(50, 20));
        button.setBounds(150, 0, 100, 25);
        button.addActionListener(this);

        this.add(label);
        this.add(button);
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (e.getSource() == button) {

        }
    }
}
