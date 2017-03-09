package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.IExaminable;
import diplomat.roomescape.gameobjects.IPlaceable;
import diplomat.roomescape.gameobjects.IPlaceableTarget;

/**
 * Created by rachelcabot on 09/03/2017.
 */
public class FloorPad extends AGameObject implements IExaminable, IPlaceableTarget {
    private IPlaceable placedObject;

    public FloorPad() {
        super("floorpad");
    }

    public void Place(IPlaceable object, Player player){

        placedObject = object;
        player.UnlockDoor();
    }
    public void UnPlace(IPlaceable object, Player player){
        player.LockDoor();
        placedObject = null;
    }

    @Override
    public String Describe() {
        return "It's a raised slab on the floor. Looks like it could be pressed down. ";
    }
}
