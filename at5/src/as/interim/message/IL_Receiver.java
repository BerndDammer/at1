package as.interim.message;

public interface IL_Receiver
{
    /*
     * receive is called from a non awt thread
     * informs a gui that the message contains new data
     * 
     */
    void receive(MessageBase message);
}
