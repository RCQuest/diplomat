package diplomat.roomescape.commands;

import diplomat.roomescape.IRoomEscapeViewModel;
import diplomat.roomescape.commands.IGameCommand;
import diplomat.roomescape.gameobjects.actors.Player;

/**
 * Created by rachelcabot on 28/09/2016.
 */
public class DoNothingCommand implements IGameCommand {
    @Override
    public void Execute(Player player, IRoomEscapeViewModel viewModel) {
        return;
    }
}
