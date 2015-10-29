package effect;

public class ArcTan3 extends EffectDialog
{
	private static final long serialVersionUID = 1L;

	public ArcTan3()
	{
		// thread start before setVisible
		startIt();
		//setVisible(true);
	}

	@Override
	public double nextSample(double l, double r)
	{
		double x, y, result;
		double f = 2.0 / Math.PI;
		x = r;
		y = l * 0.5 + 0.50001;
		result = Math.atan2(y, x) / Math.PI;
		return result;
	}
}
