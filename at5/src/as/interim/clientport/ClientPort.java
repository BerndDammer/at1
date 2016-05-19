package as.interim.clientport;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

import as.interim.ByteBufferOutputStream;
import as.interim.message.IL_Publish;
import as.interim.message.IL_Demultiplexer;
import as.interim.message.IL_Receiver;
import as.interim.message.MessageBase;
import as.starter.LoggingInit;
import as.starter.StaticConst;
import as.starter.StaticStarter;

public class ClientPort implements IL_Publish, IL_Demultiplexer
{
    private final Logger logger = LoggingInit.get( this );

    // private final BlockingQueue<MessageBase> mq = new LinkedBlockingQueue<>();
    // private final Thread thread;

    private final ByteBuffer bbIncomming = ByteBuffer.allocate( StaticConst.BB_SIZE );
    private final ByteBufferOutputStream bbosIncomming = new ByteBufferOutputStream();

    private final ClientPortTransmitter clientPortTransmitter;

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
            StaticStarter.getServerPort().incoming( bbOutgoing );
            logger.info( "Message downsend"  );
            bufferFull = false;
            notify();
        }

    }

    public ClientPort()
    {
        clientPortTransmitter = new ClientPortTransmitter();
    }

    // @Override
    // public void run()
    // {
    // // TODO Auto-generated method stub
    // while (true)
    // {
    // try
    // {
    // MessageBase m = getMQ().take();
    // }
    // catch (InterruptedException e)
    // {
    // logger.severe( "Interrupted" );
    // }
    // }
    // }

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
    }
}
