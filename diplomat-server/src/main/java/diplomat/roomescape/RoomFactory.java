package diplomat.roomescape;

import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.actors.Door;
import diplomat.roomescape.gameobjects.actors.Key;
import diplomat.roomescape.gameobjects.actors.Room;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rachelcabot on 02/10/2016.
 */
public class RoomFactory {

    public final HashMap<String, Class<? extends AGameObject>> objectStringsToGameObjectClass = new HashMap<String, Class<? extends AGameObject>>(){{
        put("key",Key.class);
        put("door",Door.class);
    }};

    public Room CreateRoom(String fileName){
        ArrayList<AGameObject> objects = new ArrayList<>();
        InputStream is = RoomFactory.class.getResourceAsStream(fileName);
        String fileString = null;
        try {
            fileString = IOUtils.toString(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] objectStrings = fileString.split("\n");
        for (String objectString : objectStrings) {
            try {
                AGameObject object = objectStringsToGameObjectClass.get(objectString).newInstance();
                objects.add(object);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return new Room(objects);
    }
}
