package eventsystem.events;

import eventsystem.events.core.EventArgs;
import eventsystem.main.ObjState;

/**
 *
 * @author nerobot
 */
public class ObjStateEventArgs extends EventArgs {
    public ObjState newState;
    public ObjStateEventArgs(ObjState state) {
        newState = state;
    }
}
