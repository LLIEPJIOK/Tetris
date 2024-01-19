package core.menu.settings.controls;

import core.menu.MenuButton;
import org.jetbrains.annotations.NotNull;
import config.ApplicationData;
import utils.GamePainter;
import utils.ComponentCreator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class ChangeControlPanel extends JPanel implements ActionListener {
    private JLabel keyLabel;
    private MenuButton acceptButton;
    private MenuButton cancelButton;
    private int key;
    private final Font font;
    private String name;
    private int width;
    private final Font usedKeyFont;
    private final String usedButtonText;
    private final int usedButtonWidth;
    private int opacity;
    private final Timer timer;
    private final List<ActionListener> actionListeners;

    {
        setSize(407, 464);
        setLayout(null);
        setOpaque(false);
        setFocusable(false);

        setupKeyLabel();
        setupAcceptButton();
        setupCancelButton();

        font = ApplicationData.getFont(Font.BOLD, 25);
        name = "";

        usedKeyFont = ApplicationData.getFont(Font.BOLD, 14);
        usedButtonText = "This key is already in use!";
        usedButtonWidth = getFontMetrics(usedKeyFont).stringWidth(usedButtonText);

        timer = new Timer(50, e -> timerFunction());
        actionListeners = new ArrayList<>();
    }

    public void addActionListener(ActionListener actionListener) {
        actionListeners.add(actionListener);
    }

    public void setName(String name) {
        this.name = name;
        width = getFontMetrics(font).stringWidth(name);
    }

    public void setKey(int key) {
        this.key = key;
        this.keyLabel.setText(KeyEvent.getKeyText(key).toLowerCase());
    }

    private void timerFunction() {
        opacity -= 8;
        if (opacity <= 0) {
            opacity = 0;
            timer.stop();
        }
        repaint();
    }

    private void setupKeyLabel() {
        keyLabel = ComponentCreator.createLabel("", 2, 30);
        keyLabel.setBounds(103, 200, 200, 40);
        keyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        keyLabel.setOpaque(true);
        this.add(keyLabel);
    }

    private void setupAcceptButton() {
        acceptButton = new MenuButton("Accept", 16);
        acceptButton.setBounds(238, 310, 70, 25);
        acceptButton.addActionListener(this);
        this.add(acceptButton);
    }

    private void setupCancelButton() {
        cancelButton = new MenuButton("Cancel", 16);
        cancelButton.setBounds(98, 310, 70, 25);
        cancelButton.addActionListener(this);
        this.add(cancelButton);
    }

    public void usedButtonClicked() {
        opacity = 255;
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        GamePainter.paintUnderFrame(g, getWidth(), getHeight(), 300, 300);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(0xbbdadf));
        g2d.setFont(font);
        g2d.drawString(name, (getWidth() - width) / 2, 150);

        g2d.setColor(new Color(187, 218, 223, opacity));
        g2d.setFont(usedKeyFont);
        g2d.drawString(usedButtonText, (getWidth() - usedButtonWidth) / 2, 255);
    }

    @Override
    public void actionPerformed(@NotNull ActionEvent e) {
        if (e.getSource() == cancelButton) {
            ActionEvent actionEvent = new ActionEvent(this, 1, "");
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
            return;
        }
        if (e.getSource() == acceptButton) {
            ActionEvent actionEvent = new ActionEvent(this, 1, String.valueOf(key));
            actionListeners.forEach(actionListener -> actionListener.actionPerformed(actionEvent));
        }
    }
}
