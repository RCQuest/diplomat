package roomescape.commands;

import roomescape.IRoomEscapeViewModel;
import roomescape.gameobjects.IStandaloneUsable;
import roomescape.gameobjects.actors.Player;

/**
 * Created by Rachel on 21/09/2016.
 */
public class UseCommand implements IGameCommand {
    private IStandaloneUsable object;

    public UseCommand(IStandaloneUsable object) {
        this.object = object;
    }

    @Override
    public void Execute(Player player, IRoomEscapeViewModel viewModel) {
        object.Use(player);
        viewModel.ShowUseResult(object);
    }
}
