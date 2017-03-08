package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.AObtainable;
import diplomat.roomescape.gameobjects.IExaminable;

/**
 * Created by rachelcabot on 08/03/2017.
 */
public class Stone extends AObtainable implements IExaminable {
    public Stone() {
        super("stone");
    }

    @Override
    public String GetObtainedDescription() {
        return "You pick up the stone. ";
    }

    @Override
    public String Describe() {
        return "It's just a plain old pebble. ";
    }
}
