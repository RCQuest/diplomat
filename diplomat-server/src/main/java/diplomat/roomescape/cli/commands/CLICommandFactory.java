package diplomat.roomescape.cli.commands;

import diplomat.roomescape.cli.commands.token.*;
import diplomat.roomescape.commands.DoNothingCommand;
import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.actors.Player;
import diplomat.roomescape.commands.IGameCommand;
import diplomat.roomescape.commands.LookCommand;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Rachel on 21/09/2016.
 */
public class CLICommandFactory {

    private final HashMap<String,ICommandTokenStrategy> commandTokenStrategies = new HashMap<String,ICommandTokenStrategy>(){{
        put("look",new LookTokenStrategy());
        put("use",new UseTokenStrategy());
        put("on",new OnTokenStrategy());
        put("pickup",new PickupTokenStrategy());
        put("every",new EveryTokenStrategy());
    }};
    private ArrayList<AGameObject> roomObjects;
    private ArrayList<AGameObject> inventoryObjects;


    public IGameCommand CreateCommand(String commandString, Player player){
        String[] commandTokens = tokenize(commandString);

        if(!areValidTokens(commandTokens)) return new DoNothingCommand();

        for(String commandToken : commandTokens){

        }

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
        List<String> validTokens = new ArrayList<>(Arrays.asList(getValidCommandTokens()));
        validTokens.addAll(GetNames(roomObjects));
        validTokens.addAll(GetNames(inventoryObjects));
        return validTokens.contains(token);
    }

    public void UpdateAvailableGameObjects(ArrayList<AGameObject> roomObjects, ArrayList<AGameObject> inventoryObjects) {
        this.roomObjects = roomObjects;
        this.inventoryObjects = inventoryObjects;
    }

    private List<String> GetNames(ArrayList<AGameObject> objects) {
        List<String> names = objects.stream()
            .map(object -> object.GetName())
            .collect(Collectors.toList());
        System.out.println(names.toString());
        return names;
    }

    private String[] getValidCommandTokens(){
        Set<String> set = commandTokenStrategies.keySet();
        return set.toArray(new String[set.size()]);
    }
}
