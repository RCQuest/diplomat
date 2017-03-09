package diplomat.roomescape;

import diplomat.roomescape.commands.IGameCommand;
import diplomat.roomescape.commands.UndoCommand;
import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.AGameOverInvoker;
import diplomat.roomescape.gameobjects.actors.Player;
import diplomat.roomescape.gameobjects.actors.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class GameController {

    private IRoomEscapeViewModel viewModel;
    private Stack<String> scenarioList;
    private Player player;
    private RoomFactory roomFactory;


    public GameController(IRoomEscapeViewModel viewModel, Stack<String> scenarioList) {
        this.viewModel = viewModel;
        this.scenarioList = scenarioList;
        this.roomFactory = new RoomFactory();

        ResetGame(this.scenarioList.pop());
    }

    public void ResetGame(String roomPath) {
        Room room = roomFactory.CreateRoom(roomPath);
        this.player = new Player(room,this::NextRoom,this::OnGameOver);
        viewModel.SetPlayer(this.player);
        room.AddObject(this.player.GetInventory());
        SubscribeToGameOverCallbacks();
    }

    public void NextRoom(){
        ResetGame(scenarioList.pop());
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
    }
}
