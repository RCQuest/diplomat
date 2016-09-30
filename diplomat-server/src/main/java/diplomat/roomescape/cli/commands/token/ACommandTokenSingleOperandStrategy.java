package diplomat.roomescape.cli.commands.token;

import diplomat.roomescape.cli.commands.InvalidCommandException;

/**
 * Created by rachelcabot on 30/09/2016.
 */
public abstract class ACommandTokenSingleOperandStrategy extends ACommandTokenStrategy {
    protected GameObjectTokenStrategy objectStrategy;

    public void AssignAsProperty(GameObjectTokenStrategy objectStrategy) throws InvalidCommandException {
        if(this.objectStrategy==null) this.objectStrategy = objectStrategy;
        else if(this.next!=null) this.next.AssignAsProperty(objectStrategy);
        else throw new InvalidCommandException();
    }

    public GameObjectTokenStrategy GetObjectStrategy() throws InvalidCommandException {
        if(objectStrategy==null) throw new InvalidCommandException();
        return objectStrategy;
    }
}
