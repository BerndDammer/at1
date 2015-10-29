package splicing_sleeve;

import generals.ATConstants;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import starter.error;

public class MonitorPort implements Runnable
{
	protected static final Logger logger = Logger.getAnonymousLogger();

	private final long startTime;
	private long nextTimeout;

	private final Thread thisWorker;

	// ---------------------------------- 4 networking
	private DatagramChannel downIn;
	private DatagramChannel downOut;

	private Selector selector;

	//private ByteBuffer recBuffer = ByteBuffer.allocate(ATConstants.MAX_DATAGRAM).order(ByteOrder.BIG_ENDIAN);

	//private ByteBuffer transBuffer = ByteBuffer.allocate(ATConstants.MAX_DATAGRAM).order(ByteOrder.BIG_ENDIAN);

	private SocketAddress sendTo;
	private SelectionKey downInReadable;

	// ----------------------------------- sequencer checker

	private int transmittCounter = 1;
	private int lastSeenCounter = -1;
	private int receiveCounter = 0;
	private int problemCounter = 0;

	// ---------------------------------
	private final int recSrc;

	public MonitorPort(boolean isServer)
	{
		int bindPort = isServer ? IControlPortBase.HEARTBEAT_DEAMON_SOURCE_PORT : IControlPortBase.HEARTBEAT_CONTROLLER_SOURCE_PORT;

		int sendPort = isServer ? IControlPortBase.HEARTBEAT_DEAMON_DESTINATION_PORT
		        : IControlPortBase.HEARTBEAT_CONTROLLER_DESTINATION_PORT;

		recSrc = isServer ? IControlPortBase.HEARTBEAT_CONTROLLER_SOURCE_PORT : IControlPortBase.HEARTBEAT_DEAMON_SOURCE_PORT;

		int recDest = isServer ? IControlPortBase.HEARTBEAT_CONTROLLER_DESTINATION_PORT
		        : IControlPortBase.HEARTBEAT_DEAMON_DESTINATION_PORT;

		startTime = System.currentTimeMillis();
		// error.log("Starting ZDRIVE sequencer " + startTime);
		nextTimeout = startTime + IControlPortBase.HEARTBEAT_DELAY;
		// expectedModul = si.SlaveModulCode;
		// expectedVersion = si.SlaveModulVersion;
		try
		{
			downIn = DatagramChannel.open(StandardProtocolFamily.INET);
			downIn.setOption(StandardSocketOptions.SO_REUSEADDR, true);
			downIn.bind(new InetSocketAddress(recDest));

			sendTo = new InetSocketAddress(ATConstants.MULTICAST_GROUP, sendPort);
			downOut = DatagramChannel.open(StandardProtocolFamily.INET);
			downOut.setOption(StandardSocketOptions.SO_REUSEADDR, true);
			downOut.bind(new InetSocketAddress(bindPort));

			if (ATConstants.IS_INTERFACE_AUTO)
			{
				List<NetworkInterface> nil = AutomaticNetworkInterface.getList();
				for (NetworkInterface ni : nil)
				{
					logger.info(" Network interface " + ni.getName() + " at " + getClass().getSimpleName());
					downIn.setOption(StandardSocketOptions.IP_MULTICAST_IF, ni);
					downIn.join(InetAddress.getByName(ATConstants.MULTICAST_GROUP), ni);

					downOut.setOption(StandardSocketOptions.IP_MULTICAST_IF, ni);
					downOut.join(InetAddress.getByName(ATConstants.MULTICAST_GROUP), ni);

				}
			}
			else
			{
				String interfaceName = ATConstants.INTERFACE_NAME;
				NetworkInterface ni = NetworkInterface.getByName(interfaceName);
				error.notNull(ni);

				downIn.setOption(StandardSocketOptions.IP_MULTICAST_IF, ni);
				downIn.join(InetAddress.getByName(ATConstants.MULTICAST_GROUP), ni);

				downOut.setOption(StandardSocketOptions.IP_MULTICAST_IF, ni);
				downOut.join(InetAddress.getByName(ATConstants.MULTICAST_GROUP), ni);
			}

			downIn.configureBlocking(false);
			downOut.configureBlocking(false);

			selector = Selector.open();
			downInReadable = downIn.register(selector, SelectionKey.OP_READ);

		}
		catch (SocketException e)
		{
			error.exception(e);
		}
		catch (IOException e)
		{
			error.exception(e);
		}
		thisWorker = new Thread(this, isServer ? "HeartbeatDaemon" : "HeartbeatController");
		activate();
	}

	private void activate()
	{
		thisWorker.start();
	}

