//package Launcher1;

public abstract class ApplicationEntry
{
    public String ButtonCaption;
    public String IconFile;
    public String comment = null;
    //--------------- must add
    //---- what if different OS requires different library pathes
    //public String applicationDir;
    //public String libraryPath;
    public String[] args;

    public ApplicationEntry( String c )
    {
        ButtonCaption = c ;
        IconFile = null;
        args = null;
    }
    public ApplicationEntry setClp( String[] cmd )
    {
        args = cmd;
        return( this );
    }
    public ApplicationEntry setComment( String c )
    {
        comment = c;
        return( this );
    }
    public String getComment()
    {
        if( comment == null )
            return( ButtonCaption);
        else
            return( comment );
    }
    public abstract void launch();
}
