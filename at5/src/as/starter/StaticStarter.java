package as.starter;

import as.backend.FCPlatformSelector;
import as.gui.central.ASApplication;
import as.interim.port.ClientPort;
import as.interim.port.ServerPort;
import javafx.application.Application;

// test: define main in interface ???
public class StaticStarter
{
    private static final String[] start_parameters = {"rmiserver","bbobjectclient" };
    // private static final Application application = null;
    private static ClientPort clientPort;
    private static ServerPort serverPort;
    private static FCPlatformSelector fcPlatformSelector;
   
    public static void main( String[] args )
    {
        LoggingInit.forceClassLoadingAndSetLogName( "as.log" );
        serverPort = new ServerPort();
        clientPort = new ClientPort();
        fcPlatformSelector = new FCPlatformSelector();
        // must be last or separate thread
        Application.launch( ASApplication.class, args ); // this never returns !!!
    }

    public static ClientPort getClientPort()
    {
        return clientPort;
    }

    public static ServerPort getServerPort()
    {
        return serverPort;
    }
}
