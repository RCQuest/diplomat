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
}
