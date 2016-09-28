package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.*;

/**
 * Created by Rachel on 21/09/2016.
 */
public class Player {
    private Room currentRoom;
    private Inventory playerInventory;

    public String Look(IExaminable at) {
        return at.Describe();
    }

    public void Pickup(IObtainable object) {
        playerInventory.AddToInventory(object);
    }

    public void Use(IStandaloneUsable object) {
        object.Use(this);
    }

    public void UseOn(IUsable object, IUsableTarget target) {
        object.Use(target, this);
    }

    public Room GetRoom() {
        return currentRoom;
    }
}
