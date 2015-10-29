package split_test.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IRemote extends Remote
{
    public static final int PORT = 53000;
    public static final String SERVICE_NAME = "service";
    
    public void hello(String s ) throws RemoteException;
    public void connect() throws RemoteException;
    public void transfer( ElementID elementID, MessageBase message, Class<? extends IGenReceiver> receiver) throws RemoteException;
    public void transfer2( ElementID elementID, MessageBase message ) throws RemoteException;
}
