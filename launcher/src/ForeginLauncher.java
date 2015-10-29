import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class ForeginLauncher
{
    private static boolean found = false;
    private static LinkedList<ApplicationEntryNativeScript> fList = null;

    public static JPanel getPanel()
    {
        JPanel p = new JPanel();
        p.setLayout( new FlowLayout() );
        for( ApplicationEntryNativeScript e : fList )
        {
            p.add( new ApplicationButton( e ) );
        }
        return( p );
    }
    public static boolean hasOther()
    {
        scan();
        return( found );
    }
    private static void scan()
    {
        File actDir = null;
        File scanRoot = null;

        found = false;
        fList = new LinkedList<ApplicationEntryNativeScript>();
        //---------------------- scan it ... every error sets no found
        try
        {
            actDir = new File( System.getProperty( "user.dir") );
            scanRoot = new File( actDir.getParent() );

            for( File tf : scanRoot.listFiles() )
            {
                if( tf.isDirectory() )
                {
                    String s = tf.getName();
                    File tl = new File( tf.getCanonicalPath() + File.separator + "Launcher" + s +".jar" );
                    myLogger.get().finer( "scanning " + s );
                    if( tl.exists()
                        && ! tf.getCanonicalPath().equals( actDir.getCanonicalPath() )
                        && ! tf.getName().equals( PG.DONT_FIND )
                       )
                    {
                        myLogger.get().finer( "tl.getCanonicalPath()    " + tl.getCanonicalPath() );
                        myLogger.get().finer( "actDir.getCanonicalPath()" + actDir.getCanonicalPath() );
                        //---------------------
                        myLogger.get().finer( "found " + tl.getCanonicalPath() );
                        fList.add( new ApplicationEntryNativeScript( s, tf.getCanonicalPath() + File.separator + s, tf.getCanonicalPath() ) );
                        found = true;
                    }
                }
            }
        }
        catch( IOException ie )
        {
            myLogger.get().warning( "Problem scanning for other Launchers");

        }
        finally
        {
        }
    }
}
