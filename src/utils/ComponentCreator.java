package utils;

import config.ApplicationData;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ComponentCreator {
    public static @NotNull JButton createButton(String text, Color color, int borderThickness, int fontSize) {
        JButton button = new JButton(text);
        Border border = new LineBorder(Color.BLACK, borderThickness, false);
        button.setBorder(border);
        button.setFont(ApplicationData.getFont(Font.BOLD, fontSize));
        button.setBackground(color);
        button.setForeground(new Color(0x003495));
        button.setFocusPainted(false);
        button.addMouseListener(new HoverButtonMouseAdapter(button));
        button.addActionListener(ApplicationData.getButtonClickPlayer());
        return button;
    }

    public static @NotNull JLabel createLabel(String text, int borderThickness, int fontSize) {
        JLabel label = new JLabel(text);
        Border border = new LineBorder(Color.BLACK, borderThickness, false);
        label.setBorder(border);
        label.setFont(ApplicationData.getFont(Font.BOLD, fontSize));
        label.setBackground(new Color(0x00b3d4));
        label.setForeground(new Color(0x003495));
        return label;
    }
}
