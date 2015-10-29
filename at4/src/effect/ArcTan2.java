package effect;

public class ArcTan2 extends EffectDialog
{
	private static final long serialVersionUID = 1L;

	public ArcTan2()
	{
		// thread start before setVisible
		startIt();
		setVisible(true);
	}

	@Override
	public double nextSample(double l, double r)
	{
		double x, y, result;
		double f = 2.0 / Math.PI;
		x = l * 0.5 + 0.50001;
		y = r * 0.5 + 0.50001;
		result = Math.atan2(x, y) / Math.PI;
		return result;
	}
}
