package diplomat.roomescape.commands;

import diplomat.roomescape.IRoomEscapeViewModel;
import diplomat.roomescape.gameobjects.IObtainable;
import diplomat.roomescape.gameobjects.actors.Player;

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
