package diplomat.roomescape;

import diplomat.roomescape.gameobjects.*;
import diplomat.roomescape.gameobjects.actors.Player;


public interface IRoomEscapeViewModel {
    void ShowLookResult(IExaminable at);

    void ShowUseResult(IStandaloneUsable object);

    void ShowUseOnResult(IUsable object, IUsableTarget target);

    void ShowPickupResult(AObtainable obtainable, Player player);

    void ShowGameComplete();

    void SetPlayer(Player player);
}
