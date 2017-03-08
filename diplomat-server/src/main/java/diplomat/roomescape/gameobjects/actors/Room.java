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
        String d = "You're in a room. There's ";
        d=d+GetDescribedList(GetListOfDescribableObjects());

        return d;
    }

    private String GetDescribedList(ArrayList<AGameObject> gameObjects) {
        String s ="";
        String vowels ="aeiou";
        for (int i = 0; i < gameObjects.size(); i++) {
            AGameObject go = gameObjects.get(i);
            String n = go.GetName();
            String connector = "";
            if(i==gameObjects.size()-1&&gameObjects.size()>1)
                connector="and ";
            if (vowels.indexOf(Character.toLowerCase(n.charAt(0))) != -1)
                connector = connector+ "an ";
            else
                connector =connector+ "a ";
            s = s + connector + n;
            if(i==gameObjects.size()-1)
                s = s + ".";
            else
                s=s+", ";
        }

        return s;

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

    public void RemoveObject(AGameObject obscuredItem) {
        objects.remove(obscuredItem);
        System.out.println(obscuredItem);
    }
}
