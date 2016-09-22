package roomescape.commands;

import roomescape.IRoomEscapeViewModel;
import roomescape.gameobjects.IObtainable;
import roomescape.gameobjects.actors.Player;

public class PickupCommand implements IGameCommand {
    private IObtainable object;

    public PickupCommand(IObtainable object) {
        this.object = object;
    }

    @Override
    public void Execute(Player player, IRoomEscapeViewModel viewModel) {
        player.Pickup(object);
        viewModel.ShowPickupResult(player);
    }
}
