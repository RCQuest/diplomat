package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.AGameOverInvoker;
import diplomat.roomescape.gameobjects.IStandaloneUsable;

/**
 * Created by rachelcabot on 08/03/2017.
 */
public class Trapdoor extends AGameOverInvoker implements IStandaloneUsable {
    public Trapdoor() {
        super("trapdoor");
    }

    @Override
    public boolean Use(Player player) {
        player.InvokeGameOver();
        return true;
    }

    @Override
    public String GetUsageDescription() {
        return "You jump down the trapdoor to freedom!";
    }

    @Override
    public void UnUse() {
        //shouldn't happen
    }
}
