package diplomat.roomescape.gameobjects.actors;

import diplomat.eventsystem.events.core.Event;
import diplomat.eventsystem.events.core.EventSystem;
import diplomat.eventsystem.events.core.IWithEvents;
import diplomat.roomescape.gameobjects.*;

/**
 * Created by rachelcabot on 28/09/2016.
 */
public class Door extends AGameOverInvoker implements IStandaloneUsable, IUsableTarget, IExaminable {
    private boolean locked;

    public Door() {
        super("door");
        this.locked = true;
    }

    @Override
    public String Describe() {
        if(locked){
            return "It's locked.";
        }
        return "It's unlocked.";
    }

    public void Unlock() {
        this.locked = false;
    }

    @Override
    public void Use() {
        System.out.println("Invoking callback from door...");
        if(!locked)
            this.gameOverCallback.Invoke();
    }

    @Override
    public String GetUsageDescription() {
        if(!locked)
            return "You walk through the unlocked door.";
        else
            return "You stumble face first into the locked door.";
    }

    public boolean IsLocked() {
        return locked;
    }
}
