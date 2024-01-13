package dto;

import lombok.Getter;
import lombok.Setter;

import java.awt.event.KeyEvent;
import java.io.Serializable;
import java.util.HashMap;

@Getter
@Setter
public class SavableApplicationData implements Serializable {
    private int menuVolume;
    private int gameVolume;
    private int effectsVolume;
    private HashMap<Integer, String> keysCommands;
    private HashMap<String, Integer> commandsKeys;

    {
        gameVolume = 80;
        menuVolume = 80;
        effectsVolume = 80;

        keysCommands = new HashMap<>();
        keysCommands.put(KeyEvent.VK_A, "Move left");
        keysCommands.put(KeyEvent.VK_D, "Move right");
        keysCommands.put(KeyEvent.VK_S, "Move down");
        keysCommands.put(KeyEvent.VK_E, "Rotate left");
        keysCommands.put(KeyEvent.VK_R, "Rotate right");
        keysCommands.put(KeyEvent.VK_SPACE, "Drop");
        keysCommands.put(KeyEvent.VK_ESCAPE, "Pause/Resume");

        commandsKeys = new HashMap<>();
        commandsKeys.put("Move left", KeyEvent.VK_A);
        commandsKeys.put("Move right", KeyEvent.VK_D);
        commandsKeys.put("Move down", KeyEvent.VK_S);
        commandsKeys.put("Rotate left", KeyEvent.VK_E);
        commandsKeys.put("Rotate right", KeyEvent.VK_R);
        commandsKeys.put("Drop", KeyEvent.VK_SPACE);
        commandsKeys.put("Pause/Resume", KeyEvent.VK_ESCAPE);
    }
}
