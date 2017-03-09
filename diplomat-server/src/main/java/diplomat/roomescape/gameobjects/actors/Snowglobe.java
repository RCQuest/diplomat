package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.IBreakable;
import diplomat.roomescape.gameobjects.IExaminable;
import diplomat.roomescape.gameobjects.IStandaloneUsable;

/**
 * Created by rachelcabot on 09/03/2017.
 */
public class Snowglobe extends AGameObject implements IExaminable,IBreakable, IStandaloneUsable {
    private AGameObject containedObject;

    public Snowglobe(AGameObject containedObject) {
        super("snowglobe");
        this.containedObject = containedObject;
    }

    @Override
    public boolean BreakSelf(Player player) {
        player.RemoveFromRoom(this);
        player.AddToRoom(containedObject);
        return true;
    }

    @Override
    public String GetBreakDescription() {
        return "You smash the snowglobe. It's a complete mess. Amongst the debris, there's a key. ";
    }

    @Override
    public void UnBreakSelf(Player player) {
        player.AddToRoom(this);
        player.RemoveFromRoom(containedObject);
    }

    @Override
    public String Describe() {
        return "It's a glass snowglobe. It looks as though there's a key inside. ";
    }

    @Override
    public boolean Use(Player player) {
        return true;
    }

    @Override
    public String GetUsageDescription() {
        return "You shake the snowglobe. It's very pretty. ";
    }

    @Override
    public void UnUse() {

    }
}
