package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.IExaminable;
import diplomat.roomescape.gameobjects.IUsable;
import diplomat.roomescape.gameobjects.IUsableTarget;

/**
 * Created by rachelcabot on 09/03/2017.
 */
public class Pail extends AGameObject implements IUsable,IExaminable {
    public Pail() {
        super("pail");
    }

    @Override
    public String Describe() {
        return "It's full of water. ";
    }

    @Override
    public boolean Use(IUsableTarget target, Player player) {
        if(Tube.class.isInstance(target)){
            ((Tube)target).AlterBaseWaterLevel(1.0,player);
            return true;
        }
        return false;
    }

    @Override
    public String GetUsageDescription(IUsableTarget target) {
        return "You pour water into the tube. The key floats to the top. ";
    }

    @Override
    public void UnUse(IUsableTarget target, Player player) {
        if(Tube.class.isInstance(target)){
            ((Tube)target).UnAlterBaseWaterLevel(1.0,player);
        }
    }
}
