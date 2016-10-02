package diplomat.roomescape.gameobjects;

public abstract class AGameObject {
    private String name;

    public AGameObject(String name) {
        this.name = name;
    }

    public String GetName() {
        return name;
    }

    @Override
    public String toString(){
        return name;
    }
}
