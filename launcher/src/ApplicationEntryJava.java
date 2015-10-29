//package Launcher1;

public abstract class ApplicationEntryJava extends ApplicationEntry
{
    public String MainClassName;
    public String JarFileName;

    public ApplicationEntryJava( String c, String cm, String j )
    {
        super( c );
        MainClassName = cm;
        JarFileName = j;
    }
}

