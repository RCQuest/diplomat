package diplomat.roomescape.cli;

import diplomat.Diplomat;
import diplomat.cli.Command;
import diplomat.cli.CommandController;
import diplomat.eventsystem.events.core.IEventListener;
import diplomat.roomescape.GameController;
import diplomat.roomescape.IRoomEscapeViewModel;
import diplomat.roomescape.cli.commands.CLICommandFactory;
import diplomat.roomescape.cli.commands.InvalidCommandException;
import diplomat.roomescape.commands.DoNothingCommand;
import diplomat.roomescape.commands.IGameCommand;
import diplomat.roomescape.gameobjects.actors.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Stack;

@Component
public class CLIGameBootstrap implements IEventListener<Command>{
    private GameController game;
    private CLICommandFactory commandFactory;
    private CommandController cli;
    public final HashMap<String, Stack<String>> taskSets = new HashMap<String, Stack<String>>(){{
        put("1",new Stack<String>(){{
            push("/ropeweight.room");
            push("/watertube.room");
            push("/weightgate.room");
            push("/pressurepad.room");
            push("/sphere.room");
            push("/pailtube.room");
            push("/clothkey.room");
            push("/keydoor.room");

        }});
        put("2",new Stack<String>(){{
            push("/nestedcrates.room");
            push("/manyice.room");
            push("/cratetrapdoor.room");
            push("/hairdrier.room");
            push("/cratekey.room");
            push("/ladderhatch.room");
            push("/keydoor.room");

        }});
    }};

    @Autowired
    public CLIGameBootstrap(CommandController cli){
        IRoomEscapeViewModel viewModel = new CLIViewModel(cli);
        String taskSet = Diplomat.args[0];
        this.cli = cli;
        this.commandFactory = new CLICommandFactory();
        this.game = new GameController(viewModel,taskSets.get(taskSet));
        this.cli.commandEvent.subscribe(this);
    }

    @Override
    public void onReceived(Object sender, Command args) {
        this.commandFactory.UpdateAvailableGameObjects(game.GetAllGameObjects(),game.GetPlayerInventory());
        IGameCommand command;
        try {
            command = this.commandFactory.CreateCommand(args.getCommandString());
        } catch (InvalidCommandException e) {
            this.cli.AppendToResponse("Invalid command.");
            command = new DoNothingCommand();
        }
        game.HandleCommand(command);

    }
}
