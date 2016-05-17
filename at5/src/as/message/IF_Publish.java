package as.message;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

public interface IF_Publish extends Runnable
{
    final static Logger logger = Logger.getAnonymousLogger();

    default void publish(MessageBase message)
    {
        try
        {
            getMQ().put(message);
        }
        catch (InterruptedException e)
        {
            logger.throwing( null, null, e );
        }
    }
    BlockingQueue<MessageBase> getMQ();
}