	public void run()
	{
		ByteBuffer recBuffer = ByteBuffer.allocate(ATConstants.MAX_DATAGRAM).order(ByteOrder.BIG_ENDIAN);

		ByteBuffer transBuffer = ByteBuffer.allocate(ATConstants.MAX_DATAGRAM).order(ByteOrder.BIG_ENDIAN);


		boolean running = true;
		// -- selector
		int selectReturn;
		Set<SelectionKey> selectedKeys;
		Iterator<SelectionKey> iSelectedKeys;
		while (running)
		{
			try
			{
				long timeout = nextTimeout - System.currentTimeMillis();
				if (timeout < 1L) timeout = 1L;

				// ask for the selected keys and remove handled keys from the
				// iterator
				// as doc recommends
				selectReturn = selector.select(timeout);
				selectedKeys = selector.selectedKeys();
				iSelectedKeys = selectedKeys.iterator();

				// if (selectReturn > 0)
				// if( selectedKeys.size() > 0)
				if (iSelectedKeys.hasNext())
				{
					if (selectReturn == 0)
					{
						selectorProblem("select return value zero");
					}
					while (iSelectedKeys.hasNext())
					{
						SelectionKey sk = iSelectedKeys.next();
						if (sk == downInReadable)
						{
							recBuffer.clear();
							SocketAddress ssa = downIn.receive(recBuffer);
							{
								if (ssa == null)
								{
									problem("No source address");
								}
								else if (!(ssa instanceof InetSocketAddress))
								{
									problem("Wrong address type");
								}
								else
								{
									InetSocketAddress issa = (InetSocketAddress) ssa;
									if (issa.getPort() != recSrc)
									{
										problem("Wrong source port");
									}
									else
									{
										; // TODO correct reading here
									}
								}
							}
							// TODO --- read even if source wrong ????
							if (recBuffer.position() > 0)
							{
								bufferReceived(ssa, recBuffer);
								iSelectedKeys.remove();
							}
							else
							{
								selectorProblem("Selected but no data");
							}
						}
						else
						{
							selectorProblem("Unknown key selected");
						}
					}
				}
				else
				{
					if (selectReturn != 0)
					{
						selectorProblem("select return value non zero");
					}
					// no selection keys ... must be timeout
					if (System.currentTimeMillis() >= nextTimeout)
					{
						prepareTransmittHeader(transBuffer);
						prepareTransmittHeartbeat(transBuffer);
						onTimeout(transBuffer);
						doTheTransmitt( transBuffer);
						nextTimeout += IControlPortBase.HEARTBEAT_DELAY; // increment
						                                                 // ONLY
						                                                 // here
					}
					else
					{
						selectorProblem("timeout without out time ....????");
					}
				}
				{
					// -- after all selections
					checkTickListener();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
				selectorProblem("IOException");
			}
		}
	}

	// ----------------------------------------------- functions (abstract ????)
	private void onTimeout(ByteBuffer transmittBuffer)
	{

	}

	protected void onHeartbeat(SocketAddress ssa, ByteBuffer rb, int length)
	{
		for (int i = 0; i < length - 4; i++)
		{
			rb.get();
		}
	}

	// ------------------------------------------------ receive handler
	private void bufferReceived(SocketAddress ssa, ByteBuffer recBuffer)
	{
		boolean block_ok = true;

		receiveCounter++;
		recBuffer.flip();

		if (recBuffer.remaining() < 3 * 4)
		{
			problem("short block");
			return;
		}

		if (recBuffer.getInt() != IControlPortBase.CODE_MAGIC)
		{
			problem("wrong code magic");
			block_ok = false;
		}
		if (recBuffer.getInt() != IControlPortBase.SPEC_VER)
		{
			problem("wrong spec version");
			block_ok = false;
		}
		if (recBuffer.getInt() != IControlPortBase.TAG_VER)
		{
			problem("wrong tac version");
			block_ok = false;
		}

		while (recBuffer.hasRemaining())
		{
			if (recBuffer.remaining() < 4)
			{
				problem("short header");
				break;
			}
			int type = recBuffer.getShort();
			int length = recBuffer.getShort();
			if (recBuffer.remaining() < length - 4)
			{
				problem("cutted tail");
				break;
			}
			if( type == IControlPortBase.TAG_HEARTBEAT)
			{
				onHeartbeat(ssa, recBuffer, length);
			}
			else
			{
				for (int i = 0; i < length - 4; i++)
				{
					recBuffer.get();
				}
				logger.info("got block type [" + type + "]");
			}
		}

		if (!block_ok)
		{
			problem("wrong block structure");
		}
	}

	// ------------------------------------------------ transmitt handler
	private ByteBuffer prepareTransmittHeader(ByteBuffer tb)
	{
		tb.clear();
		tb.putInt(IControlPortBase.CODE_MAGIC);
		tb.putInt(IControlPortBase.SPEC_VER);
		tb.putInt(IControlPortBase.TAG_VER);
		return tb;
	}

	private ByteBuffer prepareTransmittHeartbeat(ByteBuffer tr)
	{
		short size = 4 + 4 + 4 + 4;

		tr.putShort(IControlPortBase.TAG_HEARTBEAT);
		tr.putShort(size);

		tr.putInt((int) (System.currentTimeMillis() - startTime));
		tr.putInt(transmittCounter);
		{
			transmittCounter++;
			if (transmittCounter == -2) transmittCounter = 2;
		}
		tr.putInt(lastSeenCounter);

		return tr;
	}

	private void doTheTransmitt(ByteBuffer transBuffer)
	{
		transBuffer.flip();
		try
		{
			downOut.send(transBuffer, sendTo);
			checkTickListener();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	// -----------------------------------------------------------------------
	private void selectorProblem(String text)
	{
		// --- do some trash for debugging
		/*
		 * int i = 0; i++; i = i * i;
		 */
		error.log("Selector Problem [" + "daemon" + "]>>> " + text + " <<<");
	}

	protected void problem(String text)
	{
		error.log("Interface Problem [" + "daemon" + "]>>> " + text + " <<<");
		problemCounter++;
	}

	private void checkTickListener()
	{
		// what to do here
	}
}
