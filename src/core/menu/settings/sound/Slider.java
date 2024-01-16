package core.menu.settings.sound;

import javax.swing.*;
import java.awt.*;

public class Slider extends JSlider {
    {
        setOpaque(false);
        setBackground(new Color(180, 180, 180));
        setForeground(new Color(0xFFE3C755, true));
        setUI(new SliderUI(this));
        setSize(200, 20);
    }
}
