//package Launcher1;

import java.util.*;
import java.io.*;

public class ApplicationEntryNativeScript extends ApplicationEntry
{
    String scr;
    String actDir;
    private boolean isRelative;
    public ApplicationEntryNativeScript( String scr )
    {
        super( scr );
        this.scr = scr;
        actDir = null;
        isRelative = true;
    }
    public ApplicationEntryNativeScript( String caption, String fullPathToScript, String executeDir )
    {
        super( caption );
        this.scr = fullPathToScript;
        actDir = executeDir;
        isRelative = false;
    }
    public void launch()
    {
        LinkedList<String> cmd = new LinkedList<String>();

        switch( OsIdentifier.getOS() )
        {
            case OsIdentifier.WINDOWS :
                cmd.add( scr + ".bat" );
            break;
            case OsIdentifier.LINUX :
                if( isRelative )
                    cmd.add( "./" + scr + ".sh" );
                else
                    cmd.add(        scr + ".sh" );
            break;
            default :
                myLogger.logout("Alien OS");
            break;
        }
        if( args != null ) for( String c : args ) cmd.add( c );

        ProcessBuilder pb = new ProcessBuilder( cmd );
        if( actDir != null )
        {
            File af = new File( actDir );
            pb.directory( new File( actDir ) );
            myLogger.get().finer( "Set actdir " + actDir );
            myLogger.get().finer( "get actdir " + pb.directory() );
            myLogger.get().finer( "exits : " + pb.directory().exists() );
        }
        myLogger.get().finer( "Exec native script cmd : " + cmd );
        try
        {
            pb.start();
        }
        catch( Exception e )
        {
            myLogger.get().severe( "Process launch Ex : " + e );
        }
    }
}
