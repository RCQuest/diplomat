package roomescape.cli;

import roomescape.gameobjects.actors.Player;
import roomescape.commands.IGameCommand;
import roomescape.commands.LookCommand;

/**
 * Created by Rachel on 21/09/2016.
 */
public class CLICommandFactory {

    public IGameCommand CreateCommand(String commandString, Player player){
        return new LookCommand(player.GetRoom());
    }
}
