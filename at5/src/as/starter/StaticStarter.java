package as.starter;

import as.gui.central.ASApplication;
import as.interim.clientport.ClientPort;
import as.interim.serverport.ServerPort;
import javafx.application.Application;

// test: define main in interface ???
public class StaticStarter
{
    //private static final Application application = null;
    private static ClientPort clientPort;
    private static ServerPort serverPort;
    public static void main( String[] args )
    {
        LoggingInit.forceClassLoadingAndSetLogName( "as.log" );
        serverPort = new ServerPort();
        clientPort = new ClientPort();
        Application.launch( ASApplication.class, args );
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
