package split_test.server;

import java.rmi.RemoteException;

import split_test.interfaces.ElementID;
import split_test.interfaces.IGenReceiver;
import split_test.interfaces.IRemote;
import split_test.interfaces.MessageBase;

public class Server implements IRemote
{

    @Override
    public void transfer(ElementID elementID, MessageBase message, Class<? extends IGenReceiver> receiver) throws RemoteException
    {
        p("----------transfer---------");
        p("El id 1 : " + elementID );
        p("Message : " + message );
        p("Receiver : " + receiver );
    }

    private void p(String s)
    {
        System.out.println(s);
    }

    @Override
    public void transfer2(ElementID elementID, MessageBase message) throws RemoteException
    {
        p("----------transfer2--------");
        p("El id 2 : " + elementID );
        p("Message : " + message );
    }

    @Override
    public void hello(String s) throws RemoteException
    {
        p(s);
    }

	@Override
	public void connect() throws RemoteException 
	{
		p("connected");		
	}
}
