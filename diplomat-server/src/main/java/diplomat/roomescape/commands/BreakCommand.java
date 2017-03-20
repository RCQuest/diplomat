package diplomat.roomescape.commands;

import diplomat.roomescape.IRoomEscapeViewModel;
import diplomat.roomescape.gameobjects.IBreakable;
import diplomat.roomescape.gameobjects.actors.Player;

/**
 * Created by rachelcabot on 08/03/2017.
 */
public class BreakCommand implements IGameCommand {
    private IBreakable object;
    private boolean wasSuccessful;

    public BreakCommand(IBreakable object) {

        this.object = object;
    }

    @Override
    public void Execute(Player player, IRoomEscapeViewModel viewModel) {
        wasSuccessful = object.BreakSelf(player);
        viewModel.ShowBreakResult(object);
    }

    @Override
    public void Undo(Player player) {
        object.UnBreakSelf(player);
    }

    @Override
    public boolean WasSuccessful() {
        return wasSuccessful;
    }
}
