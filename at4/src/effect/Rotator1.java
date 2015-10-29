package effect;


public class Rotator1 extends EffectDialog
{
	private static final long serialVersionUID = 1L;
	private static final long SPR = 4800l;
	long sampleCounter;
	public Rotator1()
	{
		//thread start before setVisible
		startIt();
		setVisible( true );
	}


	@Override
	public double nextSample(double l, double r)
	{
		sampleCounter ++;
		int a = (int)(sampleCounter % SPR);
		double al = (double)a / (double)SPR * 2.0 * Math.PI;
		double result = Math.sin( al ) * l + Math.cos(al) * r;
		return result;
	}
}

