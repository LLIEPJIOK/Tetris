package utils;

import config.ApplicationData;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class SoundPlayer {
    private static Clip menuMusic;
    private static Clip gameMusic;
    private static Clip usedKeyMusic;
    private static Clip hoverButtonMusic;
    private static Clip pressedButtonMusic;
    private static Clip pressedStartButtonMusic;
    private static Clip pressedBackButtonMusic;

    private static AudioInputStream createAudioInputStream(String fileName) throws UnsupportedAudioFileException, IOException {
        InputStream audioSrc = SoundPlayer.class.getResourceAsStream(fileName);
        return AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(audioSrc)));
    }

    public static void loadMusic() {
        loadMenuMusic();
        loadGameMusic();
        loadUsedKeyMusic();
        loadHoverButtonMusic();
        loadPressedButtonMusic();
        loadPressedStartButtonMusic();
        loadPressedBackButtonMusic();
    }

    private static void loadMenuMusic() {
        try {
            menuMusic = AudioSystem.getClip();
            menuMusic.open(createAudioInputStream(ApplicationData.getBackgroundMusicPath()));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private static void loadGameMusic() {
        try {
            gameMusic = AudioSystem.getClip();
            gameMusic.open(createAudioInputStream(ApplicationData.getGameMusicPath()));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private static void loadUsedKeyMusic() {
        try {
            usedKeyMusic = AudioSystem.getClip();
            usedKeyMusic.open(createAudioInputStream(ApplicationData.getUsedKeyMusicPath()));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private static void loadHoverButtonMusic() {
        try {
            hoverButtonMusic = AudioSystem.getClip();
            hoverButtonMusic.open(createAudioInputStream(ApplicationData.getHoverButtonMusicPath()));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private static void loadPressedButtonMusic() {
        try {
            pressedButtonMusic = AudioSystem.getClip();
            pressedButtonMusic.open(createAudioInputStream(ApplicationData.getPressedButtonMusicPath()));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private static void loadPressedStartButtonMusic() {
        try {
            pressedStartButtonMusic = AudioSystem.getClip();
            pressedStartButtonMusic.open(createAudioInputStream(ApplicationData.getPressedStartButtonMusicPath()));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private static void loadPressedBackButtonMusic() {
        try {
            pressedBackButtonMusic = AudioSystem.getClip();
            pressedBackButtonMusic.open(createAudioInputStream(ApplicationData.getPressedBackButtonMusicPath()));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void playMenuMusic() {
        if (menuMusic != null) {
            menuMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public static void playGameMusic() {
        if (gameMusic != null) {
            gameMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public static void playUsedKeyMusic() {
        if (usedKeyMusic != null && !usedKeyMusic.isRunning()) {
            usedKeyMusic.setFramePosition(0);
            usedKeyMusic.start();
        }
    }

    public static void playHoverButtonMusic() {
        if (hoverButtonMusic != null && !hoverButtonMusic.isRunning()) {
            hoverButtonMusic.setFramePosition(0);
            hoverButtonMusic.start();
        }
    }

    public static void playPressedButtonMusic() {
        if (pressedButtonMusic != null && !pressedButtonMusic.isRunning()) {
            pressedButtonMusic.setFramePosition(0);
            pressedButtonMusic.start();
        }
    }

    public static void playPressedStartButtonMusic() {
        if (pressedStartButtonMusic != null && !pressedStartButtonMusic.isRunning()) {
            pressedStartButtonMusic.setFramePosition(0);
            pressedStartButtonMusic.start();
        }
    }

    public static void playPressedBackButtonMusic() {
        if (pressedBackButtonMusic != null && !pressedBackButtonMusic.isRunning()) {
            pressedBackButtonMusic.setFramePosition(0);
            pressedBackButtonMusic.start();
        }
    }

    public static void stopMenuMusic() {
        if (menuMusic != null && menuMusic.isRunning()) {
            menuMusic.stop();
        }
    }

    public static void stopGameMusic() {
        if (gameMusic != null && gameMusic.isRunning()) {
            gameMusic.stop();
        }
    }

    public static void setMenuVolume(int volume) {
        FloatControl control = (FloatControl) menuMusic.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(volume / 100f * 86f - 80f);
    }

    public static void setGameVolume(int volume) {
        FloatControl control = (FloatControl) gameMusic.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(volume / 100f * 86f - 80f);
    }

    public static void setEffectsVolume(int volume) {
        float value = volume / 100f * 86f - 80f;

        FloatControl control = (FloatControl) usedKeyMusic.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(value);

        control = (FloatControl) hoverButtonMusic.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(value);

        control = (FloatControl) pressedButtonMusic.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(value);

        control = (FloatControl) pressedStartButtonMusic.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(value);

        control = (FloatControl) pressedBackButtonMusic.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(value);
    }
}
