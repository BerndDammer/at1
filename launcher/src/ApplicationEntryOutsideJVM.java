//package Launcher1;

import java.util.*;
import java.io.*;

public class ApplicationEntryOutsideJVM extends ApplicationEntryJava
{
    public String[] jreLaunchAddPara;
    public String ext;
    public boolean PlatformExtensionsFlag = false;

    public ApplicationEntryOutsideJVM( String compoundName )
    {
        super( compoundName + " OUT", compoundName, compoundName + ".jar" );
        jreLaunchAddPara = null;
    }

    public ApplicationEntryOutsideJVM( String c, String cn, String j )
    {
        super( c, cn, j );
        jreLaunchAddPara = null;
    }

    public ApplicationEntryOutsideJVM setJreLaunchAddPara( String[] s )
    {
        jreLaunchAddPara = s;
        return( this );
    }
    public ApplicationEntryOutsideJVM setExtDirs( String s )
    {
        ext = s;
        return( this );
    }
    public ApplicationEntryOutsideJVM usePlatformExtensions( boolean b)
    {
        PlatformExtensionsFlag = b;
        return( this );
    }
    /*
    public ApplicationEntryOutsideJVM useGlobalPlatformExtensions( boolean b){}
    public ApplicationEntryOutsideJVM addLocalStandardExtensions( String SubDirName){}
    Local - Global
    Standard - Platform
    Parameter use + boolean
              add + String

    */
    public void launch()
    {
        LinkedList<String> cmd = new LinkedList<String>();

        cmd.add( OsIdentifier.getJavaHome() + File.separator + "bin" + File.separator + "java" );
        cmd.add( "-Djava.library.path=." ); //---- Linux !!!!!!!!
        {
            if( PlatformExtensionsFlag )
            {
                //------------------ build extension string
                String es = null;

                switch( OsIdentifier.getOS() )
                {
                    case OsIdentifier.WINDOWS :
                        es = "extensionsWindows";
                    break;
                    case OsIdentifier.LINUX :
                        es = "extensionsLinux";
                    break;
                    default :
                        myLogger.logout("Alien OS");
                    break;
                }
                if( ext != null )
                {
                    es = es + File.pathSeparator + ext;
                }
                else
                {
                    //--- nothing to do here
                }
                cmd.add( "-Djava.ext.dirs=" + es );
            }
            else
            {
                if( ext != null )
                {
                    cmd.add( "-Djava.ext.dirs=" + ext );
                }
                else
                {
                    //--- nothing to do here
                }
            }
        }
        if( jreLaunchAddPara != null && jreLaunchAddPara.length > 0 )
            for( String c : jreLaunchAddPara ) cmd.add( c );
        //cmd.add( "-cp" );
        cmd.add( "-jar" );
        cmd.add( JarFileName  );
        //cmd.add( MainClassName );
        if( args != null ) for( String c : args ) cmd.add( c );

        ProcessBuilder pb = new ProcessBuilder( cmd );

        try
        {
            System.out.println("------before");
            System.out.println( pb.command() ) ;
            System.out.println( pb.command().get(0) ) ;
            System.out.println( pb.command().get(1) ) ;
            System.out.println( pb.command().get(2) ) ;
            System.out.println( pb.command().get(3) ) ;
            //System.out.println( pb.command().get(4) ) ;

            Process p = pb.start();

            System.out.println("-----after");
            System.out.println( p );
        }
        catch( Exception e )
        {
            System.out.println( e );
        }
    }
}
