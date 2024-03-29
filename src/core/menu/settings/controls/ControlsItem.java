package core.menu.settings.controls;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import utils.ComponentCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class ControlsItem extends JComponent implements ActionListener {
    private final JButton button;
    private final JLabel label;
    @Getter
    private int key;
    private final List<ActionListener> actionListeners;

    {
        setLayout(null);
        setSize(new Dimension(250, 25));
        setPreferredSize(new Dimension(250, 25));

        actionListeners = new ArrayList<>();
    }

    public ControlsItem(String name, int key) {
        label = ComponentCreator.createLabel(name, 0, 18);
        label.setOpaque(true);
        label.setPreferredSize(new Dimension(50, 20));
        label.setBounds(0, 0, 130, 25);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        this.key = key;

        button = ComponentCreator.createButton(KeyEvent.getKeyText(key).toLowerCase(), new Color(0xbbdadf), 2, 18);
        button.setPreferredSize(new Dimension(50, 20));
        button.setBounds(150, 0, 100, 25);
        button.addActionListener(this);

        this.add(label);
        this.add(button);
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    public String getName() {
        return label.getText();
    }

    public void setKey(int key) {
        this.key = key;
        button.setText(KeyEvent.getKeyText(key).toLowerCase());
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (e.getSource() == button) {
            ActionEvent actionEvent = new ActionEvent(this, 1, "");
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
        }
    }
}
