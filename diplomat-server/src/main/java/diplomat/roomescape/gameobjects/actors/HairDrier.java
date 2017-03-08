package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.*;

/**
 * Created by rachelcabot on 08/03/2017.
 */
public class HairDrier extends AObtainable implements IExaminable, IUsable{
    public HairDrier() {
        super("hairdrier");
    }

    @Override
    public String GetObtainedDescription() {
        return "You pick up the hairdrier.";
    }

    @Override
    public String Describe() {
        return "It's a hairdrier - a fancy, portable one too. ";
    }

    @Override
    public boolean Use(IUsableTarget target, Player player) {
        boolean success = Ice.class.isInstance(target);
        if(success){
            ((Ice)target).Melt(player);
        }
        return success;
    }

    @Override
    public String GetUsageDescription(IUsableTarget target) {
        boolean success = Ice.class.isInstance(target);
        if(success){
            return "You melt the ice with the hairdrier! " +
                    "There's a "+((Ice)target).GetEncasedObject().GetName()+" in the puddle.";
        } else {
            return "You hairdry it, but nothing happens.";
        }
    }

    @Override
    public void UnUse(IUsableTarget target, Player player) {
        boolean success = Ice.class.isInstance(target);
        if(success){
            ((Ice)target).UnMelt(player);
        }
    }
}
