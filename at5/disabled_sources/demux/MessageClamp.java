package as.interim.message;

import java.util.logging.Logger;

import as.starter.LoggingInit;
import javafx.application.Platform;

public class MessageClamp implements IL_Receiver, LC_MessageOfferer
{
    private final Logger logger = LoggingInit.get(this);
    
    private MessageBase message = null;
    private final Runnable finalReceiver;
    public MessageClamp(Runnable finalReceiver)
    {
        this.finalReceiver = finalReceiver;
    }
    // for non awt side 
    @Override
    public synchronized void receive(MessageBase message)
    {
        if( this.message != null)
        {
            logger.severe("Clamp full");
        }
        this.message = message;
        Platform.runLater( finalReceiver );
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
    @Override
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
    @Override
    public synchronized void release()
    {
        this.message = null;
        notify();
    }
}
