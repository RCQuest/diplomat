package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.AGameObject;
import diplomat.roomescape.gameobjects.IExaminable;
import diplomat.roomescape.gameobjects.IPlaceableTarget;
import diplomat.roomescape.gameobjects.IStandaloneUsable;

import java.util.ArrayList;

/**
 * Created by rachelcabot on 09/03/2017.
 */
public class Chain extends AGameObject implements IExaminable, IPlaceableTarget,IStandaloneUsable {
    private ArrayList<Weight> weights;

    public Chain() {
        super("chain");
        this.weights = new ArrayList<Weight>();
    }

    @Override
    public String Describe() {
        return "You can see the metal chain is connected to the portcullis via a series of pulleys. ";
    }

    @Override
    public boolean Use(Player player) {
        return true;
    }

    @Override
    public String GetUsageDescription() {
        return "You pull down on the chain with all your might, but you barely lift the portcullis.";
    }

    @Override
    public void UnUse() {

    }

    public void PlaceWeight(Weight weight, Player player){
        player.RemoveFromRoom(weight);
        this.weights.add(weight);
        if(isEnoughWeights()){
            player.UnlockPortcullis();
        }
    }

    public void UnPlaceWeight(Weight weight, Player player){
        player.AddToRoom(weight);
        this.weights.remove(weight);
        if(!isEnoughWeights()){
            player.LockPortcullis();
        }
    }

    public boolean isEnoughWeights() {
        return this.weights.size() > 4;
    }
}
