package diplomat.roomescape.commands;

import diplomat.roomescape.IRoomEscapeViewModel;
import diplomat.roomescape.gameobjects.AObtainable;
import diplomat.roomescape.gameobjects.actors.Player;

public class PickupCommand implements IGameCommand {
    private AObtainable object;

    public PickupCommand(AObtainable object) {
        this.object = object;
    }

    @Override
    public void Execute(Player player, IRoomEscapeViewModel viewModel) {
        viewModel.ShowPickupResult(object,player);
        player.Pickup(object);
    }
}
