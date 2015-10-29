package effect;

import java.util.logging.Logger;

public class DNote
{
	private static final Logger logger = Logger.getAnonymousLogger();

	private static String[] names = 
		{
		"C", "C#", "D", "D#", "E", "F", "F#","G", "G#","A", "Bb", "B"
		};
	private final int octave;
	private final int halfstep;

	
	DNote( int octave, int halfstep)
	{
		this.octave = octave;
		this.halfstep = halfstep;
	}
	public double getFrequency()
	{
		int steps = octave * 12 + halfstep;
		steps -= 9;
		double lf = Math.log(110.0) + Math.log( 2.0 ) * ( (double)steps / 12.0 );
		double f = Math.exp(lf);
		logger.info("Frequency : " + f);
		return f;
	}
	@Override
	public String toString()
	{
		return names[halfstep];
	}
}
