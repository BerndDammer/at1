package as.interim.message;

import java.io.Serializable;

public class MessageIdentityDisk3 implements Serializable, Comparable
{
    private static final long serialVersionUID = 1L;

    private final String idstring;
    public MessageIdentityDisk3(MessageBase mb)
    {
        idstring = mb.getClass().getCanonicalName();
    }
    @Override
    public boolean equals(Object o)
    {
        boolean result;
        result = ((MessageIdentityDisk3)o).idstring.equals( idstring );
        return result;
    }
    public boolean equals(MessageIdentityDisk3 id)
    {
        boolean result;
        result = id.idstring.equals( idstring );
        return result;
    }
    @Override
    public int compareTo( Object o )
    {
        MessageIdentityDisk3 md = (MessageIdentityDisk3)o;
        return idstring.compareTo( md.idstring );
    }
}
