package diplomat.cli;

import diplomat.eventsystem.events.core.EventArgs;

public class Command extends EventArgs{

    private String commandString;

    public String getCommandString(){
        return commandString;
    }
}
