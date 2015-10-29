package splicing_sleeve;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

import starter.CentralControl;

public class MonitorPortClient extends MonitorPort
{
	public MonitorPortClient()
	{
		super(false);
	}
	
	@Override
	protected void onHeartbeat(SocketAddress ssa, ByteBuffer rb, int length)
	{
		for (int i = 0; i < length - 4; i++)
		{
			rb.get();
		}
		if( ssa instanceof InetSocketAddress )
		{
			CentralControl.getServerView().newIP( ((InetSocketAddress)ssa).getAddress());
		}
		else
		{
			logger.warning("no inet socket");
		}
	}
}
