package as.interim.message;

import java.util.List;

public class MessageChannelSelect extends MessageBase
{
    /**
     * 
     */
    private static final long serialVersionUID = 4245033144303246502L;
    public List<String> names = null;
    public CMD cmd;
    public String selected;
    public MessageChannelSelect()
    {
        
    }
    
    public enum CMD
    {
        REQUEST_LIST,
        ANSWER_LIST,
        SET;
    }
}
