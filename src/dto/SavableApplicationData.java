package dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SavableApplicationData implements Serializable {
    private int menuVolume;
    private int gameVolume;

    {
        gameVolume = 80;
        menuVolume = 80;
    }
}
