package diplomat.eventsystem.main;

import diplomat.eventsystem.events.ObjStateEventArgs;
import diplomat.eventsystem.events.core.EventSystem;
import diplomat.eventsystem.disposable.IDisposable;
import diplomat.eventsystem.events.core.EventArgs;
import diplomat.eventsystem.events.core.Event;
import diplomat.eventsystem.events.core.IWithEvents;
import diplomat.eventsystem.events.core.SimpleEvent;

/**
 *
 * @author nerobot
 */

public class MyObject implements IDisposable, IWithEvents {

    private ObjState state = ObjState.Undefined;
    private String name;
    // публичный обработчик события, на который можно подписаться
    public final Event<ObjStateEventArgs> eventStateChanged = EventSystem.newEvent(this);
    public final SimpleEvent eventDie = EventSystem.newSimpleEvent(this);

    public MyObject(String name) {
        this.name = name;
    }

    public void freeze() {
        setState(ObjState.Freezed);
    }

    private void setState(ObjState state) {
        if (this.state == state) return;
        this.state = state;
        // проверяем подписчиков, чтобы зря не создавать объект с аргументами
        if (eventStateChanged.hasListeners()) {
            eventStateChanged.sendEvent(this, new ObjStateEventArgs(state));
        }
    }

    public void makeDead() {
        setState(ObjState.Dead);
        if (eventDie.hasListeners()) {
            // нам важен сам факт события, параметры не нужны - empty
            eventDie.sendEvent(this, EventArgs.Empty);
        }
        dispose();
    }

    @Override
    public void dispose() {
        EventSystem.remove(this);
    }

    @Override
    public String toString() {
        return "object["+name+"]";
    }
}
