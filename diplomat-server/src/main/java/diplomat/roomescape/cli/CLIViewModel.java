package diplomat.roomescape.cli;

import diplomat.cli.CommandController;
import diplomat.roomescape.IRoomEscapeViewModel;
import diplomat.roomescape.gameobjects.*;
import diplomat.roomescape.gameobjects.actors.Player;

public class CLIViewModel implements IRoomEscapeViewModel {
    private static String helpText = "Possible commands:\n " +
            "look _\n " +
            "use _\n " +
            "use _ on _\n " +
            "pickup _ \n " +
            "every _\n " +
            "help";
    private static String undoneText = "undone";
    private static String startCode = "restarting";
    private CommandController cli;
    private Player player;

    public CLIViewModel(CommandController cli) {
        this.cli = cli;
    }

    @Override
    public void ShowLookResult(IExaminable at) {
        this.cli.AppendToResponse(at.Describe());
    }

    @Override
    public void ShowUseResult(IStandaloneUsable object) {
        this.cli.AppendToResponse(object.GetUsageDescription());
    }

    @Override
    public void ShowUseOnResult(IUsable object, IUsableTarget target) {
        this.cli.AppendToResponse(object.GetUsageDescription(target));
    }

    @Override
    public void ShowPickupResult(AObtainable obtainable, Player player) {
        this.cli.AppendToResponse(obtainable.GetObtainedDescription());
    }

    @Override
    public void ShowGameComplete() {
        this.cli.AppendToResponse("You have escaped the room! Congratulations.");
    }

    public void SetPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void ShowHelp() {
        this.cli.AppendToResponse(CLIViewModel.helpText);
    }

    @Override
    public void ShowUndoResult() {
        this.cli.AppendToResponse(CLIViewModel.undoneText);
    }

    @Override
    public void ShowRestartCode() {
        this.cli.AppendToResponse(CLIViewModel.startCode);
    }
}
