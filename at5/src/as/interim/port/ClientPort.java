package as.interim.port;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import as.interim.ByteBufferInputStream;
import as.interim.ByteBufferOutputStream;
import as.interim.message.DemuxCall;
import as.interim.message.IL_DemultiplexerMessage;
import as.interim.message.IL_MessageBaseReceiver;
import as.interim.message.IL_Publish;
import as.interim.message.MessageBase;
import as.interim.message.MessageIdentityDisk;
import as.starter.LoggingInit;
import as.starter.IC_StaticConst;
import as.starter.StaticStarter;
import javafx.application.Platform;

public class ClientPort implements IL_Publish, IL_DemultiplexerMessage
{
    private final Logger logger = LoggingInit.get( this );

    private class ClientPortTransmitter extends SmallWorker
    {
        private final ByteBuffer bbOutgoing = ByteBuffer.allocate( IC_StaticConst.BB_SIZE );
        private final ByteBufferOutputStream bbosOutgoing = new ByteBufferOutputStream();

        private boolean bufferFull = false;

        private ClientPortTransmitter()
        {
            super( "ClientPortTransmitter" );
            start();
        }

        @Override
        public void run()
        {
            // TODO Auto-generated method stub
            while (true)
            {
                process();
            }
        }

        /*
         * awt thead must convert because message can be changed outside
         * 
         */
        synchronized void publish( MessageBase message )
        {
            while (bufferFull)
                controlWait();
            bbOutgoing.clear();
            bbosOutgoing.setByteBuffer( bbOutgoing );
            ObjectOutputStream oos;
            try
            {
                oos = new ObjectOutputStream( bbosOutgoing );
                oos.writeObject( message );
                oos.close();
                logger.info( "Message Size : " + bbOutgoing.position() );
                bufferFull = true;
                notify();
            }
            catch (IOException e)
            {
                logger.throwing( null, null, e );
            }
        }

        synchronized private void process()
        {
            while (!bufferFull)
                defaultWait();
            bbOutgoing.flip();
            StaticStarter.getServerPort().incoming( bbOutgoing );
            logger.info( "Message downsend" );
            bufferFull = false;
            notify();
        }

    }

    private class ClientPortReceiver extends SmallWorker
    {
        private final ByteBuffer bbIncoming = ByteBuffer.allocate( IC_StaticConst.BB_SIZE );
        private final ByteBufferInputStream bbisIncoming = new ByteBufferInputStream();

        private boolean bufferFull = false;

        private ClientPortReceiver()
        {
            super( "ClientPortReceiver" );
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

        synchronized void incoming( ByteBuffer bb )
        {
            while (bufferFull)
                controlWait();
            bbIncoming.clear();
            bbIncoming.put( bb );
            bbIncoming.flip();
            bufferFull = true;
            notify();
        }

        synchronized private void process()
        {
            while (!bufferFull)
                defaultWait();

            bbisIncoming.setByteBuffer( bbIncoming );

            ObjectInputStream ois;
            try
            {
                ois = new ObjectInputStream( bbisIncoming );
                Object o = ois.readObject();
                ois.close();
                if (IC_StaticConst.LOG_INTERIM)
                    logger.info( "read object : " + o.getClass().getCanonicalName() );
                bufferFull = false;
                notify();
                if (o instanceof MessageBase)
                {
                    MessageBase mb = (MessageBase) o;
                    MessageIdentityDisk md = mb.getMessageIdentityDisk();
                    if (receivers.containsKey( md ))
                    {

                        List<IL_MessageBaseReceiver<? extends MessageBase>> mrs = receivers.get( md );
                        for (IL_MessageBaseReceiver<? extends MessageBase> r : mrs)
                        {
                            fxpusher.push( r, mb, true );
                        }
                    }
                    else
                    {
                        if (IC_StaticConst.LOG_INTERIM)
                            logger.info( "message without receiver" );
                    }

                }
                else
                {
                    if (IC_StaticConst.LOG_INTERIM)
                        logger.info( "read suspicious object : " + o.getClass().getCanonicalName() );
                }
            }
            catch (IOException e)
            {
                logger.throwing( null, null, e );
            }
            catch (ClassNotFoundException e)
            {
                logger.throwing( null, null, e );
            }
        }
    }

    private class FXPusher implements Runnable
    {
        // TODO optimize Multithreadding by copy message before handle
        // message copy
        // message from to byte buffer
        private IL_MessageBaseReceiver<? extends MessageBase> receiver = null;
        private MessageBase message = null;

        @Override
        public void run()
        {
            pushFX();
        }

        private synchronized void pushFX()
        {
            DemuxCall.doTheDemuxCall( receiver, message );
            message = null;
            notify();
        }

        private synchronized void push( IL_MessageBaseReceiver<? extends MessageBase> receiver, MessageBase act_msg,
                boolean mustWait )
        {
            this.receiver = receiver;
            this.message = act_msg;
            Platform.runLater( this );
            if (mustWait)
                while (this.message != null)
                    try
                    {
                        wait();
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
        }
    }

    private final Map<MessageIdentityDisk, List<IL_MessageBaseReceiver<? extends MessageBase>>> receivers = new TreeMap<MessageIdentityDisk, List<IL_MessageBaseReceiver<? extends MessageBase>>>();

    private final ClientPortTransmitter clientPortTransmitter;
    private final ClientPortReceiver clientPortReceiver;
    private final FXPusher fxpusher = new FXPusher();

    
    public ClientPort()
    {
        clientPortTransmitter = new ClientPortTransmitter();
        clientPortReceiver = new ClientPortReceiver();
    }

    /**********************
     * this is called by the awt thread
     * 
     * @see as.interim.message.IL_Publish#publish(as.interim.message.MessageBase)
     * 
     */
    public void publish( MessageBase message )
    {
        clientPortTransmitter.publish( message );
    }

    @Override
    public void register( MessageBase message, IL_MessageBaseReceiver<? extends MessageBase> receiver )
    {
        DemuxCall.scan( receiver );
        MessageIdentityDisk md = message.getMessageIdentityDisk();
        if (receivers.containsKey( md ))
        {
            receivers.get( md ).add( receiver );
        }
        else
        {
            List<IL_MessageBaseReceiver<? extends MessageBase>> mrs = new LinkedList<>();
            mrs.add( receiver );
            receivers.put( md, mrs );
        }
    }

    // intentionally package private
    void incoming( ByteBuffer bb )
    {
        clientPortReceiver.incoming( bb );
    }
}
