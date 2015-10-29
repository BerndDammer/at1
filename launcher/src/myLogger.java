import java.io.*;
//import java.net.*;
//import java.util.*;
import java.util.logging.*;
//import h1.*;

// must make thread save ?????

public class myLogger
{
    private static Logger l = null;
    private static String name = null;
    public static Logger get()
    {
        if( l == null ) CreateLogger();
        return( l );
    }

    public static void logout( String s )
    {
        get(); //--- create if not exists
        l.severe( s );
        System.exit( 1 );
    }
    /*
    public static void setName( String n )
    {
        name = n;
    }
    */
    private static void  CreateLogger()
    {
        CheckHomeDir();

        try
        {
            l = Logger.getLogger( "LauncherLogger" );

            name = System.getProperty( "user.dir" );
            name = new File( name ).getName();
            name = name + "Log.xml";

            FileHandler fh = new FileHandler( getHomeName() + File.separator + name );
            l.setUseParentHandlers( false ); //--- no console output
            l.addHandler( fh );
            l.setLevel( Level.ALL );
        }
        catch( Exception e )
        {
            System.out.println("Unexpected exception");
            e.printStackTrace();
            l = null;
            System.exit( 1 );
        }
    }
    private static void CheckHomeDir()
    {
        File hdf = new File( getHomeName() );
        if( hdf.exists() )
        {
            if( hdf.isDirectory() )
            {
                return; //-------- everything OK
            }
            else
            {
                try
                {
                    if( ! hdf.delete() ) throw new Exception("Can't delete irritating File!");
                    if( ! hdf.mkdir() )  throw new Exception("Can't create directory");
                    return;
                }
                catch( Exception e )
                {
                    System.out.println("Can't create home directory");
                    System.out.println("Exception : ");
                    System.exit( 1 );
                }
            }
        }
        else
        {
            try
            {
                if( ! hdf.mkdir() )  throw new Exception("Can't create directory");
                return;
            }
            catch( Exception e )
            {
                System.out.println("Can't create home directory");
                System.out.println("Exception : ");
                System.exit( 1 );
            }
        }
    }
    private static String getHomeName()
    {
        return( System.getProperty( "user.home") + File.separator + "." + PG.HOME_SUBDIR );
    }
}

