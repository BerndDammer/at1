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
import as.interim.message.IL_Demultiplexer;
import as.interim.message.IL_Publish;
import as.interim.message.IL_Receiver;
import as.interim.message.MessageBase;
import as.interim.message.MessageIdentityDisk;
import as.starter.LoggingInit;
import as.starter.StaticConst;
import as.starter.StaticStarter;

public class ClientPort implements IL_Publish, IL_Demultiplexer
{
    private final Logger logger = LoggingInit.get( this );

    private final Map<MessageIdentityDisk, List<IL_Receiver>> receivers = new TreeMap<>();

    private final ClientPortTransmitter clientPortTransmitter;
    private final ClientPortReceiver clientPortReceiver;

    private class ClientPortTransmitter extends Thread
    {
        private final ByteBuffer bbOutgoing = ByteBuffer.allocate( StaticConst.BB_SIZE );
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
                try
                {
                    wait();
                }
                catch (InterruptedException e)
                {
                    logger.throwing( null, null, e );
                }
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
                try
                {
                    wait();
                }
                catch (InterruptedException e)
                {
                    logger.throwing( null, null, e );
                }
            bbOutgoing.flip();
            StaticStarter.getServerPort().incoming( bbOutgoing );
            logger.info( "Message downsend"  );
            bufferFull = false;
            notify();
        }

    }
    private class ClientPortReceiver extends Thread
    {
        private final ByteBuffer bbIncoming = ByteBuffer.allocate( StaticConst.BB_SIZE );
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
                try
                {
                    wait();
                }
                catch (InterruptedException e)
                {
                    logger.throwing( null, null, e );
                }
            bbIncoming.clear();
            bbIncoming.put( bb );
            bbIncoming.flip();
            bufferFull = true;
            notify();
        }

        synchronized private void process()
        {
            while (!bufferFull)
                try
                {
                    wait();
                }
                catch (InterruptedException e)
                {
                    logger.throwing( null, null, e );
                }

            bbisIncoming.setByteBuffer( bbIncoming );

            ObjectInputStream ois;
            try
            {
                ois = new ObjectInputStream( bbisIncoming );
                Object o = ois.readObject();
                ois.close();
                if (StaticConst.LOG_INTERIM)
                    logger.info( "read object : " + o.getClass().getCanonicalName() );
                bufferFull = false;
                notify();
                if( o instanceof MessageBase)
                {
                    MessageBase mb = (MessageBase)o;
                    MessageIdentityDisk md = mb.getMessageIdentityDisk(); 
                    if( receivers.containsKey( md ))
                    {
                        List<IL_Receiver> mrs = receivers.get( md );
                        for( IL_Receiver r : mrs)
                        {
                            r.receive( mb );
                        }
                    }
                    else
                    {
                        if (StaticConst.LOG_INTERIM)
                            logger.info( "message without receiver"  );
                    }
                    
                }
                else
                {
                    if (StaticConst.LOG_INTERIM)
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
    public void register( MessageBase message, IL_Receiver receiver )
    {
        MessageIdentityDisk md = message.getMessageIdentityDisk();
        if( receivers.containsKey( md ))
        {
            receivers.get( md ).add(receiver);
        }
        else
        {
            List<IL_Receiver> mrs = new LinkedList<>();
            mrs.add( receiver);
            receivers.put( md, mrs );
        }
    }
    //intentionally package private
    void incoming(ByteBuffer bb)
    {
        clientPortReceiver.incoming( bb );
    }
}
