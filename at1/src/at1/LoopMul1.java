package at1;

import javax.swing.JFrame;

public class LoopMul1 extends LoopConvert
{

	public LoopMul1(JFrame parent)
	{
		super(parent);
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected double effect(double l, double r)
	{
		return l*r*0.7 + 0.3 *( l + r);
		//return(l-r);
		//return( (l+r) *0.5);
	}
}
