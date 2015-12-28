package effect;

import controller.interfaces.IControlOutReceiver;
import controller.interfaces.IControlOutTransmitter.ControlReceiveParameter;


public abstract class DelayBase extends EffectDialog implements IControlOutReceiver
{
	private static final long serialVersionUID = 1L;

	private static final double SAMPLE_FREQ = 48000.0;
    private final int HISTORY_SIZE;
	private int history_index = 0;
    private static final double hist_fact = SAMPLE_FREQ / 1000.0;
    
    private final double[] history; 
    private final double maxTime; 
	
	public DelayBase( double maxTime)
	{
	    this.maxTime = maxTime;
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
	    int delay_depth = (int)(para[13] * hist_fact);
	    int delay_index = history_index - delay_depth;
	    if(delay_index < 0) delay_index += HISTORY_SIZE;
	    double delay_output = history[delay_index];
	    
	    //mix delay and original
        double result = (delay_output* para[15] + input * (1.0 - para[15]));
        //double delay_input = (delay_output* para[14] + input * (1.0 - para[14]));
        double delay_input = (delay_output* para[14] + input * (1.0 - para[14]*0.3));
		
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
        controlReceiveParameter = new ControlReceiveParameter("feedback", 0.0, +0.95);
        parent.getControllerPanelMapper().add( controlReceiveParameter,14);
        controlReceiveParameter = new ControlReceiveParameter("effect", 0.0, +1.00);
        parent.getControllerPanelMapper().add( controlReceiveParameter,15);
	}
}

