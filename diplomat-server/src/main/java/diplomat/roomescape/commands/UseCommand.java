package diplomat.roomescape.commands;

import diplomat.roomescape.IRoomEscapeViewModel;
import diplomat.roomescape.gameobjects.IStandaloneUsable;
import diplomat.roomescape.gameobjects.actors.Player;

/**
 * Created by Rachel on 21/09/2016.
 */
public class UseCommand implements IGameCommand {
    private IStandaloneUsable object;
    private boolean wasSuccessful;

    public UseCommand(IStandaloneUsable object) {
        this.object = object;
    }

    @Override
    public void Execute(Player player, IRoomEscapeViewModel viewModel) {
        viewModel.ShowUseResult(object);
        wasSuccessful = object.Use();
    }

    @Override
    public void Undo(Player player) {
        object.UnUse();
    }

    @Override
    public boolean WasSuccessful() {
        return wasSuccessful;
    }
}
