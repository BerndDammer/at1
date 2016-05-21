package as.backend;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import as.functionchain.IC_FunctionChainElement;
import as.interim.message.IL_Receiver;
import as.interim.message.MessageBase;
import as.interim.message.MessagePlatformSelect;
import as.starter.LoggingInit;
import as.starter.StaticConst;
import as.starter.StaticStarter;

public class FCPlatformSelector extends Thread implements IC_FunctionChainElement, IL_Receiver
{
    private final Logger logger = LoggingInit.get( this );
    private final List<String> names = new LinkedList<>();

    private enum SOUND_PLATFORM
    {
        JAVASOUND,
        ASIO;
    }

    private final MessagePlatformSelect receivePlatformSelect = new MessagePlatformSelect();
    private final MessagePlatformSelect transmittPlatformSelect = new MessagePlatformSelect();

    public FCPlatformSelector()
    {
        super( FCPlatformSelector.class.getCanonicalName() );

        for( SOUND_PLATFORM sp : SOUND_PLATFORM.values())
        {
            names.add( sp.name() );
        }

        StaticStarter.getServerPort().register( receivePlatformSelect, this );

        start();
    }

    @Override
    public void run()
    {
        while (true)
        {
            process();
        }
    }

    private synchronized void process()
    {
        try
        {
            wait(3000);
            
        }
        catch (InterruptedException e)
        {
        }
    }

    @Override
    public synchronized void receive( MessageBase message )
    {
        if (StaticConst.LOG_INTERIM)
            logger.info( "Command to platform selector"  );
        transmittPlatformSelect.names = names;
        transmittPlatformSelect.cmd = MessagePlatformSelect.CMD.SHOW_LIST;
        StaticStarter.getServerPort().publish( transmittPlatformSelect );
    }
}
