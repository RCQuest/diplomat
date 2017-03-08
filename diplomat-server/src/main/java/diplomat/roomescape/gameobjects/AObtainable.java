package diplomat.roomescape.gameobjects;

import diplomat.roomescape.gameobjects.actors.Player;

/**
 * Created by Rachel on 21/09/2016.
 */
public abstract class AObtainable extends AGameObject {
    private boolean isObtained;

    public AObtainable(String name) {
        super(name);
        isObtained = false;
    }

    public abstract String GetObtainedDescription();

    public boolean isObtained() {
        return isObtained;
    }

    public void setObtained(boolean obtained) {
        isObtained = obtained;
    }

    public boolean PickupSelf(Player player) {
        return player.GetInventory().AddToInventory(this);
    }

    public void UnPickupSelf(Player player) {
        player.GetInventory().Discard(this);
    }
}
