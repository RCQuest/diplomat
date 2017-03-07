package diplomat.roomescape.cli.commands.token;

import diplomat.roomescape.cli.commands.InvalidCommandException;
import diplomat.roomescape.commands.IGameCommand;
import diplomat.roomescape.commands.StartCommand;

/**
 * Created by rachelcabot on 07/03/2017.
 */
public class StartTokenStrategy extends ACommandTokenStrategy {
    @Override
    public IGameCommand collapseToCommand() throws InvalidCommandException {
        return new StartCommand();
    }
}
