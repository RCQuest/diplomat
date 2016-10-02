package diplomat.roomescape.cli;

import diplomat.cli.CommandController;
import diplomat.roomescape.IRoomEscapeViewModel;
import diplomat.roomescape.gameobjects.*;
import diplomat.roomescape.gameobjects.actors.Player;

public class CLIViewModel implements IRoomEscapeViewModel {
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
}
