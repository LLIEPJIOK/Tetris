package dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.ButtonClickPlayer;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ApplicationData {
    @Getter
    private static final Dimension applicationDimension;
    @Getter
    private final static int squareSize;
    @Getter
    private final static int fieldWidth;
    @Getter
    private final static int fieldHeight;
    @Getter
    private final static int timerDuration;
    @Getter
    private final static String backgroundMusicPath;
    @Getter
    private final static String gameMusicPath;
    @Getter
    private final static String usedKeyMusicPath;
    @Getter
    private final static String hoverButtonMusicPath;
    @Getter
    private final static String pressedButtonMusicPath;
    @Getter
    private final static String pressedStartButtonMusicPath;
    @Getter
    private final static String pressedBackButtonMusicPath;
    @Getter
    private final static String tetrisIconPath;
    @Getter
    private final static String menuGifPath;
    private static final ObjectMapper objectMapper;
    private static final File file;
    private static SavableApplicationData savableData;
    @Getter
    private static final ButtonClickPlayer buttonClickPlayer;

    static {
        applicationDimension = new Dimension(420, 500);
        squareSize = 20;
        fieldWidth = 10;
        fieldHeight = 20;
        timerDuration = 500;
        backgroundMusicPath = "../BackMusic.wav";
        gameMusicPath = "../GameMusic.wav";
        usedKeyMusicPath = "../UsedKey.wav";
        hoverButtonMusicPath = "../HoverButtonSound.wav";
        pressedButtonMusicPath = "../ButtonPressed.wav";
        pressedStartButtonMusicPath = "../StartButtonPressed.wav";
        pressedBackButtonMusicPath = "../BackButtonPressed.wav";
        tetrisIconPath = "../tetris.png";
        menuGifPath = "../MenuGif.gif";
        objectMapper = new ObjectMapper();
        file = new File("src/settings/settings.json");
        buttonClickPlayer = new ButtonClickPlayer();

        load();

        UIManager.getDefaults().put("Button.disabledText", Color.BLACK);
    }

    public static int getMenuVolume() {
        return savableData.getMenuVolume();
    }

    public static int getGameVolume() {
        return savableData.getGameVolume();
    }

    public static HashMap<Integer, String> getKeysCommands() {
        return savableData.getKeysCommands();
    }

    public static HashMap<String, Integer> getCommandsKeys() {
        return savableData.getCommandsKeys();
    }

    public static void setMenuVolume(int volume) {
        savableData.setMenuVolume(volume);
    }

    public static void setGameVolume(int volume) {
        savableData.setGameVolume(volume);
    }

    private static void load() {
        try {
            savableData = objectMapper.readValue(file, SavableApplicationData.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            objectMapper.writeValue(file, savableData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
