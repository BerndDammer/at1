package at1;


public class error
{
    public error()
    {
        exit("static only");
    }
    
    public static void log(String s)
    {
        System.out.println(s);
    }
    public static void notNull(Object o)
    {
        if(o == null)
            throw new Error("Object must not be NULL ");
    }
    public static void exception( Exception e)
    {
        e.printStackTrace();
        System.exit( 1 );
    }

    public static void exit( String text)
    {
        System.out.println(text);
        System.exit( 1 );
    }
}
