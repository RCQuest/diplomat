package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.AGameOverInvoker;
import diplomat.roomescape.gameobjects.IExaminable;
import diplomat.roomescape.gameobjects.IStandaloneUsable;
import diplomat.roomescape.gameobjects.IUsableTarget;

/**
 * Created by rachelcabot on 28/09/2016.
 */
public class Hatch extends AGameOverInvoker implements IUsableTarget, IExaminable {

    public Hatch() {
        super("hatch");
    }

    @Override
    public String Describe() {
        return "It's an opening in the ceiling.";
    }

    public void ClimbInto() {
        this.gameOverCallback.Invoke();
    }
}
