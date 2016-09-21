package roomescape;

/**
 * Created by Rachel on 21/09/2016.
 */
public interface IGameCommand {
    void Execute(Player player, IRoomEscapeViewModel viewModel);
}
