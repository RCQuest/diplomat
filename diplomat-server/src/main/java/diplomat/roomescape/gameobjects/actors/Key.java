package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.*;

/**
 * Created by rachelcabot on 28/09/2016.
 */
public class Key extends AGameObject implements IExaminable, IObtainable, IUsable{
    public Key() {
        super("key");
    }

    @Override
    public String Describe() {
        return "It's a key.";
    }

    @Override
    public void Use(IUsableTarget target, Player player) {
        if(Door.class.isInstance(target)){
            Door door = (Door)target;
            door.Unlock();
            player.Discard(this);
        } else {

        }
    }
}
