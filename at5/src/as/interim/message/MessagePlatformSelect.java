package as.interim.message;

import java.util.List;

public class MessagePlatformSelect extends MessageBase
{
    private static final long serialVersionUID = -533735861149591462L;
    public List<String> names;
    public CMD cmd;
    public MessagePlatformSelect()
    {
    }
    
    public enum CMD
    {
        GET_LIST,
        SHOW_LIST;
    }
}
