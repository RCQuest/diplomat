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
    public void Use(IUsableTarget target, Player player) {
        if(Door.class.isInstance(target)&&isObtained()&&((Door)target).IsLocked()){
            Door door = (Door)target;
            door.Unlock();
            player.Discard(this);
        }
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
    public String GetObtainedDescription() {
        return null;
    }
}
