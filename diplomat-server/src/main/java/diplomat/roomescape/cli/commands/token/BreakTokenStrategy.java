package diplomat.roomescape.cli.commands.token;

import diplomat.roomescape.cli.commands.InvalidCommandException;
import diplomat.roomescape.commands.BreakCommand;
import diplomat.roomescape.commands.IGameCommand;
import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.IBreakable;

/**
 * Created by rachelcabot on 08/03/2017.
 */
public class BreakTokenStrategy extends ACommandTokenSingleOperandStrategy {
    @Override
    public IGameCommand collapseToCommand() throws InvalidCommandException {
        AGameObject object = this.GetObjectStrategy().GetGameObject();
        if(!IBreakable.class.isInstance(object))
            throw new InvalidCommandException();
        BreakCommand command = new BreakCommand((IBreakable) object);
        return command;
    }
}
