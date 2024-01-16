package utils;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class ButtonUtil {
    public static void disableAllButtons(@NotNull Container container) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof JButton button) {
                button.setEnabled(false);
            } else if (component instanceof Container cont) {
                disableAllButtons(cont);
            }
        }
    }

    public static void enableAllButtons(@NotNull Container container) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof JButton button) {
                button.setEnabled(true);
            } else if (component instanceof Container cont) {
                enableAllButtons(cont);
            }
        }
    }
}
