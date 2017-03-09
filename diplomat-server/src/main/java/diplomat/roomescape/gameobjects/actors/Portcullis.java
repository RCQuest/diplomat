package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.*;

/**
 * Created by rachelcabot on 09/03/2017.
 */
public class Portcullis extends AGameOverInvoker implements IExaminable, IStandaloneUsable {
    private boolean shut;

    public Portcullis() {
        super("portcullis");
        this.shut = true;
    }

    @Override
    public String Describe() {
        if(shut){
            return "It's a metal gate which looks like it draws up into the ceiling. It looks very heavy. ";
        } else {
            return "The portcullis is open. ";
        }
    }

    @Override
    public boolean Use(Player player) {
        if(shut){
            return false;
        } else {
            gameOverCallback.Invoke();
            return true;
        }
    }

    @Override
    public String GetUsageDescription() {
        if(shut){
            return "You walk into the closed, metal, spike-laden portcullis." +
                    "That really hurt. ";
        } else {
            return "You walk through the portcullis safely. ";
        }
    }

    @Override
    public void UnUse() {
        //UH
    }

    public void Open() {
        shut = false;
    }

    public void Shut() {
        shut = true;
    }
}
