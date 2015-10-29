package split_test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class error
{
    //private static final long serialVersionUID = common.fap.svuid;

    private static final Logger anonymousLogger;

    //private static final Handler mysHandler;
    //private static final Handler mycHandler;

    // TODO why does this not show FINER logs
    //    static class MyHandler extends StreamHandler
    //    {
    //        private MyHandler()
    //        {
    //            setOutputStream( System.out );
    //            setLevel(Level.ALL);
    //            //setFormatter( new SimpleFormatter() );
    //        }
    //    }
    static
    {
        /////////create logging properties
        Properties myLogPoperties = new Properties();

        myLogPoperties.setProperty(".level", "INFO");
        myLogPoperties.setProperty("handlers", "java.util.logging.ConsoleHandler");
        myLogPoperties.setProperty("java.util.logging.ConsoleHandler.level", "ALL");
        myLogPoperties.setProperty("java.util.logging.ConsoleHandler.formatter", "java.util.logging.SimpleFormatter");
        myLogPoperties.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: [%1$tc]  <<<%5$s>>>%n");

        /////////make input stream of properties
        try
        {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            myLogPoperties.store(output, null);
            ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
            LogManager.getLogManager().readConfiguration(input);
            //LogManager.getLogManager().reset();
        }
        catch (IOException xe)
        {
            // TODO what to do here
            end();
        }
        //      try
//      {
//          StringWriter output = new StringWriter();
//          myLogPoperties.store(output, null);
//          StringReader input = new StringReader( output.toString() );
//          LogManager.getLogManager().readConfiguration( input );
//          //LogManager.getLogManager().reset();
//      }
//      catch( IOException xe)
//      {
//          // TODO what to do here
//          end();
//      }
        anonymousLogger = Logger.getAnonymousLogger();
        //        for(Handler oldHandler : anonymousLogger.getHandlers())
        //        {
        //            anonymousLogger.removeHandler( oldHandler);
        //        }
        //mycHandler = new ConsoleHandler();
        //mysHandler = new MyHandler();
        //mysHandler = new StreamHandler( System.out, new SimpleFormatter() );

        //mycHandler.setLevel( Level.ALL );
        //mysHandler.setLevel( Level.ALL );

        //anonymousLogger.addHandler( mycHandler );
        //anonymousLogger.setLevel( Level.ALL );
    }

    private error()
    {
        configuration();
    }

    //////////////////////////////////////////////////////////
    public static void log(String s)
    {
        anonymousLogger.log(Level.INFO, s);
    }

    public static void notNull(Object o)
    {
        if (o == null)
        {
            anonymousLogger.log(Level.SEVERE, "Object must not be NULL ");
            end();
        }
    }

    public static void exception(Exception e)
    {
        anonymousLogger.throwing("error classs", "error.throwing", e);
        end();
    }

    public static void exit(String text)
    {
        anonymousLogger.severe(text);
        end();
    }

    public static void configuration()
    {
        anonymousLogger.config("configuration error");
        end();
    }

    public static void notimplemented()
    {
        anonymousLogger.config("not implemented");
        end();
    }

    private static void end()
    {
        System.exit(1);
    }
}
