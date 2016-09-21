package roomescape;

class GameController {

    private IRoomEscapeViewModel viewModel;
    private Player player;

    GameController(IRoomEscapeViewModel viewModel, Player player) {
        this.viewModel = viewModel;
        this.player = player;
    }

    private void HandleCommand(IGameCommand command) {
        command.Execute(player,viewModel);
    }
}
