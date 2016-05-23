package as.interim.message;

import java.io.Serializable;

public class MessageIdentityDisk4 implements Serializable, Comparable
{
    private static final long serialVersionUID = 1L;

    private final Long id;
    public MessageIdentityDisk4(MessageBase mb)
    {
        id = mb.getID();
    }
    @Override
    public boolean equals(Object o)
    {
        boolean result;
        result = ((MessageIdentityDisk4)o).id.longValue() == id.longValue();
        return result;
    }
    public boolean equals(MessageIdentityDisk4 mid)
    {
        boolean result;
        result = this.id.longValue() == mid.id.longValue();
        return result;
    }
    @Override
    public int compareTo( Object o )
    {
        MessageIdentityDisk4 md = (MessageIdentityDisk4)o;
        return id.compareTo( md.id );
    }
}
