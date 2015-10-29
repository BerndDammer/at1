package effect;


public class LoopMul1 extends EffectDialog
{
	private static final long serialVersionUID = 1L;
	
	public LoopMul1()
	{
		//thread start before setVisible
		startIt();
		setVisible( true );
	}


	@Override
	public double nextSample(double l, double r)
	{
		return l*r;
	}
}

