package diplomat.roomescape.cli;

import diplomat.cli.Command;
import diplomat.cli.CommandController;
import diplomat.eventsystem.events.core.IEventListener;
import diplomat.roomescape.GameController;
import diplomat.roomescape.IRoomEscapeViewModel;
import diplomat.roomescape.commands.IGameCommand;
import diplomat.roomescape.gameobjects.actors.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CLIGameBootstrap implements IEventListener<Command>{
    private GameController game;
    private CLICommandFactory commandFactory;
    private CommandController cli;

    @Autowired
    public CLIGameBootstrap(CommandController cli){
        IRoomEscapeViewModel viewModel = new CLIViewModel(cli);
        this.cli = cli;
        this.commandFactory = new CLICommandFactory();
        this.game = new GameController(viewModel);
        this.cli.commandEvent.subscribe(this);
    }

    @Override
    public void onReceived(Object sender, Command args) {
        IGameCommand command = this.commandFactory.CreateCommand(args.getCommandString(),game.GetPlayer());
        game.HandleCommand(command);

    }
}
