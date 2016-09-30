package diplomat.roomescape.cli.commands.token;

import diplomat.roomescape.cli.commands.InvalidCommandException;
import diplomat.roomescape.commands.IGameCommand;
import diplomat.roomescape.gameobjects.AGameObject;

/**
 * Created by rachelcabot on 30/09/2016.
 */
public class GameObjectTokenStrategy extends ACommandTokenStrategy {
    private AGameObject gameObject;

    public GameObjectTokenStrategy(AGameObject gameObject) {
        this.gameObject = gameObject;
    }

    public AGameObject GetGameObject() throws InvalidCommandException {
        if(gameObject==null) throw new InvalidCommandException();
        return gameObject;
    }

    @Override
    public IGameCommand collapseToCommand() throws InvalidCommandException {
        throw new InvalidCommandException();
    }
}
