package as.interim.message;

import java.util.logging.Logger;

import as.starter.LoggingInit;

public class MessageClamp
{
    private final Logger logger = LoggingInit.get(this);
    
    private MessageBase message = null;

    // for non awt side 
    public synchronized void stall(MessageBase message)
    {
        if( this.message != null)
        {
            logger.severe("Clamp full");
        }
        this.message = message;
        notify();
        while( this.message != null)
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                logger.throwing(null, null, e);
            }
        }
    }
    // for awt side
    public synchronized MessageBase aquire()
    {
        while( this.message == null)
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                logger.throwing(null, null, e);
            }
        }
        return message;
    }
    public synchronized void release()
    {
        this.message = null;
        notify();
    }
}
