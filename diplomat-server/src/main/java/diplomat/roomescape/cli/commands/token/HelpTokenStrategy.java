package diplomat.roomescape.cli.commands.token;

import diplomat.roomescape.cli.commands.InvalidCommandException;
import diplomat.roomescape.commands.HelpCommand;
import diplomat.roomescape.commands.IGameCommand;

/**
 * Created by rachelcabot on 20/02/2017.
 */
public class HelpTokenStrategy extends ACommandTokenStrategy {
    @Override
    public IGameCommand collapseToCommand() throws InvalidCommandException {
        return new HelpCommand();
    }
}
