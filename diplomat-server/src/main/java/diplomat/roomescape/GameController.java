package diplomat.roomescape;

import diplomat.roomescape.commands.IGameCommand;
import diplomat.roomescape.gameobjects.actors.Player;

public class GameController {

    private IRoomEscapeViewModel viewModel;
    private Player player;

    public GameController(IRoomEscapeViewModel viewModel, Player player) {
        this.viewModel = viewModel;
        this.player = player;
    }

    public void HandleCommand(IGameCommand command) {
        command.Execute(player,viewModel);
    }

    public Player GetPlayer() {
        return player;
    }
}
