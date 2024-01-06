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
    private final static String backgroundMusicName;
    @Getter
    private final static String gameMusicName;

    static {
        applicationDimension = new Dimension(420, 500);
        squareSize = 20;
        delta = 6;
        fieldWidth = 10;
        fieldHeight = 20;
        timerDuration = 500;
        backgroundMusicName = "BackMusic.wav";
        gameMusicName = "GameMusic.wav";
    }
}
