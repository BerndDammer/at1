package split_test.client;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Logger;

import split_test.error;
import split_test.interfaces.ElementID;
import split_test.interfaces.IGenReceiver;
import split_test.interfaces.IRemote;
import split_test.interfaces.MessageBase;
import split_test.interfaces.MessageLockGroup;

public class Client implements IGenReceiver
{
	private static final Logger logger = Logger.getAnonymousLogger();

	// ///////////////////////////////////
	// /////////Server Data
	// /////////Client Data
	private Remote remote = null;
	private IRemote controlServer = null;

	private Registry registryServer;

	public Client()
	{
		logger.info("Client RMI init start");
		try
		{
			registryServer = LocateRegistry.getRegistry(Registry.REGISTRY_PORT);
			remote = registryServer.lookup(IRemote.SERVICE_NAME);
			controlServer = (IRemote) remote;

			// controlServer.connect();
			logger.info("Client RMI init done !");
		} catch (RemoteException | NotBoundException e)
		{
			logger.severe("Client RMI init failure !");
			error.exception(e);
		}
	}

	public final void doSomeTest()
	{
		ElementID id1 = new ElementID("47.11.08.15");
		ElementID id2 = new ElementID("47.11.08.16");
		MessageBase m1 = new MessageBase();
		MessageLockGroup m2 = new MessageLockGroup();

		try
		{
			controlServer.connect();
			controlServer.hello("Hello Hello!");
			controlServer.transfer2(id1, m1);
			//controlServer.transfer(id2, m2, this.getClass() ); // makes crash
			controlServer.transfer(id2, m2, IGenReceiver.class ); 
		} catch (RemoteException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
