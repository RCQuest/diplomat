package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.Callback;
import diplomat.roomescape.commands.IGameCommand;
import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.AObtainable;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Rachel on 21/09/2016.
 */
public class Player {
    private Room currentRoom;
    private Callback nextRoomCallback;
    private Callback gameOverCallback;
    private Inventory playerInventory;
    private Stack<IGameCommand> commandHistory;

    public Player(Room initialRoom, Callback nextRoomCallback, Callback gameOverCallback){
        this.currentRoom = initialRoom;
        this.nextRoomCallback = nextRoomCallback;
        this.gameOverCallback = gameOverCallback;
        this.playerInventory = new Inventory();
        this.commandHistory = new Stack<>();
    }

    public boolean Pickup(AObtainable object) {
        return object.PickupSelf(this);
    }

    public Room GetRoom() {
        return this.currentRoom;
    }

    public void Discard(AObtainable obj) {
        playerInventory.Discard(obj);
    }

    public ArrayList<AGameObject> GetInventoryObjects() {
        return playerInventory.GetInventoryObjects();
    }

    public Inventory GetInventory() {
        return playerInventory;
    }

    public void UndoLastCommand() {
        if(!commandHistory.isEmpty()) {
            IGameCommand lastCommand = commandHistory.pop();
            if(lastCommand.WasSuccessful())
                lastCommand.Undo(this);
        }
    }

    public void UnPickup(AObtainable object) {
        object.UnPickupSelf(this);
    }

    public void Store(IGameCommand command) {
        commandHistory.push(command);
    }

    public void NextRoom() {
        nextRoomCallback.Invoke();
    }

    public void AddToRoom(AGameObject obscuredItem) {
        currentRoom.AddObject(obscuredItem);
    }

    public void RemoveFromRoom(AGameObject obscuredItem) {
        currentRoom.RemoveObject(obscuredItem);
    }

    public void InvokeGameOver(){
        gameOverCallback.Invoke();
    }

    public void UnlockDoor() {
        ((Door)currentRoom.GetRoomObject("door")).Unlock();
    }

    public void LockDoor() {
        ((Door)currentRoom.GetRoomObject("door")).Lock();
    }

    public void UnlockPortcullis() {
        ((Portcullis)currentRoom.GetRoomObject("portcullis")).Open();

    }

    public void LockPortcullis() {
        ((Portcullis)currentRoom.GetRoomObject("portcullis")).Shut();

    }
}
