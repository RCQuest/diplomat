package roomescape;

/**
 * Created by Rachel on 21/09/2016.
 */
public class CLICommandFactory {

    public IGameCommand CreateCommand(String commandString, Player player){
        return new LookCommand(player.GetRoom());
    }
}
