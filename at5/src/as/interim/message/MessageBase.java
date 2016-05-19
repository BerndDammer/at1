package as.interim.message;

import java.io.Serializable;

public class MessageBase implements Serializable, Cloneable
{
    private static final long serialVersionUID = 1L;
    //private final MessageType messageId = new MessageType(this); 
    // cannot be here because it will be serialized
    // private final IL_Receiver receiver; 
    
    public MessageBase()
    {
        //this.receiver = receiver;
    }
    public MessageType getMessageId()
    {
        //return messageId;
        return null;
    }
    public MessageBase clone()
    {
        try
        {
            return (MessageBase)super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            // TODO handle
            e.printStackTrace();
            return null;
        }
    }
}
