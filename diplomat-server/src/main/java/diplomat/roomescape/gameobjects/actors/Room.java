package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.IExaminable;

import java.util.List;

public class Room implements IExaminable {
    private List<AGameObject> objects;

    public Room(List<AGameObject> objects) {
        this.objects = objects;
        System.out.println("Room created");
    }

    @Override
    public String Describe() {
        return "It's a room";
    }

    public AGameObject GetRoomObject(String name) {
        return objects.stream()
            .filter(x -> x.GetName().equals(name))
            .findFirst()
            .get();
    }
}
