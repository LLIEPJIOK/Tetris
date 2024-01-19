package core.menu;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.*;

@Setter
@NoArgsConstructor
public class CustomButton extends JButton {
    protected boolean isPressed;
    protected boolean isHover;
    {
        isPressed = false;
        isHover = false;
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        addMouseListener(new CustomMouseAdapter(this));
    }

    protected CustomButton(String text) {
        super(text);
    }
}
