//package Launcher1;

import java.util.*;

public class ApplicationEntryDocument extends ApplicationEntry
{
    String doc;

    public ApplicationEntryDocument( String docName)
    {
        super( docName );
        this.doc = docName;
    }
    public void launch()
    {
        LinkedList<String> cmd = new LinkedList<String>();

        switch( OsIdentifier.getOS() )
        {
            case OsIdentifier.WINDOWS :
                String cc = System.getenv("ComSpec" );
                cmd.add( cc != null ? cc : "cmd" );
                cmd.add( "/c" );
                /*
                cmd.add( "start" ); //--- wieso geht das nicht ... weil nur als comspec internes commando
                */
            break;
            case OsIdentifier.LINUX :
                cmd.add( "konqueror" );
            break;
            default :
                System.exit( 1 );
            break;
        }
        cmd.add( doc );
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
