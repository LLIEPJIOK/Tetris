import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

@Getter
@Setter
public class Square {
    private final static int SQUARE_SIZE;
    private final static int DELTA;
    private int x;
    private int y;

    static {
        SQUARE_SIZE = 20;
        DELTA = 6;
    }

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveUp() {
        --y;
    }

    public void moveLeft() {
        --x;
    }

    public void moveDown() {
        ++y;
    }

    public void moveRight() {
        ++x;
    }
}
