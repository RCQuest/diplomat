package diplomat.roomescape;

import diplomat.roomescape.commands.IGameCommand;
import diplomat.roomescape.commands.UndoCommand;
import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.AGameOverInvoker;
import diplomat.roomescape.gameobjects.actors.Player;
import diplomat.roomescape.gameobjects.actors.Room;

import java.util.ArrayList;

public class GameController {

    private IRoomEscapeViewModel viewModel;
    private Player player;
    private RoomFactory roomFactory;

    public GameController(IRoomEscapeViewModel viewModel) {
        this.viewModel = viewModel;
        this.roomFactory = new RoomFactory();

        ResetGame("/keydoor.room");
    }

    private void ResetGame(String roomPath) {
        Room room = roomFactory.CreateRoom(roomPath);
        this.player = new Player(room);
        viewModel.SetPlayer(this.player);
        room.AddObject(this.player.GetInventory());
        SubscribeToGameOverCallbacks();
    }

    private void SubscribeToGameOverCallbacks() {
        player.GetRoom().GetAllRoomObjects().stream().forEach(object->{
            if(AGameOverInvoker.class.isInstance(object))
                ((AGameOverInvoker)object).SetGameOverCallback(this::OnGameOver);
        });
    }

    public void HandleCommand(IGameCommand command) {
        command.Execute(player,viewModel);
        if(!UndoCommand.class.isInstance(command)){
            player.Store(command);
        }
    }

    public ArrayList<AGameObject> GetAllGameObjects() {
        return player.GetRoom().GetAllRoomObjects();
    }

    public ArrayList<AGameObject> GetPlayerInventory() {
        return player.GetInventoryObjects();
    }

    private void OnGameOver() {
        viewModel.ShowGameComplete();
        ResetGame("/justdoor.room");
    }
}
