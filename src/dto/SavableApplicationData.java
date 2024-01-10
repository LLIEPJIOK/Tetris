package dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class SavableApplicationData implements Serializable {
    private final int menuVolume;
    private final int gameVolume;

    {
        gameVolume = 80;
        menuVolume = 80;
    }
}
