package act;

import javax.swing.JFrame;

public class LoopMul1 extends LoopConvert
{
	int retAccu;
	
	public LoopMul1(JFrame parent)
	{
		super(parent);
		retAccu = 0;
		//thread start before setVisible
		startIt();
		setVisible( true );
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected double effect(double l, double r)
	{
		return l*r;
		//return(l-r);
		//return( (l+r) *0.5);
	}
}

