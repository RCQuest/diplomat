package diplomat.roomescape.commands;

import diplomat.roomescape.IRoomEscapeViewModel;
import diplomat.roomescape.gameobjects.actors.Player;

/**
 * Created by rachelcabot on 21/02/2017.
 */
public class UndoCommand implements IGameCommand {
    @Override
    public void Execute(Player player, IRoomEscapeViewModel viewModel) {
        viewModel.ShowUndoResult();
        player.UndoLastCommand();
    }

    @Override
    public void Undo(Player player) {
        System.err.println("This should never happen!");
    }

    @Override
    public boolean WasSuccessful() {
        return true;
    }
}
