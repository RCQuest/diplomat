package cli;

import eventsystem.events.core.Event;
import eventsystem.events.core.EventSystem;
import eventsystem.events.core.IEventListener;
import eventsystem.events.core.IWithEvents;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Controller
public class CommandController implements IWithEvents{

    public final Event<Command> commandEvent = EventSystem.newEvent(this);

    @MessageMapping("/command")
    public void receiveCommand(Command command) throws Exception {
        System.out.println(command.getCommandString());
        commandEvent.sendEvent(this,command);
    }

    @SendTo("/topic/response")
    public Response respond(String response){
        return new Response(response);
    }

}
