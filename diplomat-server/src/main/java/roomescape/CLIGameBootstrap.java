package roomescape;

/**
 * Created by Rachel on 21/09/2016.
 */
public class CLIGameBootstrap {
    private GameController game;
    private CLICommandFactory commandFactory;

    public CLIGameBootstrap(){
        this.commandFactory = new CLICommandFactory();
    }
}
