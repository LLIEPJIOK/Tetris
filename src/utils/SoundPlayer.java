package utils;

import dto.ApplicationData;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class SoundPlayer {
    private static Clip menuMusic;
    private static Clip gameMusic;

    private static AudioInputStream createAudioInputStream(String fileName) throws UnsupportedAudioFileException, IOException {
        InputStream audioSrc = SoundPlayer.class.getResourceAsStream(fileName);
        return AudioSystem.getAudioInputStream(new BufferedInputStream(Objects.requireNonNull(audioSrc)));
    }

    public static void loadMusic() {
        loadMenuMusic();
        loadGameMusic();
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
}
