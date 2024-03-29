package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Square {
    private int x;
    private int y;

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
