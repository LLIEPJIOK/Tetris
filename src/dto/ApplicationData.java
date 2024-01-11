package dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.awt.*;
import java.io.File;
import java.io.IOException;

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
    private final static int delta;
    @Getter
    private final static String backgroundMusicPath;
    @Getter
    private final static String gameMusicPath;
    @Getter
    private final static String tetrisIconPath;
    @Getter
    private final static String menuGifPath;
    private static final ObjectMapper objectMapper;
    private static final File file;
    private static SavableApplicationData savableData;

    static {
        applicationDimension = new Dimension(420, 500);
        squareSize = 20;
        delta = 6;
        fieldWidth = 10;
        fieldHeight = 20;
        timerDuration = 500;
        backgroundMusicPath = "../BackMusic.wav";
        gameMusicPath = "../GameMusic.wav";
        tetrisIconPath = "../tetris.png";
        menuGifPath = "../MenuGif.gif";
        objectMapper = new ObjectMapper();
        file = new File("src/settings/settings.json");

        load();
    }

    public static int getMenuVolume() {
        return savableData.getMenuVolume();
    }

    public static int getGameVolume() {
        return savableData.getGameVolume();
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
