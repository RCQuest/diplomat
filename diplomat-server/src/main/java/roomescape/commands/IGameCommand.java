package roomescape.commands;

import roomescape.IRoomEscapeViewModel;
import roomescape.gameobjects.actors.Player;

public interface IGameCommand {
    void Execute(Player player, IRoomEscapeViewModel viewModel);
}
