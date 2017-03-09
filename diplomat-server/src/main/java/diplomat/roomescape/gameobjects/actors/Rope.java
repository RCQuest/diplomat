package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.IExaminable;
import diplomat.roomescape.gameobjects.IPlaceableTarget;
import diplomat.roomescape.gameobjects.IStandaloneUsable;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 09/03/2017.
 */
public class Rope extends AGameObject implements IExaminable, IPlaceableTarget,IStandaloneUsable {
    private ArrayList<Weight> weights;
    private boolean snapped;

    public Rope() {
        super("rope");
        this.weights = new ArrayList<Weight>();
        snapped = false;
    }

    @Override
    public String Describe() {
        return "You can see the frayed rope is connected to the portcullis via a series of pulleys. ";
    }

    @Override
    public boolean Use(Player player) {
        return true;
    }

    @Override
    public String GetUsageDescription() {
        return "You pull down on the rope with all your might, but you barely lift the portcullis.";
    }

    @Override
    public void UnUse() {

    }

    public void PlaceWeight(Weight weight, Player player){
        player.RemoveFromRoom(weight);
        this.weights.add(weight);
        if(isEnoughWeights() && !isTooManyWeights()){
            player.UnlockPortcullis();
        }else if(isTooManyWeights()){
            player.RemoveFromRoom(this);
            this.snapped = true;
        }
    }

    public void UnPlaceWeight(Weight weight, Player player){
        player.AddToRoom(weight);
        this.weights.remove(weight);
        if(!isEnoughWeights()){
            player.LockPortcullis();
        }
        if(snapped&&!isTooManyWeights()){
            if(!player.ObjectIsInRoom(this))
                player.AddToRoom(this);
            if(isEnoughWeights())
                player.UnlockPortcullis();
        }
    }

    public boolean isEnoughWeights() {
        return this.weights.size() > 4;
    }

    public boolean isTooManyWeights(){
        return this.weights.size() > 6;
    }
}
