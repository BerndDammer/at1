package split_test.interfaces;


public interface IFromMonitorLock extends IGenReceiver
{
    public void fromMonitor( MessageLockGroup connectMessage );

}
