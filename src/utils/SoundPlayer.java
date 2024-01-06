package utils;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class SoundPlayer {
    private static Clip backgroundMusic;
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
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(createAudioInputStream(ApplicationConstants.getBackgroundMusicPath()));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private static void loadGameMusic() {
        try {
            gameMusic = AudioSystem.getClip();
            gameMusic.open(createAudioInputStream(ApplicationConstants.getGameMusicPath()));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    public static void playMenuMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public static void playGameMusic() {
        if (gameMusic != null) {
            gameMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public static void stopMenuMusic() {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
        }
    }

    public static void stopGameMusic() {
        if (gameMusic != null && gameMusic.isRunning()) {
            gameMusic.stop();
        }
    }
}
