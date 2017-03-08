package diplomat.roomescape.commands;

import diplomat.roomescape.*;
import diplomat.roomescape.gameobjects.IUsable;
import diplomat.roomescape.gameobjects.IUsableTarget;
import diplomat.roomescape.gameobjects.ObjectGroup;
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
        if(ObjectGroup.class.isInstance(target)){
            viewModel.ShowGroupUseOnResult(object,(ObjectGroup)target);
            wasSuccessful = ((ObjectGroup)target).PerformGroupUseOn(object,player);
        } else {
            viewModel.ShowUseOnResult(object,target);
            wasSuccessful = object.Use(target,player);
        }
    }

    @Override
    public void Undo(Player player) {
        if(ObjectGroup.class.isInstance(target)){
            ((ObjectGroup)target).PerformGroupUnUseOn(object,player);
        } else {
            object.UnUse(target,player);
        }
    }

    @Override
    public boolean WasSuccessful() {
        return wasSuccessful;
    }
}
