package diplomat.roomescape.gameobjects;

import diplomat.roomescape.IRoomEscapeViewModel;
import diplomat.roomescape.gameobjects.actors.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by rachelcabot on 04/03/2017.
 */
public class ObjectGroup extends AObtainable implements IExaminable,IStandaloneUsable,IUsable,IUsableTarget,IPlaceable,IPlaceableTarget,IBreakable,IOpenable {
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

    public boolean PerformGroupUseOn(IUsable useable, Player player) {
        for (AGameObject object : objects) {
            if(IUsableTarget.class.isInstance(object))
                useable.Use(((IUsableTarget) object),player);
        }
        return false;
    }

    public void PerformGroupUnUseOn(IUsable hairDrier, Player player) {
        for (AGameObject object : objects) {
            if(IUsableTarget.class.isInstance(object))
                hairDrier.UnUse(((IUsableTarget) object),player);
        }
    }

    public String GetUsageDescription(IUsable useable) {
        ArrayList<IUsableTarget> o = new ArrayList<>();
        for (AGameObject object : objects) {
            o.add((IUsableTarget)object);
        }
        return o.stream()
                .map(useable::GetUsageDescription)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public boolean BreakSelf(Player player) {
        for (AGameObject object : objects) {
            if(IBreakable.class.isInstance(object))
                ((IBreakable)object).BreakSelf(player);
        }
        return true;
    }

    @Override
    public String GetBreakDescription() {
        ArrayList<IBreakable> o = new ArrayList<>();
        for (AGameObject object : objects) {
            o.add((IBreakable)object);
        }
        return o.stream()
                .map(IBreakable::GetBreakDescription)
                .collect(Collectors.joining("\n"));
    }

    @Override
    public void UnBreakSelf(Player player) {
        for (AGameObject object : objects) {
            if(IBreakable.class.isInstance(object))
                ((IBreakable)object).UnBreakSelf(player);
        }
    }

    @Override
    public boolean Place(IPlaceableTarget target, Player player) {
        for (AGameObject object : objects) {
            if(IPlaceable.class.isInstance(object))
                ((IPlaceable)object).Place(target,player);
        }
        return true;
    }

    @Override
    public void UnPlace(IPlaceableTarget target, Player player) {
        for (AGameObject object : objects) {
            if(IPlaceable.class.isInstance(object))
                ((IPlaceable)object).UnPlace(target,player);
        }
    }

    @Override
    public String GetPlaceDescription(IPlaceableTarget target) {
        ArrayList<IPlaceable> o = new ArrayList<>();
        for (AGameObject object : objects) {
            o.add((IPlaceable)object);
        }
        return o.stream()
                .map(ob->ob.GetPlaceDescription(target))
                .collect(Collectors.joining("\n"));
    }

    @Override
    public boolean OpenSelf(Player player) {
        for (AGameObject object : objects) {
            if(IOpenable.class.isInstance(object))
                ((IOpenable)object).OpenSelf(player);
        }
        return true;
    }

    @Override
    public void UnOpenSelf(Player player) {
        for (AGameObject object : objects) {
            if(IOpenable.class.isInstance(object))
                ((IOpenable)object).UnOpenSelf(player);
        }
    }

    @Override
    public String GetOpenDescription() {
        ArrayList<IOpenable> o = new ArrayList<>();
        for (AGameObject object : objects) {
            o.add((IOpenable)object);
        }
        return o.stream()
                .map(IOpenable::GetOpenDescription)
                .collect(Collectors.joining("\n"));
    }
}
