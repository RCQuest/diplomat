package roomescape;

/**
 * Created by Rachel on 21/09/2016.
 */
public class Player {
    private Room currentRoom;
    private Inventory playerInventory;

    public String Look(ILookable at) {
        return at.Describe();
    }

    public void Pickup(IObtainable object) {
        playerInventory.AddToInventory(object);
    }

    public void Use(IStandaloneUsable object) {
        object.Use();
    }

    public void UseOn(IUsable object, IUsableTarget target) {
        object.Use(target);
    }

    public Room GetRoom() {
        return currentRoom;
    }
}
