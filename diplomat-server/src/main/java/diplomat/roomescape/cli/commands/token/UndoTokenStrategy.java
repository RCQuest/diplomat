package diplomat.roomescape.cli.commands.token;

import diplomat.roomescape.cli.commands.InvalidCommandException;
import diplomat.roomescape.commands.IGameCommand;
import diplomat.roomescape.commands.UndoCommand;

/**
 * Created by rachelcabot on 21/02/2017.
 */
public class UndoTokenStrategy extends ACommandTokenStrategy {
    @Override
    public IGameCommand collapseToCommand() throws InvalidCommandException {
        return new UndoCommand();
    }
}
