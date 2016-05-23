package as.interim.message;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

public class DemuxCall
{
    private static final Logger logger = Logger.getAnonymousLogger();
    public DemuxCall()
    {
    }

    public static void doTheDemuxCall( IL_MessageBaseReceiver<? extends MessageBase>receiver, MessageBase message ) 
    {
        try
        {
            Method receiveMethod;
//            receiveMethod = IL_MessageBaseReceiver.class.getMethod( "receiveMessage", MessageBase.class );
//            logger.info( receiveMethod.toString() );
//            receiveMethod = receiver.getClass().getMethod( "receiveMessage", MessageBase.class );
//            logger.info( receiveMethod.toString() );
            receiveMethod = receiver.getClass().getMethod( "receiveMessage", message.getClass() );
//            logger.info( receiveMethod.toString() );
            receiveMethod.invoke( message );
        }
        catch (NoSuchMethodException e)
        {
            logger.throwing( "DemuxCall", "doTheDemuxCall", e );
        }
        catch (SecurityException e)
        {
            logger.throwing( "DemuxCall", "doTheDemuxCall", e );
        }
        catch (IllegalAccessException e)
        {
            logger.throwing( "DemuxCall", "doTheDemuxCall", e );
        }
        catch (IllegalArgumentException e)
        {
            logger.throwing( "DemuxCall", "doTheDemuxCall", e );
        }
        catch (InvocationTargetException e)
        {
            logger.throwing( "DemuxCall", "doTheDemuxCall", e );
        }
    }
}
