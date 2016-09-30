package diplomat.roomescape.cli.commands;

import diplomat.roomescape.cli.commands.token.*;
import diplomat.roomescape.commands.DoNothingCommand;
import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.actors.Player;
import diplomat.roomescape.commands.IGameCommand;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Rachel on 21/09/2016.
 */
public class CLICommandFactory {

    private final HashMap<String,Class<? extends ACommandTokenStrategy>> commandTokenStrategies = new HashMap<String,Class<? extends ACommandTokenStrategy>>(){{
        put("look",LookTokenStrategy.class);
        put("use",UseTokenStrategy.class);
        put("on",OnTokenStrategy.class);
        put("pickup",PickupTokenStrategy.class);
        put("every",EveryTokenStrategy.class);
    }};
    private ArrayList<AGameObject> roomObjects;
    private ArrayList<AGameObject> inventoryObjects;


    public IGameCommand CreateCommand(String commandString) throws InvalidCommandException {
        String[] commandTokens = tokenize(commandString);

        if(!areValidTokens(commandTokens)) throw new InvalidCommandException();

        String firstToken = commandTokens[0];

        ACommandTokenStrategy strategySequence;

        try {
            strategySequence = commandTokenStrategies.get(firstToken).newInstance();
            for (int i = 1; i < commandTokens.length; i++) {
                String commandToken = commandTokens[i];
                if(isCommandToken(commandToken)) {
                    strategySequence.AssignAsProperty(commandTokenStrategies.get(commandToken).newInstance());
                } else {
                    strategySequence.AssignAsProperty(new GameObjectTokenStrategy(getGameObject(commandToken)));
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new InvalidCommandException();
        }

        return strategySequence.collapseToCommand();
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

    private boolean isCommandToken(String token){
        return Arrays.asList(getValidCommandTokens()).contains(token);
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

    private AGameObject getGameObject(String gameObjectName) {
        ArrayList<AGameObject> objects = new ArrayList<>();
        objects.addAll(this.inventoryObjects);
        objects.addAll(this.roomObjects);
        return objects.stream()
                .filter(x -> x.GetName().equals(gameObjectName))
                .findFirst()
                .get();
    }

    private String[] getValidCommandTokens(){
        Set<String> set = commandTokenStrategies.keySet();
        return set.toArray(new String[set.size()]);
    }
}
