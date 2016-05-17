package as.serverport;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import as.message.IF_Publish;
import as.message.MessageBase;

public class ServerPort implements IF_Publish
{
    private final BlockingQueue<MessageBase> mq = new LinkedBlockingQueue<>();
    private final Thread thread;

    public ServerPort()
    {
        thread = new Thread( this, "ServerPortTransmitter" );
        thread.start();
    }

    @Override
    public void run()
    {
        // TODO Auto-generated method stub
        while (true)
        {
            try
            {
                MessageBase m = getMQ().take();
            }
            catch (InterruptedException e)
            {
                // ignore this
                logger.severe("Interrupted");
            }
        }
    }


    @Override
    public BlockingQueue<MessageBase> getMQ()
    {
        return mq;
    }
}
