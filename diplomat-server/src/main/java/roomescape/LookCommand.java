package roomescape;

/**
 * Created by Rachel on 21/09/2016.
 */
public class LookCommand implements IGameCommand {
    private ILookable at;

    public LookCommand(ILookable at) {
        this.at = at;
    }

    @Override
    public void Execute(Player player, IRoomEscapeViewModel viewModel) {
        viewModel.ShowLookResult(this.at);
    }
}
