package diplomat.roomescape.commands;

import diplomat.roomescape.IRoomEscapeViewModel;
import diplomat.roomescape.gameobjects.actors.Player;

public interface IGameCommand {
    void Execute(Player player, IRoomEscapeViewModel viewModel);
}
