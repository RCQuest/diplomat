package diplomat.roomescape.cli.commands.token;

import diplomat.roomescape.cli.commands.InvalidCommandException;
import diplomat.roomescape.commands.IGameCommand;
import diplomat.roomescape.commands.PickupCommand;
import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.AObtainable;

/**
 * Created by rachelcabot on 29/09/2016.
 */
public class PickupTokenStrategy extends ACommandTokenSingleOperandStrategy {

    @Override
    public IGameCommand collapseToCommand() throws InvalidCommandException {
        AGameObject object = this.GetObjectStrategy().GetGameObject();
        if(!AObtainable.class.isInstance(object))
            throw new InvalidCommandException();
        PickupCommand command = new PickupCommand((AObtainable) object);
        return command;
    }
}
