package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.*;

/**
 * Created by rachelcabot on 28/09/2016.
 */
public class Key extends AObtainable implements IExaminable, IUsable{
    public Key() {
        super("key");
    }

    @Override
    public String Describe() {
        return "It's a key.";
    }

    @Override
    public boolean Use(IUsableTarget target, Player player) {
        boolean success =
            Door.class.isInstance(target)&&isObtained()&&((Door)target).IsLocked();
        if(success){
            Door door = (Door)target;
            door.Unlock();
            player.Discard(this);
        }
        return success;
    }

    @Override
    public String GetUsageDescription(IUsableTarget target) {
        if(isObtained()){
            if(Door.class.isInstance(target)) {
                if(((Door)target).IsLocked()) {
                    return "You unlock the door with the key.";
                } else {
                    return "The door's already unlocked.";
                }
            } else {
                return "You can't use a key on that.";
            }
        } else {
            return "You need to pick the key up before you can use it.";
        }
    }

    @Override
    public void UnUse(IUsableTarget target, Player player) { // assumes a successful use
        Door door = (Door)target;
        door.Lock();
        player.Pickup(this);
    }

    @Override
    public String GetObtainedDescription() {
        if(!isObtained())
            return "You pick up the key.";
        else
            return "You already have the key.";
    }
}
