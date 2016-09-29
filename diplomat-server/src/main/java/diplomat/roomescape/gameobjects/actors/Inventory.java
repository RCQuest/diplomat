package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.IObtainable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Rachel on 21/09/2016.
 */
public class Inventory {

    public ArrayList<IObtainable> obtainedObjects;

    public Inventory(){
        this.obtainedObjects = new ArrayList<>();
    }

    public void AddToInventory(IObtainable object) {
        this.obtainedObjects.add(object);
    }

    public void Discard(IObtainable obj) {
        obtainedObjects.remove(obj);
    }

    public ArrayList<AGameObject> GetInventoryObjects() {
        List<AGameObject> objects = obtainedObjects.stream()
                .map(object -> (AGameObject)object)
                .collect(Collectors.toList());
        return (ArrayList<AGameObject>) objects;
    }
}
