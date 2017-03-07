package diplomat.roomescape.commands;

import diplomat.roomescape.IRoomEscapeViewModel;
import diplomat.roomescape.gameobjects.AGameOverInvoker;
import diplomat.roomescape.gameobjects.actors.Player;

/**
 * Created by rachelcabot on 07/03/2017.
 */
public class StartCommand extends AGameOverInvoker implements IGameCommand {
    public StartCommand() {
        super("start");
    }

    @Override
    public void Execute(Player player, IRoomEscapeViewModel viewModel) {
        viewModel.ShowRestartCode();
        player.NextRoom();
    }

    @Override
    public void Undo(Player player) {
        // shouldn't happen
    }

    @Override
    public boolean WasSuccessful() {
        return false;
    }
}
