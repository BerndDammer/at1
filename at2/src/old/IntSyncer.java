package old;

public class IntSyncer
{
	private int value;
	private boolean has = false;

	public IntSyncer()
	{
	}

	public synchronized void put(int value)
	{
		this.value = value;
		has = true;
	}

	public synchronized boolean has()
	{
		return this.has;
	}

	public synchronized int get()
	{
		has = false;
		return value;
	}
}
