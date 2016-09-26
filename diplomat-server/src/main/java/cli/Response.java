package cli;

import eventsystem.events.core.EventArgs;

public class Response{

    private String content;

    public Response(String content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }
}
