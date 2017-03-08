package diplomat.roomescape.commands;

import diplomat.roomescape.*;
import diplomat.roomescape.gameobjects.IUsable;
import diplomat.roomescape.gameobjects.IUsableTarget;
import diplomat.roomescape.gameobjects.actors.Player;

/**
 * Created by Rachel on 21/09/2016.
 */
public class UseOnCommand implements IGameCommand {
    private IUsable object;
    private IUsableTarget target;
    private boolean wasSuccessful;

    public UseOnCommand(IUsable object, IUsableTarget target) {
        this.object = object;
        this.target = target;
    }


    @Override
    public void Execute(Player player, IRoomEscapeViewModel viewModel) {
        viewModel.ShowUseOnResult(object,target);
        wasSuccessful = object.Use(target,player);
        //TODO: what is target is object group?
    }

    @Override
    public void Undo(Player player) {
        object.UnUse(target,player);
    }

    @Override
    public boolean WasSuccessful() {
        return wasSuccessful;
    }
}
