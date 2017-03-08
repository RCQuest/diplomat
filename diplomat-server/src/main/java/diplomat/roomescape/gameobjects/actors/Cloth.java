package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.AObtainable;
import diplomat.roomescape.gameobjects.IExaminable;

/**
 * Created by rachelcabot on 08/03/2017.
 */
public class Cloth extends AObtainable implements IExaminable {
    private AGameObject obscuredItem;

    public Cloth(AGameObject obscuredItem){
        super("cloth");
        this.obscuredItem = obscuredItem;
    }

    @Override
    public String GetObtainedDescription() {
        return "Picking up the cloth reveals a "+obscuredItem.GetName()+".";
    }

    @Override
    public String Describe() {
        return "You can see it's covering something up.";
    }
}
