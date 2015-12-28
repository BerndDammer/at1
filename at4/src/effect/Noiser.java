package effect;

import java.util.Random;

import controller.interfaces.IControlOutReceiver;
import controller.interfaces.IControlOutTransmitter.ControlReceiveParameter;


public class Noiser extends EffectDialog implements IControlOutReceiver
{
	private static final long serialVersionUID = 1L;

	private static final double SAMPLE_FREQ = 48000.0;
    private final int HISTORY_SIZE;
	private int history_index = 0;
    private static final double hist_fact = SAMPLE_FREQ / 1000.0;
    
    private final double[] history; 
    private final double maxTime; 
    private final Random random = new Random();
	
	public Noiser()
	{
	    this.maxTime = 0.02;
	    HISTORY_SIZE = (int)( SAMPLE_FREQ * maxTime + 500 );
	    history = new double [HISTORY_SIZE]; 
	    for(int i = 0; i < HISTORY_SIZE;i++)
	        history[i] = 0.0;
		startIt();
	}


	@Override
	public double nextSample(double l, double r)
	{
	    double[] para = parent.getControllerPanelMapper().lockValuesForSample();

	    double input = (r* para[12] + l * (1.0 - para[12])) * 0.5;
	    
	    //store history ... get history index
	    history_index ++;
	    if( history_index >= HISTORY_SIZE) history_index = 0;
	    
	    //get delay output
        double dtms = (para[13] * hist_fact);
        dtms += para[14] * hist_fact * random.nextGaussian();
        int delay_depth = (int)dtms;

        int delay_index = history_index - delay_depth;
	    if(delay_index < 0) delay_index += HISTORY_SIZE;
	    double delay_output = history[delay_index];
	    
	    //mix delay and original
        double result = (delay_output* para[15] + input * (1.0 - para[15]));
        double delay_input = input;
		
        history[ history_index] = delay_input;

	    parent.getControllerPanelMapper().freeValuesForSample();
		return result;
	}

	@Override
	public void commitControls()
	{
	    ControlReceiveParameter controlReceiveParameter;
        controlReceiveParameter = new ControlReceiveParameter("PickupMix", 0.0, +1.0);
        parent.getControllerPanelMapper().add( controlReceiveParameter,12);
        controlReceiveParameter = new ControlReceiveParameter("Time[ms]", 1.0, maxTime * 1000.0);
        parent.getControllerPanelMapper().add( controlReceiveParameter,13);
        controlReceiveParameter = new ControlReceiveParameter("spread", 0.0, 0.5);
        parent.getControllerPanelMapper().add( controlReceiveParameter,14);
        controlReceiveParameter = new ControlReceiveParameter("effectMix", 0.0, +1.00);
        parent.getControllerPanelMapper().add( controlReceiveParameter,15);
	}
}

