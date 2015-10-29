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

public class DeamonServer implements IControlServer
{
    private static final Logger logger = Logger.getAnonymousLogger();

    private boolean incommingConnected = false;
    private boolean outgoingConnected = false;
    private Remote stub;
    private Registry registryServer, registryClient;
    private IControlClient controlClient = null;

    public DeamonServer()
    {
        logger.info("Start init RMI Server");
        try
        {
            stub = UnicastRemoteObject.exportObject(this, IControlPortBase.DEAMON_SERVICE_PORT);
            registryServer = LocateRegistry.createRegistry(IControlPortBase.DEAMON_REGISTRY_PORT);
            registryServer.rebind(IControlPortBase.DEAMON_SERVICE_NAME, stub);
            logger.info("OK init RMI Server");
        }
        catch (RemoteException e)
        {
            logger.info("Failure init RMI Server");
            error.exit("Cannot establish Control Port");
        }
    }

    //////////////////////////////////////////////////////////////
    @Override
    public void connect(InetAddress clientAddress) throws RemoteException
    {
        logger.info("connect start");
        incommingConnected = true;
        controlClient = null;

        Remote remote;
        try
        {
            registryClient = LocateRegistry.getRegistry(Inet4Address.getLocalHost().getHostName(),
                    IControlPortBase.CONTROLLER_REGISTRY_PORT);
            remote = registryClient.lookup(IControlPortBase.CONTROLLER_SERVICE_NAME);
            controlClient = (IControlClient) remote;
            outgoingConnected = true;
        }
        catch (NotBoundException e)
        {
            logger.severe("connect failure");
            error.exception(e);// TODO Auto-generated catch block
        }
        catch (UnknownHostException e)
        {
            logger.severe("Cant find Client Host");
            error.exception(e);// TODO Auto-generated catch block
            e.printStackTrace();
        }
        logger.info("connect end");
    }

    @Override
    public void disconnect() throws RemoteException
    {
    	outgoingConnected = false;
        incommingConnected = false;
        controlClient = null;
    }

    @Override
    public void hello( String message ) throws RemoteException
    {
    	logger.info("Incomming hello [" + message + "]");
    }
}
