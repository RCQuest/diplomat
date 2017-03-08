package diplomat.roomescape.gameobjects;

import diplomat.roomescape.gameobjects.actors.Player;

/**
 * Created by rachelcabot on 08/03/2017.
 */
public interface IOpenable {
    boolean OpenSelf(Player player);

    void UnOpenSelf(Player player);
}
