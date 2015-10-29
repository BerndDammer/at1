//package Launcher1;

public class OsIdentifier
{
    public static final int UNKNOWN   = 0;
    public static final int WINDOWS = 1;
    public static final int LINUX   = 2;

    private static int osIndex = UNKNOWN;
    private static String javaHome = null;

    private static boolean gotOs = false;
    private static boolean gotHome = false;

    public static void check()
    {
        String s1;

        //-------------- OS ... first try by os.name
        s1 = System.getProperty( "os.name" );
        {
            if( s1 != null )
            {
                String s2 = s1.toUpperCase();
                if     ( s2.contains("WINDOWS") )
                {
                    osIndex = WINDOWS;
                    gotOs = true;
                }
                else if( s2.contains( "LINUX" ) )
                {
                    osIndex = LINUX;
                    gotOs = true;
                }
                else
                {
                    myLogger.logout("Alien OS");
                }
            }
        }
        //--------------------------- second try set OS
        s1 = System.getenv( "OS4J" );
        if( s1 != null && !s1.equals("") )
        {
            if     ( s1.equals("WINDOWS") )
            {
                osIndex = WINDOWS;
                gotOs = true;
            }
            else if( s1.equals( "LINUX" ) )
            {
                osIndex = LINUX;
                gotOs = true;
            }
            else
            {
                gotOs = false;
            }
        }
        //----------------------- get java home ... first try by java.home
        s1 = System.getProperty( "java.home" );
        if( s1 != null && !s1.equals("") )
        {
            javaHome = s1;
            gotHome = true;
        }
        //---------- second try JAVA_HOME
        s1 = System.getenv( "JAVA_HOME" );
        if( s1 != null && ! s1.equals( "" ) )
        {
            javaHome = s1;
            gotHome = true;
        }
        if( !gotOs )
        {
            System.out.println( "can't identify operating system" );
            System.out.println( "include env setting OS=WINDOWS or OS=LINUX" );
            myLogger.logout("Alien OS");
        }
        if( !gotHome)
        {
            System.out.println( "include env setting JAVA_HOME=<jre directory>" );
            myLogger.logout("Need java home ");
        }
    }
    public static int getOS()
    {
        return( osIndex );
    }
    public static String getJavaHome()
    {
        return( javaHome );
    }
}

