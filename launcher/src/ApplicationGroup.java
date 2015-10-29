
public class ApplicationGroup
{
    public String headline;
    public boolean developer;
    public ApplicationEntry[] list;

    public ApplicationGroup( String headline, boolean developer, ApplicationEntry[] list )
    {
        this.headline = headline;
        this.developer = developer;
        this.list = list;
    }
}
