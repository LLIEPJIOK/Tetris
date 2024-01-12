package utils;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ObjectCreator {
    public static @NotNull JButton createButton(String text, Color color,
                                                int borderThickness, int fontSize) {
        JButton button = new JButton(text);
        Border border = new LineBorder(new Color(0, 0, 0), borderThickness, false);
        button.setBorder(border);
        Font font = new Font("Arial", Font.BOLD, fontSize);
        button.setFont(font);
        button.setBackground(color);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        return button;
    }

    public static @NotNull JLabel createLabel(String text, int borderThickness, int fontSize) {
        JLabel label = new JLabel(text);
        Border border = new LineBorder(new Color(0, 0, 0, 200), borderThickness, false);
        label.setBorder(border);
        Font font = new Font("Arial",  Font.PLAIN, fontSize);
        label.setFont(font);
        label.setBackground(new Color(255, 164, 60));
        label.setForeground(new Color(0, 0, 0, 200));
        return label;
    }
}