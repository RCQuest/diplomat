package diplomat.roomescape.cli.commands.token;

import diplomat.roomescape.cli.commands.InvalidCommandException;
import diplomat.roomescape.commands.IGameCommand;
import diplomat.roomescape.commands.PickupCommand;
import diplomat.roomescape.commands.UseCommand;
import diplomat.roomescape.commands.UseOnCommand;
import diplomat.roomescape.gameobjects.*;

/**
 * Created by rachelcabot on 29/09/2016.
 */
public class UseTokenStrategy extends ACommandTokenSingleOperandStrategy {

    @Override
    public IGameCommand collapseToCommand() throws InvalidCommandException {
        AGameObject object = this.GetObjectStrategy().GetGameObject();
        if(OnTokenStrategy.class.isInstance(next)) {
            AGameObject target = ((OnTokenStrategy)next).GetObjectStrategy().GetGameObject();
            if(!IUsable.class.isInstance(object))
                throw new InvalidCommandException();
            if(!IUsableTarget.class.isInstance(target))
                throw new InvalidCommandException();
            UseOnCommand command = new UseOnCommand((IUsable)object, (IUsableTarget)target);
            return command;
        } else {
            if(!IStandaloneUsable.class.isInstance(object))
                throw new InvalidCommandException();
            UseCommand command = new UseCommand((IStandaloneUsable)object);
            return command;
        }
    }
}
