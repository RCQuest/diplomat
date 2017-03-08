package diplomat.roomescape.gameobjects;

import diplomat.roomescape.gameobjects.actors.Player;

/**
 * Created by rachelcabot on 08/03/2017.
 */
public interface IBreakable {
    boolean BreakSelf(Player player);

    String GetBreakDescription();

    void UnBreakSelf(Player player);
}
