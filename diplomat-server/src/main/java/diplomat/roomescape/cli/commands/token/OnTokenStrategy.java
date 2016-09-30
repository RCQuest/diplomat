package diplomat.roomescape.cli.commands.token;

import diplomat.roomescape.cli.commands.InvalidCommandException;
import diplomat.roomescape.commands.IGameCommand;

/**
 * Created by rachelcabot on 29/09/2016.
 */
public class OnTokenStrategy extends ACommandTokenSingleOperandStrategy {

    @Override
    public IGameCommand collapseToCommand() throws InvalidCommandException {
        throw new InvalidCommandException();
    }
}
