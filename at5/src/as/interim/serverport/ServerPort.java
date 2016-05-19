package as.interim.serverport;

import java.nio.ByteBuffer;

import as.interim.message.IL_Publish;
import as.interim.message.MessageBase;

public class ServerPort implements IL_Publish,Runnable
{
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
            //try
            {
                //MessageBase m = getMQ().take();
            }
            //catch (InterruptedException e)
            {
                // ignore this
                //logger.severe("Interrupted");
            }
        }
    }

    @Override
    public void publish( MessageBase message )
    {
        // TODO Auto-generated method stub
        
    }

    public void incoming(ByteBuffer bb)
    {
        
    }
}
