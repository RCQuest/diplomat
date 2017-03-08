package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.*;

/**
 * Created by rachelcabot on 08/03/2017.
 */
public class Crate extends AGameObject implements IExaminable, IBreakable, IOpenable {

    private AGameObject obscuredObject;
    private AObtainable containedObject;

    public Crate(AGameObject o) {
        super("crate");
        if(AObtainable.class.isInstance(o))
            this.containedObject =(AObtainable) o;
        else
            this.obscuredObject = o;
    }

    @Override
    public String Describe() {
        return "It's a crate. There might be something inside.";
    }

    @Override
    public boolean BreakSelf(Player player) {
        player.RemoveFromRoom(this);
    }
}
