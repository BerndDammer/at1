package splicing_sleeve;

import java.rmi.RemoteException;

public interface IControlServer extends IControlPortBase
{
    public void hello( String message ) throws RemoteException;

}
