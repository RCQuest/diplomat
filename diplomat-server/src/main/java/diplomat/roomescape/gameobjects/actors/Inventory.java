package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.IExaminable;
import diplomat.roomescape.gameobjects.AObtainable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Rachel on 21/09/2016.
 */
public class Inventory extends AGameObject implements IExaminable {

    public ArrayList<AObtainable> obtainedObjects;

    public Inventory(){
        super("inventory");
        this.obtainedObjects = new ArrayList<>();
    }

    public void AddToInventory(AObtainable object) {
        if(!object.isObtained()) this.obtainedObjects.add(object);
        object.setObtained(true);
    }

    public void Discard(AObtainable obj) {
        obtainedObjects.remove(obj);
        obj.setObtained(false);
    }

    public ArrayList<AGameObject> GetInventoryObjects() {
        List<AGameObject> objects = obtainedObjects.stream()
                .map(object -> (AGameObject)object)
                .collect(Collectors.toList());
        return (ArrayList<AGameObject>) objects;
    }

    @Override
    public String Describe() {
        String description;
        if(obtainedObjects.isEmpty())
            description = "You have no inventory items.";
        else
            description = "You have the following items in your inventory: "+toString();
        return description;
    }

    @Override
    public String toString(){
        return obtainedObjects.toString();
    }
}
