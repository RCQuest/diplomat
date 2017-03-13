package diplomat.roomescape.cli;

import diplomat.cli.CommandController;
import diplomat.roomescape.IRoomEscapeViewModel;
import diplomat.roomescape.gameobjects.*;
import diplomat.roomescape.gameobjects.actors.Player;

public class CLIViewModel implements IRoomEscapeViewModel {
    private static String helpText = "Commands:\n " +
            "look - describes the room\n" +
            "look (object) - describes the object\n " +
            "look inventory - shows what objects you are holding\n" +
            "use (object) - performs some action with the object\n " +
            "use (object) on (target) - applies the object to the target in some way\n " +
            "pickup (object) - puts an object in your inventory\n " +
            "every (query) - searches the room for all objects with names matching the query (used in the context of other commands)\n " +
            "place (object) on (target) - places or attaches an object to the target in some manner\n" +
            "break (object) - attempts to destroy the object\n" +
            "open (object) - opens a container of some kind\n" +
            "help - shows this list\n" +
            "undo - reverses the effect of the last command\n";
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
        this.cli.AppendToResponse("You have escaped the room! Congratulations.\n Type 'start' when you're ready to start the next room.");
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

    @Override
    public void ShowPlaceOnResult(IPlaceable object, IPlaceableTarget target) {
        this.cli.AppendToResponse(object.GetPlaceDescription(target));
    }

    @Override
    public void ShowBreakResult(IBreakable object) {
        this.cli.AppendToResponse(object.GetBreakDescription());
    }

    @Override
    public void ShowOpenResult(IOpenable object) {
        this.cli.AppendToResponse(object.GetOpenDescription());
    }

    @Override
    public void ShowGroupUseOnResult(IUsable object, ObjectGroup target) {
        this.cli.AppendToResponse(target.GetUsageDescription(object));
    }
}
