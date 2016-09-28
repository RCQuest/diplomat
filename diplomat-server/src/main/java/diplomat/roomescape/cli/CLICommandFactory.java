package diplomat.roomescape.cli;

import diplomat.roomescape.commands.DoNothingCommand;
import diplomat.roomescape.gameobjects.actors.Player;
import diplomat.roomescape.commands.IGameCommand;
import diplomat.roomescape.commands.LookCommand;

import java.util.Arrays;

/**
 * Created by Rachel on 21/09/2016.
 */
public class CLICommandFactory {

    private final String[] validTokens = {
            "look",
            "use",
            "on",
            "pickup",
            "every"
    };

    public IGameCommand CreateCommand(String commandString, Player player){
        String[] commandTokens = tokenize(commandString);

        if(!areValidTokens(commandTokens)) return new DoNothingCommand();

        return new LookCommand(player.GetRoom());
    }

    private String[] tokenize(String commandString){
        String[] tokens = commandString.split(" ");
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].toLowerCase();
        }
        return tokens;
    }

    private boolean areValidTokens(String[] tokens){
        for (String token : tokens) {
            if(!isValidToken(token)) return false;
        }
        return true;
    }

    private boolean isValidToken(String token) {
        //and objects
        return Arrays.asList(validTokens).contains(token);
    }
}
