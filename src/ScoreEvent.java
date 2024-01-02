import lombok.Getter;

import java.awt.event.ActionEvent;

@Getter
public class ScoreEvent extends ActionEvent {
    private final int score;
    private final int lines;
    public ScoreEvent(Object source, int lines) {
        super(source, 1, "add");
        this.lines = lines;
        switch (lines) {
            case 1 -> score = 100;
            case 2 -> score = 300;
            case 3 -> score = 700;
            case 4 -> score = 1500;
            default -> throw new RuntimeException("illegal number of lines");
        }
    }
}
