package effect;

public class ArcTan extends EffectDialog
{
	private static final long serialVersionUID = 1L;

	public ArcTan()
	{
		// thread start before setVisible
		startIt();
		setVisible(true);
	}

	@Override
	public double nextSample(double l, double r)
	{
		double result;
		if (l < 0.05 && r < 0.05)
			result = 0;
		else
			result = Math.atan2(l, r) / Math.PI;
		return result;
	}
}
