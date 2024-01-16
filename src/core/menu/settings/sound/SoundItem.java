package core.menu.settings.sound;

import org.jetbrains.annotations.NotNull;
import utils.ComponentCreator;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SoundItem extends JComponent implements ChangeListener {
    private JLabel volumeLabel;
    private Slider slider;
    private final SoundSetter soundSetter;

    {
        setSize(330, 20);
    }

    public SoundItem(String text, int value, SoundSetter soundSetter) {
        this.soundSetter = soundSetter;
        setupLabel(text);
        setupVolumeLabel();
        setupSlider(value);
    }

    public int getValue() {
        return slider.getValue();
    }

    private void setupLabel(String text) {
        JLabel label = ComponentCreator.createLabel(text, 0, 16);
        label.setOpaque(true);
        label.setBounds(0, 0, 55, 20);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(label);
    }

    private void setupVolumeLabel() {
        volumeLabel = ComponentCreator.createLabel("", 0, 16);
        volumeLabel.setBounds(295, 0, 35, 20);
        volumeLabel.setOpaque(true);
        volumeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(volumeLabel);
    }

    public void setupSlider(int value) {
        slider = new Slider();
        slider.setLocation(75, 0);
        slider.addChangeListener(this);
        slider.setValue(value);
        this.add(slider);
    }

    @Override
    public void stateChanged(@NotNull ChangeEvent e) {
        if (e.getSource() == slider) {
            volumeLabel.setText(Integer.toString(slider.getValue()));
            soundSetter.setVolume(slider.getValue());
        }
    }
}
