package diplomat.roomescape.cli.commands.token;

import diplomat.roomescape.cli.commands.InvalidCommandException;

/**
 * Created by rachelcabot on 30/09/2016.
 */
public abstract class ACommandTokenSingleOperandStrategy extends ACommandTokenStrategy {
    protected GameObjectTokenStrategy objectStrategy;

    @Override
    public void AssignAsProperty(ACommandTokenStrategy strategy) throws InvalidCommandException {
        System.out.println("AssignAsProperty");
        boolean isGameObjectStrategy = GameObjectTokenStrategy.class.isInstance(strategy);
        if(isGameObjectStrategy&&this.objectStrategy==null)
            this.objectStrategy = (GameObjectTokenStrategy)strategy;
        else
            this.appendToSequence(strategy);
    }

    public GameObjectTokenStrategy GetObjectStrategy() throws InvalidCommandException {
        if(objectStrategy==null) throw new InvalidCommandException();
        return objectStrategy;
    }
}
