package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.game.Field;
import core.game.PlayArea;
import core.menu.BackButton;
import core.menu.MenuButton;
import utils.ButtonClickPlayer;
import lombok.Getter;
import utils.GamePainter;
import utils.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ApplicationData {
    private static ObjectMapper objectMapper;
    private static File file;
    private static SavableApplicationData savableData;
    @Getter
    private static Dimension applicationDimension;
    @Getter
    private static int squareSize;
    @Getter
    private static int nextFigureSquareSize;
    @Getter
    private static int fieldWidth;
    @Getter
    private static int fieldHeight;
    private static Font font;
    @Getter
    private static ButtonClickPlayer buttonClickPlayer;
    @Getter
    private static List<Integer> records;

    public static int getMenuVolume() {
        return savableData.getMenuVolume();
    }

    public static int getGameVolume() {
        return savableData.getGameVolume();
    }

    public static int getEffectsVolume() {
        return savableData.getEffectsVolume();
    }

    public static HashMap<Integer, String> getKeysCommands() {
        return savableData.getKeysCommands();
    }

    public static HashMap<String, Integer> getCommandsKeys() {
        return savableData.getCommandsKeys();
    }

    public static Font getFont(int style, float size) {
        return font.deriveFont(style, size);
    }

    public static void setMenuVolume(int volume) {
        savableData.setMenuVolume(volume);
    }

    public static void setGameVolume(int volume) {
        savableData.setGameVolume(volume);
    }

    public static void setEffectsVolume(int volume) {
        savableData.setEffectsVolume(volume);
    }

    private static void findPlaceForLastRecord() {
        for (int i = records.size() - 2; i >= 0; --i) {
            if (records.get(i) >= records.get(i + 1)) {
                break;
            }
            Collections.swap(records, i, i + 1);
        }
    }

    public static void addRecord(int record) {
        if (records.size() != 10) {
            records.add(record);
        } else if (records.getLast() < record) {
            records.set(9, record);
        }
        findPlaceForLastRecord();
        save();
    }

    public static void loadGameStuff() {
        objectMapper = new ObjectMapper();
        file = new File("src/config/config.json");

        load();

        applicationDimension = new Dimension(420, 500);
        squareSize = 20;
        nextFigureSquareSize = 14;
        fieldWidth = 10;
        fieldHeight = 20;
        buttonClickPlayer = new ButtonClickPlayer();
        records = savableData.getRecords();
        try {
            File fontFile = new File("res/fonts/font.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }

        UIManager.getDefaults().put("Button.disabledText", Color.BLACK);

        SoundPlayer.loadMusic();
        GamePainter.load();
        PlayArea.load();
        Field.load();
        MenuButton.load();
        BackButton.load();
    }

    private static void load() {
        try {
            savableData = objectMapper.readValue(file, SavableApplicationData.class);
        } catch (IOException e) {
            savableData = new SavableApplicationData();
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
