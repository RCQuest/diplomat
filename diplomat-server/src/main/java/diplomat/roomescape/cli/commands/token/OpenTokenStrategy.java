package diplomat.roomescape.cli.commands.token;

import diplomat.roomescape.cli.commands.InvalidCommandException;
import diplomat.roomescape.commands.IGameCommand;
import diplomat.roomescape.commands.OpenCommand;
import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.IOpenable;

/**
 * Created by rachelcabot on 08/03/2017.
 */
public class OpenTokenStrategy extends ACommandTokenSingleOperandStrategy {
    @Override
    public IGameCommand collapseToCommand() throws InvalidCommandException {
        AGameObject object = this.GetObjectStrategy().GetGameObject();
        if(!IOpenable.class.isInstance(object))
            throw new InvalidCommandException();
        OpenCommand command = new OpenCommand((IOpenable) object);
        return command;
    }
}
