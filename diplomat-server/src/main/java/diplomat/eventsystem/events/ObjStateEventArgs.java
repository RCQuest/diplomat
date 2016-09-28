package diplomat.eventsystem.events;

import diplomat.eventsystem.events.core.EventArgs;
import diplomat.eventsystem.main.ObjState;

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
