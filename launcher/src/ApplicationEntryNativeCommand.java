import java.util.LinkedList;

//package Launcher1;

public class ApplicationEntryNativeCommand extends ApplicationEntry
{
    String cmd;

    public ApplicationEntryNativeCommand( String cmd )
    {
        super( cmd );
        this.cmd = cmd;
    }
    public void launch()
    {
        LinkedList<String> cmd = new LinkedList<String>();

        cmd.add( this.cmd );
        for( String c : args ) cmd.add( c );

        ProcessBuilder pb = new ProcessBuilder( cmd );
        try
        {
            pb.start();
        }
        catch( Exception e )
        {
            myLogger.get().severe("Native command launch Ex : " + e );
        }
    }
}
