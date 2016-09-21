package roomescape;

import java.util.List;

public class Room implements ILookable {
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
