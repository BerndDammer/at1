package split_test.interfaces;


/******************************
 * 
 * implements a function where he gets the receiver
 * interface function call 
 * 
 * if connect is called on a content area
 * this content area feeds the connect call to its childs
 * 
 * of the childs have direct ...... by ip 
 * 
 */

// TODO correct to remove type parameter
public interface IGenTransmitter
{
    public void connect(IGenReceiver receiver);
}

