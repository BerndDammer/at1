//package Launcher1;

import java.lang.reflect.*;

public class ApplicationEntryInsideJVM extends ApplicationEntryJava
{
    public ApplicationEntryInsideJVM( String compoundName )
    {
        super( compoundName + " IN", compoundName, compoundName + ".jar" );
    }
    public ApplicationEntryInsideJVM( String c, String cn, String j )
    {
        super( c, cn, j );
    }
    public void launch()
    {
        try
        {
            String[] ao = args;
            Class ac = Class.forName( MainClassName );
            //System.out.println( "ac " + ac );
            //System.out.println( "ao.getClass() " + ao.getClass() );
            Method m = ac.getMethod( "main", ao.getClass() );
            //System.out.println( "D " + ac.getDeclaringClass() );
            //System.out.println( "D " + ac.getDeclaringClass() );
            //System.out.println( "Method m : " + m );
            //m.invoke( null, ao.getClass(), ao );
            Object[] invArgs = new Object[]{ ao };
            m.invoke( null, invArgs);
        }
        catch( Exception e )
        {
            System.out.println( e );
        }
    }
}
