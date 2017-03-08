package diplomat.roomescape.gameobjects;

import diplomat.roomescape.IRoomEscapeViewModel;
import diplomat.roomescape.gameobjects.actors.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by rachelcabot on 04/03/2017.
 */
public class ObjectGroup extends AObtainable implements IExaminable,IStandaloneUsable,IUsable,IUsableTarget {
    private final AGameObject[] objects;

    public ObjectGroup(AGameObject[] objects) {
        super("group");
        System.out.println(Arrays.toString(objects));
        this.objects = objects;
//        System.out.println("created object group");
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
    public boolean Use(Player player) {
        ArrayList<IStandaloneUsable> o = new ArrayList<>();
        for (AGameObject object : objects) {
            o.add((IStandaloneUsable)object);
        }
        return o.stream()
                .map((iStandaloneUsable) -> iStandaloneUsable.Use(player))
                .reduce(true, (a, b) -> a && b);
    }

    @Override
    public String GetUsageDescription() {
        ArrayList<IStandaloneUsable> o = new ArrayList<>();
        for (AGameObject object : objects) {
            o.add((IStandaloneUsable)object);
        }
        return o.stream()
                .map(IStandaloneUsable::GetUsageDescription)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public void UnUse() {
        for (AGameObject object : objects) {
            ((IStandaloneUsable)object).UnUse();
        }
    }

    @Override
    public boolean Use(IUsableTarget target, Player player) {
        ArrayList<IUsable> o = new ArrayList<>();
        for (AGameObject object : objects) {
            o.add((IUsable)object);
        }
        return o.stream()
                .map(u -> u.Use(target,player))
                .reduce(true, (a, b) -> a && b);
    }

    @Override
    public String GetUsageDescription(IUsableTarget target) {
        ArrayList<IUsable> o = new ArrayList<>();
        for (AGameObject object : objects) {
            o.add((IUsable)object);
        }
        return o.stream()
                .map(u -> u.GetUsageDescription(target))
                .collect(Collectors.joining("\n"));
    }

    @Override
    public void UnUse(IUsableTarget target, Player player) {
        for (AGameObject object : objects) {
            ((IUsable)object).UnUse(target, player);
        }
    }

    @Override
    public String GetObtainedDescription() {
        ArrayList<AObtainable> o = new ArrayList<>();
        for (AGameObject object : objects) {
            o.add((AObtainable)object);
        }
        return o.stream()
                .map(AObtainable::GetObtainedDescription)
                .collect(Collectors.joining("\n"));
    }

    public boolean PerformGroupPickup(Player player, IRoomEscapeViewModel viewModel) {
        boolean didNotHaveItemWhenPerformed = true;
        for (AGameObject object : objects) {
            viewModel.ShowPickupResult((AObtainable)object, player);
            didNotHaveItemWhenPerformed = didNotHaveItemWhenPerformed && player.Pickup((AObtainable)object);
        }
        return didNotHaveItemWhenPerformed;
    }

    public void PerformGroupPutDown(Player player) {
        for (AGameObject object : objects) {
            player.UnPickup((AObtainable) object);
        }
    }
}
