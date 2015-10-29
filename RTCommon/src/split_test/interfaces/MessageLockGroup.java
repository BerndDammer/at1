package split_test.interfaces;

import java.io.Serializable;

import split_test.error;

public class MessageLockGroup extends MessageBase implements Cloneable, Serializable
{
    private static final long serialVersionUID = 1L;
    public LOCK_STATE lockState;
    public int lockNumber; //number on CAN bus

    @Override
    public MessageLockGroup clone()
    {
        MessageLockGroup result = null;
        try
        {
            result = (MessageLockGroup) super.clone();
        }
        catch (CloneNotSupportedException e)
        {
            error.exception(e);
        }
        return result;
    }

    public enum LOCK_STATE
    {
        // @formatter:off
        OPEN("Open", "unlocked"),
        DOOR("Door", "doorAccess"), 
        CLOSE("Close", "locked"),    
        ;
        // @formatter:on

        private final String name;
        private final String uplinkName;

        LOCK_STATE(String name, String uplinkName)
        {
            this.name = name;
            this.uplinkName = uplinkName;
        }

        public String getName()
        {
            return name;
        }

        public String getUplinkName()
        {
            return uplinkName;
        }

        public static LOCK_STATE fromUplinkName(String name)
        {
            LOCK_STATE result = null;
            for (LOCK_STATE test : values())
            {
                if (test.uplinkName.equals(name))
                    result = test;
            }
            return result;
        }
    }
}