package diplomat.roomescape;

import diplomat.roomescape.commands.IGameCommand;
import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.actors.Door;
import diplomat.roomescape.gameobjects.actors.Key;
import diplomat.roomescape.gameobjects.actors.Player;
import diplomat.roomescape.gameobjects.actors.Room;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GameController {

    private IRoomEscapeViewModel viewModel;
    private Player player;

    public GameController(IRoomEscapeViewModel viewModel) {
        this.viewModel = viewModel;
        ArrayList<AGameObject> objects = new ArrayList<>();
        objects.add(new Door());
        objects.add(new Key());
        this.player = new Player(new Room(objects));
    }

    public void HandleCommand(IGameCommand command) {
        command.Execute(player,viewModel);
    }

    public Player GetPlayer() {
        return player;
    }

    public ArrayList<AGameObject> GetAllGameObjects() {
        return player.GetRoom().GetAllRoomObjects();
    }

    public ArrayList<AGameObject> GetPlayerInventory() {
        return player.GetInventoryObjects();
    }
}
