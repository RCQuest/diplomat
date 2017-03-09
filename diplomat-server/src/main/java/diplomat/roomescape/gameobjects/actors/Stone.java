package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.AObtainable;
import diplomat.roomescape.gameobjects.IExaminable;
import diplomat.roomescape.gameobjects.IUsable;
import diplomat.roomescape.gameobjects.IUsableTarget;

/**
 * Created by rachelcabot on 08/03/2017.
 */
public class Stone extends AObtainable implements IExaminable,IUsable {
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

    @Override
    public boolean Use(IUsableTarget target, Player player) {
        if(Tube.class.isInstance(target)){
            ((Tube)target).InsertObject(this, player);
            player.RemoveFromRoom(this);
            return true;
        }
        return false;
    }

    @Override
    public String GetUsageDescription(IUsableTarget target) {
        if(Tube.class.isInstance(target)){
            String a ="";
            if(((Tube)target).WaterLevel()>0.9){
                a = "The key has risen to the top, and you can now reach it. ";
            }
            return "You insert the stone into the tube. It displaces the water inside a bit. "+a;
        }
        return "";
    }

    @Override
    public void UnUse(IUsableTarget target, Player player) {
        if(Tube.class.isInstance(target)){
            player.AddToRoom(this);
            ((Tube)target).UnInsertObject(this, player);
        }
    }
}
