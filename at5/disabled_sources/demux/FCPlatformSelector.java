package as.backend;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import as.functionchain.IC_FunctionChainElement;
import as.interim.message.DemuxCall;
import as.interim.message.IL_MessageBaseReceiver;
import as.interim.message.IL_Receiver;
import as.interim.message.MessageBase;
import as.interim.message.MessagePlatformSelect;
import as.persistent.IC_SubTreeBase;
import as.persistent.PersistentCentral;
import as.starter.LoggingInit;
import as.starter.StaticConst;
import as.starter.StaticStarter;

public class FCPlatformSelector extends Thread
        implements IC_FunctionChainElement, IL_Receiver, IL_MessageBaseReceiver<MessagePlatformSelect>
{
    private enum SOUND_PLATFORM
    {
        JAVASOUND, ASIO, ALSA, JACK;
    }

    private final Logger logger = LoggingInit.get( this );
    private final List<String> names = new LinkedList<>();
    private final String[] sfnames;
    private SOUND_PLATFORM selected = null;

    private final MessagePlatformSelect receivePlatformSelect = new MessagePlatformSelect();
    private final MessagePlatformSelect transmittPlatformSelect = new MessagePlatformSelect();
    private final IC_SubTreeBase para;

    public FCPlatformSelector()
    {
        super( FCPlatformSelector.class.getCanonicalName() );

        for (SOUND_PLATFORM sp : SOUND_PLATFORM.values())
        {
            names.add( sp.name() );
        }
        sfnames = new String[names.size()];
        for (int i = 0; i < sfnames.length; i++)
        {
            sfnames[i] = names.get( i );
        }
        StaticStarter.getServerPort().register( receivePlatformSelect, this );
        para = PersistentCentral.subPlatformSelector();
        if (para.containsKey( "Platform" ))
        {
            try
            {
                selected = SOUND_PLATFORM.valueOf( (String) para.get( "Platform" ) );
            }
            catch (Exception e)
            {
                logger.warning( "Erroro reading platform" );
            }
        }
        if (selected == null)
            selected = SOUND_PLATFORM.JAVASOUND;
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
            wait( 3000 );
        }
        catch (InterruptedException e)
        {
        }
    }

    @Override
    public void receiveMessage( MessagePlatformSelect mps )
    {
        switch (mps.cmd)
        {
            case ANSWER_LIST:
                break;
            case REQUEST_LIST:
                // transmittPlatformSelect.names = sfnames;
                transmittPlatformSelect.names = names;
                transmittPlatformSelect.selected = selected.name();
                transmittPlatformSelect.cmd = MessagePlatformSelect.CMD.ANSWER_LIST;
                StaticStarter.getServerPort().publish( transmittPlatformSelect );
                break;
            case SET:
                try
                {
                    selected = SOUND_PLATFORM.valueOf( mps.selected );
                }
                catch (Exception e)
                {
                    logger.warning( "Error reading platform" );
                    selected = SOUND_PLATFORM.JAVASOUND;
                }

                para.clear();
                para.put( "Platform", mps.selected );
                para.flush();
                break;
            default:
                break;
        }
    }

    @Override
    public synchronized void receive( MessageBase mb )
    {
        if (StaticConst.LOG_INTERIM)
            logger.info( "Command to platform selector" );
        if (mb instanceof MessagePlatformSelect)
        {
            MessagePlatformSelect mps = (MessagePlatformSelect) mb;
            receiveMessage( mps );
        }
        else
        {
        }
        DemuxCall.doTheDemuxCall( this, mb );
    }
}
