package dto;

import java.awt.event.ActionEvent;

public class CommandEvent extends ActionEvent {
    public CommandEvent(Object source, String command) {
        super(source, 1, command);
    }
}
