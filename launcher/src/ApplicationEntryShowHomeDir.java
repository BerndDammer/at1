//package Launcher1;

import java.util.*;
import java.io.*;

public class ApplicationEntryShowHomeDir extends ApplicationEntry
{
    String homeDir;

    public ApplicationEntryShowHomeDir( String homeDir )
    {
        super( "Show " + homeDir +" Dir" );
        this.homeDir = homeDir;
    }
    public void launch()
    {
        LinkedList<String> cmd = new LinkedList<String>();
        String jh = System.getProperty( "user.home" );
        jh = jh + File.separator + homeDir;

        switch( OsIdentifier.getOS() )
        {
            case OsIdentifier.WINDOWS :
                cmd.add( "Explorer" );
            break;
            case OsIdentifier.LINUX :
                cmd.add( "konqueror" );
            break;
            default :
                myLogger.logout("Alien OS");
            break;
        }
        cmd.add( jh );

        if( args != null ) for( String c : args ) cmd.add( c );

        ProcessBuilder pb = new ProcessBuilder( cmd );
        try
        {
            pb.start();
        }
        catch( Exception e )
        {
            System.out.println( e );
        }
    }
}

