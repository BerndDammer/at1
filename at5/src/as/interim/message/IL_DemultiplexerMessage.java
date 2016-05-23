package as.interim.message;

public interface IL_DemultiplexerMessage
{
    void register( MessageBase message, IL_MessageBaseReceiver<? extends MessageBase> receiver );
}
