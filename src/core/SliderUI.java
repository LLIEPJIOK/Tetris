package core;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

public class SliderUI extends BasicSliderUI {
    public SliderUI(Slider slider) {
        super(slider);
    }

    @Override
    protected Dimension getThumbSize() {
        return new Dimension(14, 14);
    }

    @Override
    public void paintFocus(Graphics g) {
        // empty body
    }

    @Override
    public void paintThumb(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(slider.getForeground());
        g2d.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
    }

    @Override
    public void paintTrack(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(slider.getBackground());
        if (slider.getOrientation() == JSlider.VERTICAL) {
            g2d.fillRoundRect(slider.getWidth() / 2 - 2, 2, 4, slider.getHeight() - 4, 1, 1);
        } else {
            g2d.fillRoundRect(2, slider.getHeight() / 2 - 2, slider.getWidth() - 4, 4, 2, 2);
        }
    }
}
