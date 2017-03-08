package diplomat.roomescape.gameobjects;

import diplomat.roomescape.gameobjects.actors.Player;

/**
 * Created by rachelcabot on 08/03/2017.
 */
public interface IPlaceable {
    boolean Place(IPlaceableTarget target, Player player);

    void UnPlace(IPlaceableTarget target, Player player);

    String GetPlaceDescription(IPlaceableTarget target);
}
