package core.menu.settings.sound;

import javax.swing.*;
import java.awt.*;

public class Slider extends JSlider {
    {
        setOpaque(false);
        setBackground(new Color(0xbbdadf));
        setForeground(new Color(0x003495));
        setUI(new SliderUI(this));
        setSize(200, 20);
    }
}
