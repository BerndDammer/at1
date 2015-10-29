package split_test.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import split_test.error;
import split_test.interfaces.IRemote;

public class ServerMain
{
    public static void main( String[] args)
    {
        Server server = new Server();
        try
        {
            Remote stub = UnicastRemoteObject.exportObject( server, IRemote.PORT );
            Registry registry = LocateRegistry.createRegistry( Registry.REGISTRY_PORT);
            registry.rebind( IRemote.SERVICE_NAME, stub);
            
        }
        catch (RemoteException e)
        {
            error.exception(e);
        }
    }
    
}
