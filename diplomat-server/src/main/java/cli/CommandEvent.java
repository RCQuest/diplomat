package cli;

import eventsystem.events.core.Event;
import eventsystem.events.core.EventArgs;

/**
 * Created by Rachel on 22/09/2016.
 */
public class CommandEvent<C extends EventArgs> extends Event {
    private C command;

    public CommandEvent(C command) {
        this.command = command;
    }

    public C getCommand() {
        return command;
    }
}
