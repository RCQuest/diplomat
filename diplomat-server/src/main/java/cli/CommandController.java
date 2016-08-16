package cli;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.io.InputStreamReader;

@Controller
public class CommandController {

    @MessageMapping("/command")
    @SendTo("/topic/response")
    public Response respond(Command command) throws Exception {
        String response = "";
        try {
            // Get runtime
            Runtime rt = Runtime.getRuntime();
            // Start a new process: UNIX command ls
            Process p = rt.exec(command.getCommandString());
            // wait for it
            p.waitFor();

            // Get process' output: its InputStream
            java.io.InputStream is = p.getInputStream();
            java.io.BufferedReader reader = new java.io.BufferedReader(new InputStreamReader(is));
            // And print each line
            String s;

            while ((s = reader.readLine()) != null) {
                response += (s + "\n");
            }
            is.close();

            System.out.println("Process exited with code = " + p.exitValue());
        } catch(IOException e) {
            response = e.getLocalizedMessage();
        }


        return new Response(response);
    }
}
