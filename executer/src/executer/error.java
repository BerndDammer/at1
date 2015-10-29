package executer;


public class error
{
    //private static final long serialVersionUID = common.fap.svuid;

    public error()
    {
        // TODO Auto-generated constructor stub
    }
    
    public static void log(String s)
    {
        System.out.println(s);
    }
    public static void notNull(Object o)
    {
        if(o == null)
            //throw new Error("Object must not be NULL ");
            end();
    }
    public static void exception( Exception e)
    {
        //e.printStackTrace();
        end();
    }
    public static void exit( String text)
    {
        System.out.println(text);
        end();
    }
    public static void configuration()
    {
        System.out.println("configuration error");
        end();
    }
    private static void end()
    {
        try
        {
            throw new Exception();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.exit( 1 );
    }

}
