package diplomat.roomescape.commands;

import diplomat.roomescape.gameobjects.IExaminable;
import diplomat.roomescape.IRoomEscapeViewModel;
import diplomat.roomescape.gameobjects.actors.Player;

/**
 * Created by Rachel on 21/09/2016.
 */
public class LookCommand implements IGameCommand {
    private IExaminable at;

    public LookCommand(IExaminable at) {
        this.at = at;
    }

    @Override
    public void Execute(Player player, IRoomEscapeViewModel viewModel) {
        viewModel.ShowLookResult(this.at);
    }

    @Override
    public void Undo(Player player) {

    }
}
