package effect;

import java.util.Random;

import controller.interfaces.IControlOutReceiver;
import controller.interfaces.IControlOutTransmitter.ControlReceiveParameter;


public class LowMix extends EffectDialog implements IControlOutReceiver
{
	private static final long serialVersionUID = 1L;

	private static final double SAMPLE_FREQ = 48000.0;
    
    private double y0 , y1;
    private double px0 , py1;
	
	public LowMix()
	{
	    y0 = y1 = 0.0;
		startIt();
	}


	@Override
	public double nextSample(double l, double r)
	{
	    double[] para = parent.getControllerPanelMapper().lockValuesForSample();

	    double x0 = (r* para[0] - l * (1.0 - para[0])) * 0.5;
	    
	    py1 = Math.exp( -2.0 * Math.PI * para[1] / SAMPLE_FREQ );
	    px0 = 1.0 - py1;
	    
	    y1 = y0;
	    y0 = y1 * py1 + x0 * px0;
	    double high = x0 - y0; 
	    
        //double result = (high * para[2] + x0 * (1.0 - para[2]));
        double result = (high + y0 * para[2]);
        //result = y0;
	    parent.getControllerPanelMapper().freeValuesForSample();
		return result;
	}

	@Override
	public void commitControls()
	{
	    ControlReceiveParameter controlReceiveParameter;
        controlReceiveParameter = new ControlReceiveParameter("PickupMix", 0.0, 1.0);
        parent.getControllerPanelMapper().add( controlReceiveParameter,0);
        controlReceiveParameter = new ControlReceiveParameter("SkipFreq", 20.0, 500.0);
        parent.getControllerPanelMapper().add( controlReceiveParameter,1);
        controlReceiveParameter = new ControlReceiveParameter("Flop", 0.0, 3.0);
        parent.getControllerPanelMapper().add( controlReceiveParameter,2);
	}
}

