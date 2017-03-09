package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.IExaminable;
import diplomat.roomescape.gameobjects.IUsableTarget;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 09/03/2017.
 */
public class Tube extends AGameObject implements IExaminable, IUsableTarget {
    private ArrayList<AGameObject> insertedObjects;
    private AGameObject floatingObject;
    private float baseWaterLevel;


    public Tube(AGameObject floatingObject, float baseWaterLevel) {
        super("tube");
        this.floatingObject = floatingObject;
        this.baseWaterLevel = baseWaterLevel;
        this.insertedObjects = new ArrayList<AGameObject>();
    }

    @Override
    public String Describe() {
        return "It's an upright, hollow tube fixed to the floor. "
                +WaterLevelDescription()
                +FloatingObjectDescription(WaterLevel()!=0.0);
    }

    private String FloatingObjectDescription(boolean hasWater) {
        if(hasWater){
            return "There's a "+floatingObject.GetName()+" floating in the water.";
        }
        return "There's a "+floatingObject.GetName()+" in it.";
    }

    private String WaterLevelDescription() {
        if(WaterLevel()==0.0)
            return "";
        else if(WaterLevel()>0.9)
            return "There's water in the tube. The water level is at the top of the tube. ";
        else if(WaterLevel()>0.5)
            return "There's water in the tube. The water reaches over halfway up the tube. ";
        else if(WaterLevel()>0.0)
            return "There's water in the tube. The water level is low. ";

        return "";

    }

    public double WaterLevel() {
        System.out.println(((float)insertedObjects.size())/10f);
        System.out.println(baseWaterLevel);
        return baseWaterLevel+((float)insertedObjects.size())/10f;
    }

    public void InsertObject(AGameObject stone, Player player) {
        insertedObjects.add(stone);
        if(WaterLevel()>0.9f) {
            if(!player.ObjectIsInRoom(floatingObject)){
                player.AddToRoom(floatingObject);

            }

        }
    }

    public void UnInsertObject(AGameObject stone, Player player) {
        insertedObjects.remove(stone);
        if(WaterLevel()<1.0f) {
            if(player.ObjectIsInRoom(floatingObject)) {
                player.RemoveFromRoom(floatingObject);
            }
        }
    }
}
