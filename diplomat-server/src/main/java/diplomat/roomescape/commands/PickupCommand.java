package diplomat.roomescape.commands;

import diplomat.roomescape.IRoomEscapeViewModel;
import diplomat.roomescape.gameobjects.AObtainable;
import diplomat.roomescape.gameobjects.actors.Player;

public class PickupCommand implements IGameCommand {
    private AObtainable object;
    private boolean didNotHaveItemWhenPerformed;

    public PickupCommand(AObtainable object) {
        this.object = object;
    }

    @Override
    public void Execute(Player player, IRoomEscapeViewModel viewModel) {
        viewModel.ShowPickupResult(object,player);
        didNotHaveItemWhenPerformed = player.Pickup(object);
    }

    @Override
    public void Undo(Player player) {
        player.PutDown(object);
    }

    @Override
    public boolean WasSuccessful() {
        return didNotHaveItemWhenPerformed;
    }
}
