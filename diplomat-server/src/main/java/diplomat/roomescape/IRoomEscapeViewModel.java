package diplomat.roomescape;

import diplomat.roomescape.gameobjects.IExaminable;
import diplomat.roomescape.gameobjects.IStandaloneUsable;
import diplomat.roomescape.gameobjects.IUsable;
import diplomat.roomescape.gameobjects.IUsableTarget;
import diplomat.roomescape.gameobjects.actors.Player;


public interface IRoomEscapeViewModel {
    void ShowLookResult(IExaminable at);

    void ShowUseResult(IStandaloneUsable object);

    void ShowUseOnResult(IUsable object, IUsableTarget target);

    void ShowPickupResult(Player player);
}
