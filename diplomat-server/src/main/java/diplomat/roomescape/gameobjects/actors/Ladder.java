package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.*;

/**
 * Created by rachelcabot on 28/09/2016.
 */
public class Ladder extends AGameObject implements IExaminable, IUsable{
    public Ladder() {
        super("ladder");
    }

    @Override
    public String Describe() {
        return "It's a ladder.";
    }

    @Override
    public boolean Use(IUsableTarget target, Player player) {
        boolean success =
            Hatch.class.isInstance(target);
        if(success){
            Hatch door = (Hatch)target;
            door.ClimbInto();
        }
        return success;
    }

    @Override
    public String GetUsageDescription(IUsableTarget target) {
        return "You place the ladder under the hatch, and then climb up and into it. ";
    }

    @Override
    public void UnUse(IUsableTarget target, Player player) {
        // eh
    }
}
