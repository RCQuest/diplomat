package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.AObtainable;
import diplomat.roomescape.gameobjects.IExaminable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Room extends AGameObject implements IExaminable {
    private ArrayList<AGameObject> objects;

    public Room(ArrayList<AGameObject> objects) {
        super("room");
        this.objects = objects;
    }

    @Override
    public String Describe() {
        return "It's a room. The following objects reside in the room: "
                +GetListOfDescribableObjects().toString();
    }

    public ArrayList<AGameObject> GetListOfDescribableObjects(){
        return new ArrayList<> (objects.stream().filter(object -> {
            if(AObtainable.class.isInstance(object))
                if(((AObtainable)object).isObtained())
                    return false;
            if(Inventory.class.isInstance(object))
                return false;
            return true;
        }).collect(Collectors.toList()));
    }

    public AGameObject GetRoomObject(String name) {
        return objects.stream()
            .filter(x -> x.GetName().equals(name))
            .findFirst()
            .get();
    }

    public ArrayList<AGameObject> GetAllRoomObjects() {
        ArrayList<AGameObject> allObjects = new ArrayList<AGameObject>(objects);
        allObjects.add(this);
        return allObjects;
    }

    public void AddObject(AGameObject gameObject) {
        objects.add(gameObject);
        System.out.println(gameObject);
    }
}
