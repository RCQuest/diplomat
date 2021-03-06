package diplomat.roomescape.cli.commands.token;

import diplomat.roomescape.cli.commands.InvalidCommandException;
import diplomat.roomescape.commands.IGameCommand;

/**
 * Created by rachelcabot on 29/09/2016.
 */
public abstract class ACommandTokenStrategy {
    protected ACommandTokenStrategy next;

    protected void appendToSequence(ACommandTokenStrategy nextStrategy) throws InvalidCommandException {
        if(this.next==null)
            this.next = nextStrategy;
        else
            this.next.AssignAsProperty(nextStrategy);
    }

    public void AssignAsProperty(ACommandTokenStrategy nextStrategy) throws InvalidCommandException {
        appendToSequence(nextStrategy);
    }

    public abstract IGameCommand collapseToCommand() throws InvalidCommandException;
}
