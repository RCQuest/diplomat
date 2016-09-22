package roomescape.commands;

import roomescape.*;
import roomescape.gameobjects.IUsable;
import roomescape.gameobjects.IUsableTarget;
import roomescape.gameobjects.actors.Player;

/**
 * Created by Rachel on 21/09/2016.
 */
public class UseOnCommand implements IGameCommand {
    private IUsable object;
    private IUsableTarget target;

    public UseOnCommand(IUsable object, IUsableTarget target) {
        this.object = object;
        this.target = target;
    }


    @Override
    public void Execute(Player player, IRoomEscapeViewModel viewModel) {
        object.Use(target,player);
        viewModel.ShowUseOnResult(object,target);
    }
}
