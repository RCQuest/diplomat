package diplomat.roomescape.cli;

import diplomat.cli.CommandController;
import diplomat.roomescape.IRoomEscapeViewModel;
import diplomat.roomescape.gameobjects.IExaminable;
import diplomat.roomescape.gameobjects.IStandaloneUsable;
import diplomat.roomescape.gameobjects.IUsable;
import diplomat.roomescape.gameobjects.IUsableTarget;
import diplomat.roomescape.gameobjects.actors.Player;

public class CLIViewModel implements IRoomEscapeViewModel {
    private CommandController cli;

    public CLIViewModel(CommandController cli) {
        this.cli = cli;
    }

    @Override
    public void ShowLookResult(IExaminable at) {
        at = () -> "hello world!";
        cli.setResponse(at.Describe());
    }

    @Override
    public void ShowUseResult(IStandaloneUsable object) {

    }

    @Override
    public void ShowUseOnResult(IUsable object, IUsableTarget target) {

    }

    @Override
    public void ShowPickupResult(Player player) {

    }
}
