package as.interim.message;

import java.io.Serializable;

public class MessageType implements Serializable
{
    private final String idstring;
    public MessageType(MessageBase mb)
    {
        idstring = mb.getClass().getCanonicalName();
    }
    @Override
    public boolean equals(Object o)
    {
        boolean result;
        result = ((MessageType)o).idstring.equals( idstring );
        return result;
    }
    public boolean equals(MessageType id)
    {
        boolean result;
        result = id.idstring.equals( idstring );
        return result;
    }
}
