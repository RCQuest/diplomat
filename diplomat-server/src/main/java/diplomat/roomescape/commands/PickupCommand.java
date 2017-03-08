package diplomat.roomescape.commands;

import diplomat.roomescape.IRoomEscapeViewModel;
import diplomat.roomescape.gameobjects.AObtainable;
import diplomat.roomescape.gameobjects.ObjectGroup;
import diplomat.roomescape.gameobjects.actors.Player;

public class PickupCommand implements IGameCommand {
    private final boolean isOnGroup;
    private AObtainable object;
    private boolean didNotHaveItemWhenPerformed;

    public PickupCommand(AObtainable object) {
        this.object = object;
        this.isOnGroup = ObjectGroup.class.isInstance(object);
    }

    @Override
    public void Execute(Player player, IRoomEscapeViewModel viewModel) {
        if(isOnGroup) {
            didNotHaveItemWhenPerformed = ((ObjectGroup)object).PerformGroupPickup(player, viewModel);
        } else {
            viewModel.ShowPickupResult(object, player);
            didNotHaveItemWhenPerformed = player.Pickup(object);
        }
    }

    @Override
    public void Undo(Player player) {
        if(isOnGroup){
            ((ObjectGroup)object).PerformGroupPutDown(player);
        }
        player.UnPickup(object);
    }

    @Override
    public boolean WasSuccessful() {
        return didNotHaveItemWhenPerformed;
    }
}
