package operating_mode;

public abstract class OperatorBase implements IOperation
{
	//protected abstract String getName();
	
	protected String getName()
	{
		return getClass().getName();
	}
	
	@Override
	public String toString()
	{
		return getName();
	}
}
