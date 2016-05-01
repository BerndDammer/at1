package wwt1;

import javafx.application.Application;
import logging.LoggingInit;

public class wwt1starter
{
    public static void main( String[] args )
    {
        LoggingInit.forceClassLoadingAndSetLogName( "wwt5.log" );
        //MYApplication.launch1(args);
        //MYApplication2.launch1(args);
        Application.launch( MYApplication5.class, args );
    }
}
