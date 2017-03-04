package diplomat.roomescape.gameobjects;

import diplomat.roomescape.gameobjects.actors.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rachelcabot on 04/03/2017.
 */
public class ObjectGroup extends AGameObject implements IExaminable,IStandaloneUsable,IUsable,IUsableTarget {
    private final AGameObject[] objects;

    public ObjectGroup(AGameObject[] objects) {
        super("group");
        System.out.println(Arrays.toString(objects));
        this.objects = objects;
        System.out.println("created object group");
    }

    @Override
    public String Describe() {
        ArrayList<IExaminable> examinables = new ArrayList<>();
        for (AGameObject object : objects) {
            examinables.add((IExaminable)object);
        }
        return examinables.stream()
                .map(IExaminable::Describe)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public boolean Use() {
        return false;
    }

    @Override
    public String GetUsageDescription() {
        return null;
    }

    @Override
    public void UnUse() {

    }

    @Override
    public boolean Use(IUsableTarget target, Player player) {
        return false;
    }

    @Override
    public String GetUsageDescription(IUsableTarget target) {
        return null;
    }

    @Override
    public void UnUse(IUsableTarget target, Player player) {

    }
}
