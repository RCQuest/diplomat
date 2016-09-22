package cli;

import eventsystem.events.core.EventArgs;

public class Command extends EventArgs{

    private String commandString;

    public String getCommandString(){
        return commandString;
    }
}
