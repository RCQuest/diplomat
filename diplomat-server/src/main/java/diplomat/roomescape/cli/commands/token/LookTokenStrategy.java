package diplomat.roomescape.cli.commands.token;

import diplomat.roomescape.cli.commands.InvalidCommandException;
import diplomat.roomescape.commands.IGameCommand;
import diplomat.roomescape.commands.LookCommand;
import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.IExaminable;

/**
 * Created by rachelcabot on 29/09/2016.
 */
public class LookTokenStrategy extends ACommandTokenSingleOperandStrategy {

    @Override
    public IGameCommand collapseToCommand() throws InvalidCommandException {
        AGameObject object = this.GetObjectStrategy().GetGameObject();
        if(!IExaminable.class.isInstance(object))
            throw new InvalidCommandException();
        LookCommand command = new LookCommand((IExaminable)object);
        return command;
    }
}
