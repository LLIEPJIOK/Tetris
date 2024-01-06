package utils;

import lombok.Getter;

import java.awt.*;

public class ApplicationConstants {
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
    }
}
