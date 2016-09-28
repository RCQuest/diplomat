package diplomat.roomescape;

import diplomat.roomescape.commands.IGameCommand;
import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.actors.Player;
import diplomat.roomescape.gameobjects.actors.Room;

import java.util.ArrayList;
import java.util.List;

public class GameController {

    private IRoomEscapeViewModel viewModel;
    private Player player;

    public GameController(IRoomEscapeViewModel viewModel) {
        this.viewModel = viewModel;
        List<AGameObject> objects = new ArrayList<>();

        this.player = new Player(new Room(objects));
        player.GetRoom().Describe();
    }

    public void HandleCommand(IGameCommand command) {
        command.Execute(player,viewModel);
    }

    public Player GetPlayer() {
        return player;
    }
}
