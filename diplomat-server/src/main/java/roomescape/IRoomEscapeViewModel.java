package roomescape;

import roomescape.gameobjects.IExaminable;
import roomescape.gameobjects.IStandaloneUsable;
import roomescape.gameobjects.IUsable;
import roomescape.gameobjects.IUsableTarget;
import roomescape.gameobjects.actors.Player;

/**
 * Created by Rachel on 21/09/2016.
 */
public interface IRoomEscapeViewModel {
    void ShowLookResult(IExaminable at);

    void ShowUseResult(IStandaloneUsable object);

    void ShowUseOnResult(IUsable object, IUsableTarget target);

    void ShowPickupResult(Player player);
}
