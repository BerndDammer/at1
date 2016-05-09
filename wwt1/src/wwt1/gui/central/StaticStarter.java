package wwt1.gui.central;

import javafx.application.Application;
import logging.LoggingInit;

// test: define main in interface ???
public interface StaticStarter
{
    public static void main( String[] args )
    {
        LoggingInit.forceClassLoadingAndSetLogName( "wwt5.log" );
        //MYApplication.launch1(args);
        //MYApplication2.launch1(args);
        Application.launch( MYApplication5.class, args );
    }
}
