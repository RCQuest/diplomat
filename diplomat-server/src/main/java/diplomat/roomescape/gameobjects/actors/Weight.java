package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.IExaminable;
import diplomat.roomescape.gameobjects.IPlaceable;
import diplomat.roomescape.gameobjects.IPlaceableTarget;

/**
 * Created by rachelcabot on 09/03/2017.
 */
public class Weight extends AGameObject implements IExaminable, IPlaceable {
    public Weight() {
        super("weight");
    }

    @Override
    public String Describe() {
        return "It's a trapezoidal weight! It says 100KG. ";
    }

    @Override
    public boolean Place(IPlaceableTarget target, Player player) {
        if (FloorPad.class.isInstance(target)) {
            ((FloorPad)target).Place(this,player);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void UnPlace(IPlaceableTarget target, Player player) {
        if (FloorPad.class.isInstance(target)) {
            ((FloorPad)target).UnPlace(this,player);
        }
    }

    @Override
    public String GetPlaceDescription(IPlaceableTarget target) {
        if (FloorPad.class.isInstance(target)) {

            return "You heft the heavy weight onto the pressure pad. You hear the door click as it unlocks. ";
        } else {
            return "You move the heavy weight onto the object. Nothing happens.";
        }
    }
}
