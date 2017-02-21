package diplomat.roomescape.gameobjects.actors;

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
    private Inventory playerInventory;
    private Stack<IGameCommand> commandHistory;

    public Player(Room initialRoom){
        this.currentRoom = initialRoom;
        this.playerInventory = new Inventory();
        this.commandHistory = new Stack<>();
    }

    public void Pickup(AObtainable object) {
        playerInventory.AddToInventory(object);
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
            lastCommand.Undo(this);
        }
    }

    public void PutDown(AObtainable object) {
        playerInventory.Discard(object);
    }

    public void Store(IGameCommand command) {
        commandHistory.push(command);
    }
}
