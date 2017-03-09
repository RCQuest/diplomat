package diplomat.roomescape.gameobjects.actors;

import diplomat.roomescape.gameobjects.*;

/**
 * Created by rachelcabot on 08/03/2017.
 */
public class Crate extends AGameObject implements IExaminable, IBreakable, IOpenable {

    private AGameObject obscuredObject;
    private AObtainable containedObject;
    private Crate anotherCrate;
    private boolean opened;

    public Crate(AGameObject o) {
        super("crate");
        if(AObtainable.class.isInstance(o))
            this.containedObject =(AObtainable) o;
        else if(Crate.class.isInstance(o))
            this.anotherCrate = (Crate) o;
        else
            this.obscuredObject = o;
        this.opened = false;
    }

    @Override
    public String Describe() {
        String a;
        if(opened){
            a = "It's open. ";
        } else {
            a = "There might be something inside.";
        }
        return "It's a crate. "+a ;
    }

    @Override
    public boolean BreakSelf(Player player) {
        player.RemoveFromRoom(this);
        player.AddToRoom(GetObject());
        return true;
    }

    @Override
    public String GetBreakDescription() {
        return "You stomp the crate with all your might. " +
                "It splinters into pieces. " +
                "Amongst the rubble, there's a "+GetObject().GetName()+" inside.";
    }

    private AGameObject GetObject() {
        if(containedObject!=null)
            return containedObject;
        else if(anotherCrate!=null){
            return anotherCrate;
        }
        return obscuredObject;
    }

    @Override
    public void UnBreakSelf(Player player) {
        player.AddToRoom(this);
        player.RemoveFromRoom(GetObject());

    }

    @Override
    public boolean OpenSelf(Player player) {
        if((containedObject!=null || anotherCrate!=null) && !opened) {
            player.AddToRoom(GetObject());
            opened = true;
            return true;
        }
        return false;
    }

    @Override
    public void UnOpenSelf(Player player) {
        if((containedObject!=null || anotherCrate!=null))
            player.RemoveFromRoom(GetObject());
        opened = false;
    }

    @Override
    public String GetOpenDescription() {
        if((containedObject!=null || anotherCrate!=null)){
            return "You open the crate up. " +
                    "There's a "+GetObject().GetName();
        } else {
            return "You open the crate up. There's nothing in it.";
        }

    }
}
