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

    void ShowHelp();

    void ShowUndoResult();

    void ShowRestartCode();

    void ShowPlaceOnResult(IPlaceable object, IPlaceableTarget target);

    void ShowBreakResult(IBreakable object);

    void ShowOpenResult(IOpenable object);

    void ShowGroupUseOnResult(IUsable object, ObjectGroup target);

    void ShowInvalidCommandMessage();
}
