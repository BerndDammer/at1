package effect;


public class OutputTest extends EffectDialog
{
	private static final long serialVersionUID = 1L;
	private static final double SAMPLE_FREQ = 48000.0;
	private static final double SIG_FREQ = 1000.0;
	private static final double AGL_FACT = SIG_FREQ * 2.0 * Math.PI / SAMPLE_FREQ;
	long sampleCounter;

	public OutputTest()
	{
		//thread start before setVisible
		startIt();
		setVisible( true );
	}


	@Override
	public double nextSample(double l, double r)
	{
		sampleCounter ++;
		double al = (double)(sampleCounter) * AGL_FACT;
		double result = Math.sin( al );
		return result;
	}
}

