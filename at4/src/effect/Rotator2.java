package effect;

import controller.interfaces.IControlOutReceiver;
import controller.interfaces.IControlOutTransmitter.ControlReceiveParameter;


public class Rotator2 extends EffectDialog implements IControlOutReceiver
{
	private static final long serialVersionUID = 1L;
	private static final long SPR = 4800l;
	long sampleCounter;

	
	public Rotator2()
	{
		//thread start before setVisible
	    //commitControls();
		startIt();
		setVisible( true );
	}


	@Override
	public double nextSample(double l, double r)
	{
	    double[] para = parent.getControllerPanelMapper().lockValuesForSample();
		sampleCounter ++;
		
		double sample_divisor = 48000.0 / para[1];
        //double sample_reminder = ((double)sampleCounter) % sample_divisor;
        double sample_reminder = ((double)sampleCounter) / sample_divisor;
		sample_reminder = sample_reminder - Math.floor( sample_reminder);
        double omega = sample_reminder * ( 2.0 * Math.PI);
		double splitter = para[2] * Math.sin( omega );
        double fak_bridge = para[0] + splitter;
        double fak_neck = 1.0 - para[0] - splitter;
        double result = l * fak_bridge + r * fak_neck;
		
		parent.getControllerPanelMapper().freeValuesForSample();
		return result;
	}
	@Override
	public void commitControls()
	{
	    ControlReceiveParameter controlReceiveParameter;
        controlReceiveParameter = new ControlReceiveParameter("base", -1.0, +1.0);
        parent.getControllerPanelMapper().add( controlReceiveParameter,0);
        controlReceiveParameter = new ControlReceiveParameter("frequency", 0.1, 3.0);
        parent.getControllerPanelMapper().add( controlReceiveParameter,1);
        controlReceiveParameter = new ControlReceiveParameter("depth", 0.0, +1.0);
        parent.getControllerPanelMapper().add( controlReceiveParameter,2);
	}
}

