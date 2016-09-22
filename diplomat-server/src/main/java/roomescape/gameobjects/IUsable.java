package roomescape.gameobjects;

import roomescape.gameobjects.actors.Player;

/**
 * Created by Rachel on 21/09/2016.
 */
public interface IUsable {
    void Use(IUsableTarget target, Player player);
}
