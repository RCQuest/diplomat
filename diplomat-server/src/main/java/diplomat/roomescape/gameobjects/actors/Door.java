package diplomat.roomescape.gameobjects.actors;

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
    public boolean Use(Player player) {
        if(!locked)
            this.gameOverCallback.Invoke();
        return !locked;
    }

    @Override
    public String GetUsageDescription() {
        if(!locked)
            return "You walk through the unlocked door.";
        else
            return "You stumble face first into the locked door.";
    }

    @Override
    public void UnUse() {
        //TODO: eek!
    }

    public boolean IsLocked() {
        return locked;
    }

    public void Lock() {
        this.locked = true;
    }
}
