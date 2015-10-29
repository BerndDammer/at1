package splicing_sleeve;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

import starter.error;

public class ControlServer implements IControlClient
{
	private static final Logger logger = Logger.getAnonymousLogger();

	// /////////Client Data
	private Remote remote = null;
	private IControlServer controlServer = null;
	private transient boolean backConnected = false;
	private Registry registryClient = null;

	// ///////////////////////////////////
	// Server Data
	private Remote stub;
	private Registry registryServer;

	public ControlServer()
	{
		String serverAddress = null;
		InetAddress myAddress = null;
		try
		{
			serverAddress = Inet4Address.getLocalHost().getHostName();
			myAddress = Inet4Address.getLocalHost();
			logger.info("Host Name : " + myAddress);
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
			logger.severe("no host name");
		}
		logger.info("Client RMI init start");
		try
		{
			registryServer = LocateRegistry.getRegistry(serverAddress, IControlPortBase.DEAMON_REGISTRY_PORT);
			remote = registryServer.lookup(IControlPortBase.DEAMON_SERVICE_NAME);
			controlServer = (IControlServer) remote;

			registryClient = LocateRegistry.createRegistry(IControlPortBase.CONTROLLER_REGISTRY_PORT);
			stub = UnicastRemoteObject.exportObject(this, IControlPortBase.CONTROLLER_SERVICE_PORT);
			registryClient.rebind(IControlPortBase.CONTROLLER_SERVICE_NAME, stub);

			controlServer.connect(myAddress);
			logger.info("Client RMI init done !");
		}
		catch (RemoteException | NotBoundException e)
		{
			logger.severe("Client RMI init failure !");
			error.exception(e);
		}
	}

	// /////////////////////////////////////////////////////////////////////////
	@Override
	public void connect(InetAddress address) throws RemoteException
	{
		logger.info("Client connect start");
		backConnected = true;
		logger.info("Client connect end");
	}

	@Override
	public void disconnect() throws RemoteException
	{
		logger.info("Client disconnect start");
		backConnected = false;
		logger.info("Client disconnect end");
	}

	@Override
	public void hello(String message) throws RemoteException
	{
		logger.info("Client incomming hello");
	}

	// ////////////////////////////////////////////
	public void test()
	{
		logger.info("Client test start");

	}
}
