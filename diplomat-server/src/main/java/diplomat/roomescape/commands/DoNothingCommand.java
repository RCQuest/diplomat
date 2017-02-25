package diplomat.roomescape.commands;

import diplomat.roomescape.IRoomEscapeViewModel;
import diplomat.roomescape.gameobjects.actors.Player;

/**
 * Created by rachelcabot on 28/09/2016.
 */
public class DoNothingCommand implements IGameCommand {
    @Override
    public void Execute(Player player, IRoomEscapeViewModel viewModel) {
        return;
    }

    @Override
    public void Undo(Player player) {

    }

    @Override
    public boolean WasSuccessful() {
        return false;
    }
}
