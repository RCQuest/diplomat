package roomescape;

import roomescape.commands.IGameCommand;
import roomescape.gameobjects.actors.Player;

public class GameController {

    private IRoomEscapeViewModel viewModel;
    private Player player;

    GameController(IRoomEscapeViewModel viewModel, Player player) {
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
