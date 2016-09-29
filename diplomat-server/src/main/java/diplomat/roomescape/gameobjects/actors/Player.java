package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.*;

import java.util.ArrayList;

/**
 * Created by Rachel on 21/09/2016.
 */
public class Player {
    private Room currentRoom;
    private Inventory playerInventory;

    public Player(Room initialRoom){
        this.currentRoom = initialRoom;
        this.playerInventory = new Inventory();
    }

    public void Pickup(IObtainable object) {
        playerInventory.AddToInventory(object);
    }

    public Room GetRoom() {
        return this.currentRoom;
    }

    public void Discard(IObtainable obj) {
        playerInventory.Discard(obj);
    }

    public ArrayList<AGameObject> GetInventoryObjects() {
        return playerInventory.GetInventoryObjects();
    }
}
