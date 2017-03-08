package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.IBreakable;
import diplomat.roomescape.gameobjects.IExaminable;
import diplomat.roomescape.gameobjects.IUsableTarget;

/**
 * Created by rachelcabot on 08/03/2017.
 */
public class Ice extends AGameObject implements IUsableTarget,IExaminable,IBreakable {
    private AGameObject encasedObject;

    public Ice(AGameObject encasedObject) {
        super("iceblock");
        this.encasedObject = encasedObject;
    }

    @Override
    public String Describe() {
        return "It's a block of ice. There's a "+encasedObject.GetName()+" encased in it.";
    }

    public void Melt(Player player){
        player.RemoveFromRoom(this);
        player.AddToRoom(encasedObject);
    }


    public void UnMelt(Player player){
        player.AddToRoom(this);
        player.RemoveFromRoom(encasedObject);
    }

    public AGameObject GetEncasedObject() {
        return encasedObject;
    }

    @Override
    public boolean BreakSelf(Player player) {
        player.RemoveFromRoom(this);
        return true;
    }

    @Override
    public String GetBreakDescription() {
        return "You pickup the ice and smash it on the floor. " +
                "Whatever was inside has also shattered! ";
    }

    @Override
    public void UnBreakSelf(Player player) {
        player.AddToRoom(this);
    }
}
