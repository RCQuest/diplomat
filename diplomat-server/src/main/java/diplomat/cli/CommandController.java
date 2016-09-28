package diplomat.cli;

import diplomat.eventsystem.events.core.Event;
import diplomat.eventsystem.events.core.EventSystem;
import diplomat.eventsystem.events.core.IWithEvents;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Controller
public class CommandController implements IWithEvents{

    public final Event<Command> commandEvent = EventSystem.newEvent(this);
    private Response currentResponse;

    @MessageMapping("/command")
    @SendTo("/topic/response")
    public Response receiveCommand(Command command) throws Exception {
        currentResponse = new Response("");
        System.out.println(command.getCommandString());
        commandEvent.sendEvent(this,command);
        return currentResponse;
    }

    public void setResponse(String response){
        System.out.println(response);
        currentResponse = new Response(response);
    }

}
