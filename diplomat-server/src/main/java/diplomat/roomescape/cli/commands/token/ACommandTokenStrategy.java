package diplomat.roomescape.cli.commands.token;

import diplomat.roomescape.commands.IGameCommand;

/**
 * Created by rachelcabot on 29/09/2016.
 */
public abstract class ACommandTokenStrategy {
    private ACommandTokenStrategy next;

    public void appendToSequence(ACommandTokenStrategy nextStrategy) {
        if(this.next==null) this.next = nextStrategy;
        else this.next.appendToSequence(nextStrategy);
    }

    public abstract IGameCommand collapseToCommand();
}
