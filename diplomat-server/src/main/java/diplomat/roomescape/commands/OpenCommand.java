package diplomat.roomescape.commands;

import diplomat.roomescape.IRoomEscapeViewModel;
import diplomat.roomescape.gameobjects.IOpenable;
import diplomat.roomescape.gameobjects.actors.Player;

/**
 * Created by rachelcabot on 08/03/2017.
 */
public class OpenCommand implements IGameCommand {
    private IOpenable object;
    private boolean wasSuccesful;

    public OpenCommand(IOpenable object) {

        this.object = object;
    }

    @Override
    public void Execute(Player player, IRoomEscapeViewModel viewModel) {
        wasSuccesful = object.OpenSelf(player);
        viewModel.ShowOpenResult(object);
    }

    @Override
    public void Undo(Player player) {
        object.UnOpenSelf(player);
    }

    @Override
    public boolean WasSuccessful() {
        return wasSuccesful;
    }
}
