package roomescape.cli;

import cli.Command;
import cli.CommandController;
import eventsystem.events.core.IEventListener;
import roomescape.GameController;
import roomescape.commands.IGameCommand;

public class CLIGameBootstrap implements IEventListener<Command>{
    private GameController game;
    private CLICommandFactory commandFactory;
    private CommandController cli;

    public CLIGameBootstrap(CommandController cli){
        this.cli = cli;
        this.commandFactory = new CLICommandFactory();
    }

    @Override
    public void onReceived(Object sender, Command args) {
        IGameCommand command = this.commandFactory.CreateCommand(args.getCommandString(),game.GetPlayer());
    }
}
