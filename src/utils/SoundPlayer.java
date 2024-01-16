package utils;

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
    private static Clip gameOverMusic;
    private static Clip changeSpeedMusic;
    private static Clip dropMusic;

    public static void loadMusic() {
        menuMusic = loadMusic("music/BackMusic.wav");
        gameMusic = loadMusic("music/GameMusic.wav");
        usedKeyMusic = loadMusic("music/UsedKey.wav");
        hoverButtonMusic = loadMusic("music/HoverButtonSound.wav");
        pressedButtonMusic = loadMusic("music/ButtonPressed.wav");
        pressedStartButtonMusic = loadMusic("music/StartButtonPressed.wav");
        pressedBackButtonMusic = loadMusic("music/BackButtonPressed.wav");
        gameOverMusic = loadMusic("music/GameOver.wav");
        changeSpeedMusic = loadMusic("music/ChangeSpeed.wav");
        dropMusic = loadMusic("music/DropSound.wav");
    }

    private static AudioInputStream createAudioInputStream(String fileName) throws UnsupportedAudioFileException, IOException {
        InputStream audioSrc = SoundPlayer.class.getResourceAsStream(fileName);
        return AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(audioSrc)));
    }

    private static Clip loadMusic(String fileName) {
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            clip.open(createAudioInputStream(fileName));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return clip;
    }

    private static void playMusicInLoop(Clip clip) {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    private static void playMusic(Clip clip) {
        if (clip != null && !clip.isRunning()) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public static void playMenuMusic() {
        playMusicInLoop(menuMusic);
    }

    public static void playGameMusic() {
        playMusicInLoop(gameMusic);
    }

    public static void playUsedKeyMusic() {
        playMusic(usedKeyMusic);
    }

    public static void playHoverButtonMusic() {
        playMusic(hoverButtonMusic);
    }

    public static void playPressedButtonMusic() {
        playMusic(pressedButtonMusic);
    }

    public static void playPressedStartButtonMusic() {
        playMusic(pressedStartButtonMusic);
    }

    public static void playPressedBackButtonMusic() {
        playMusic(pressedBackButtonMusic);
    }

    public static void playGameOverMusic() {
        playMusic(gameOverMusic);
    }

    public static void playChangeSpeedMusic() {
        playMusic(changeSpeedMusic);
    }

    public static void playDropMusic() {
        playMusic(dropMusic);
    }

    private static void stopMusic(Clip clip) {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public static void stopMenuMusic() {
        stopMusic(menuMusic);
    }

    public static void stopGameMusic() {
        stopMusic(gameMusic);
    }

    public static void setMenuVolume(int volume) {
        FloatControl control = (FloatControl) menuMusic.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(volume / 100f * 86f - 80f);
    }

    public static void setGameVolume(int volume) {
        float value = volume / 100f * 86f - 80f;

        FloatControl control = (FloatControl) gameMusic.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(value);

        control = (FloatControl) gameOverMusic.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(value);

        control = (FloatControl) dropMusic.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(value);
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
