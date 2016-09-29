package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.IExaminable;
import diplomat.roomescape.gameobjects.IStandaloneUsable;
import diplomat.roomescape.gameobjects.IUsableTarget;

/**
 * Created by rachelcabot on 28/09/2016.
 */
public class Door extends AGameObject implements IUsableTarget, IExaminable, IStandaloneUsable {
    private boolean locked = true;

    public Door() {
        super("door");
    }

    @Override
    public String Describe() {
        if(this.locked){
            return "It's locked.";
        }
        return "It's unlocked.";
    }

    public void Unlock() {
        this.locked = false;
    }

    @Override
    public void Use(Player player) {

    }

    @Override
    public String GetUsageDescription() {
        return "You walk through the door.";
    }
}
