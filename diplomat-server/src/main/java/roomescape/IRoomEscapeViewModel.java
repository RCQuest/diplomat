package roomescape;

import roomescape.gameobjects.IExaminable;
import roomescape.gameobjects.IStandaloneUsable;
import roomescape.gameobjects.IUsable;
import roomescape.gameobjects.IUsableTarget;
import roomescape.gameobjects.actors.Player;


public interface IRoomEscapeViewModel {
    void ShowLookResult(IExaminable at);

    void ShowUseResult(IStandaloneUsable object);

    void ShowUseOnResult(IUsable object, IUsableTarget target);

    void ShowPickupResult(Player player);
}
