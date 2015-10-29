package executer;

public class Element
{
    private final String name;
    private final String rtsp;

    public Element(String name, String rtsp)
    {
        this.name = name;
        this.rtsp = rtsp;
    }
    @Override
    public String toString()
    {
        return( name );
    }
    public String getName()
    {
        return name;
    }
    public String getRtsp()
    {
        return rtsp;
    }
}
