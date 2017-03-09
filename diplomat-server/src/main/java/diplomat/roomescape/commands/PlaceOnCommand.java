package diplomat.roomescape.commands;

import diplomat.roomescape.IRoomEscapeViewModel;
import diplomat.roomescape.gameobjects.IPlaceable;
import diplomat.roomescape.gameobjects.IPlaceableTarget;
import diplomat.roomescape.gameobjects.actors.Player;

/**
 * Created by rachelcabot on 08/03/2017.
 */
public class PlaceOnCommand implements IGameCommand {
    private IPlaceable object;
    private IPlaceableTarget target;
    private boolean wasSuccessful;

    public PlaceOnCommand(IPlaceable object, IPlaceableTarget target) {

        this.object = object;
        this.target = target;
    }

    @Override
    public void Execute(Player player, IRoomEscapeViewModel viewModel) {
        wasSuccessful = object.Place(target,player);
        viewModel.ShowPlaceOnResult(object,target);
    }

    @Override
    public void Undo(Player player) {
        object.UnPlace(target,player);
    }

    @Override
    public boolean WasSuccessful() {
        return wasSuccessful;
    }
}
