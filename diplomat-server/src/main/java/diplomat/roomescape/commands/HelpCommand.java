package diplomat.roomescape.commands;

import diplomat.roomescape.IRoomEscapeViewModel;
import diplomat.roomescape.gameobjects.actors.Player;

/**
 * Created by rachelcabot on 20/02/2017.
 */
public class HelpCommand implements IGameCommand {
    @Override
    public void Execute(Player player, IRoomEscapeViewModel viewModel) {
        viewModel.ShowHelp();
    }

    @Override
    public void Undo(Player player) {

    }

    @Override
    public boolean WasSuccessful() {
        return true;
    }
}
