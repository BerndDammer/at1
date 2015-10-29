package player;

import operating_mode.IChain;
import operating_mode.IOperation;

public interface IPlatformPlayer extends IChain
{
	public void setOperator(IOperation iOperation);

}
