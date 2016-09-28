package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.IExaminable;

import java.util.List;

public class Room implements IExaminable {
    private List<AGameObject> objects;

    @Override
    public String Describe() {
        return null;
    }

    public AGameObject GetRoomObject(String name) {
        return objects.stream()
            .filter(x -> x.GetName().equals(name))
            .findFirst()
            .get();
    }
}
