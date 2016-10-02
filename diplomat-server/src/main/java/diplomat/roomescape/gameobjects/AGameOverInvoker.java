package diplomat.roomescape.gameobjects;

import diplomat.roomescape.Callback;
import diplomat.roomescape.gameobjects.AGameObject;

/**
 * Created by rachelcabot on 01/10/2016.
 */
public class AGameOverInvoker extends AGameObject {

    protected Callback gameOverCallback;

    public AGameOverInvoker(String name) {
        super(name);
    }

    public void SetGameOverCallback(Callback cb){
        this.gameOverCallback = cb;
    }
}
