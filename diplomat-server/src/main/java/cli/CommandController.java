package cli;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class CommandController {

    @MessageMapping("/command")
    @SendTo("/topic/response")
    public Response respond(Command command) throws Exception {
        return new Response(
                "This is a response to the command "
                + command.getCommandString());
    }
}
