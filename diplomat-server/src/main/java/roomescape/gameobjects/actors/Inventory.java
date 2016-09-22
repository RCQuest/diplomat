package roomescape.gameobjects.actors;

import roomescape.gameobjects.IObtainable;

import java.util.List;

/**
 * Created by Rachel on 21/09/2016.
 */
public class Inventory {
    public List<IObtainable> obtainedObjects;

    public void AddToInventory(IObtainable object) {
        this.obtainedObjects.add(object);
    }
}
