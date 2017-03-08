package diplomat.roomescape.cli.commands.token;

import diplomat.roomescape.cli.commands.InvalidCommandException;
import diplomat.roomescape.commands.IGameCommand;
import diplomat.roomescape.commands.PlaceOnCommand;
import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.IPlaceable;
import diplomat.roomescape.gameobjects.IPlaceableTarget;

/**
 * Created by rachelcabot on 08/03/2017.
 */
public class PlaceTokenStrategy extends ACommandTokenSingleOperandStrategy {
    @Override
    public IGameCommand collapseToCommand() throws InvalidCommandException {
        AGameObject object = this.GetObjectStrategy().GetGameObject();
        if(OnTokenStrategy.class.isInstance(next)) {
            AGameObject target = ((OnTokenStrategy)next).GetObjectStrategy().GetGameObject();
            if(!IPlaceable.class.isInstance(object))
                throw new InvalidCommandException();
            if(!IPlaceableTarget.class.isInstance(target))
                throw new InvalidCommandException();
            PlaceOnCommand command = new PlaceOnCommand((IPlaceable)object, (IPlaceableTarget)target);
            return command;
        } else {
            throw new InvalidCommandException();
        }
    }
}
