package as.interim.message;

import java.io.Serializable;

public class MessageIdentityDisk implements Serializable, Comparable
{
    private static final long serialVersionUID = 1L;

    private final String idstring;
    public MessageIdentityDisk(MessageBase mb)
    {
        idstring = mb.getClass().getCanonicalName();
    }
    @Override
    public boolean equals(Object o)
    {
        boolean result;
        result = ((MessageIdentityDisk)o).idstring.equals( idstring );
        return result;
    }
    public boolean equals(MessageIdentityDisk id)
    {
        boolean result;
        result = id.idstring.equals( idstring );
        return result;
    }
    @Override
    public int compareTo( Object o )
    {
        MessageIdentityDisk md = (MessageIdentityDisk)o;
        return idstring.compareTo( md.idstring );
    }
}
