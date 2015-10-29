package splicing_sleeve;

import java.net.InetAddress;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IControlPortBase extends Remote
{
    public static final long INTERFACE_VERSION = 2l;

    public static final int BASE = 47307;
    public static final int DEAMON_REGISTRY_PORT = BASE + 1;
    public static final int CONTROLLER_REGISTRY_PORT = BASE + 2;
    public static final int DEAMON_SERVICE_PORT = BASE + 4;
    public static final int CONTROLLER_SERVICE_PORT = BASE + 3;
    public static final String DEAMON_SERVICE_NAME = "atdaemon";
    public static final String CONTROLLER_SERVICE_NAME = "atcontroller";

    public static final int HEARTBEAT_DEAMON_SOURCE_PORT = BASE + 3;
    public static final int HEARTBEAT_DEAMON_DESTINATION_PORT = BASE + 4;
    public static final int HEARTBEAT_CONTROLLER_SOURCE_PORT = BASE + 1;
    public static final int HEARTBEAT_CONTROLLER_DESTINATION_PORT = BASE + 2;

    public static final long HEARTBEAT_DELAY = 3000;

    public static final int CODE_MAGIC = 0X4D424348; // MagicBassControl 48
    public static final int SPEC_VER = 1;
    public static final int TAG_VER = 1;

    public static final short TAG_HEARTBEAT = 0;
    public static final short CONNECT_STATUS = 1;
    
    public void connect(InetAddress sourceAddress) throws RemoteException;
    public void disconnect() throws RemoteException;

}
