package effect;

import controller.interfaces.IControlOutReceiver;
import controller.interfaces.IControlOutTransmitter.ControlReceiveParameter;

public class Waschbrett extends EffectDialog implements IControlOutReceiver
{
	private static final long serialVersionUID = 1L;
	//private static final double SAMPLE_FREQ = 48000.0;

	private final double omega_max = Math.PI * 2.0;
    private final double fak_para_2_omega = Math.PI * 2.0 / 48000.0;
    private double phase = 0.0;
	

	public Waschbrett()
	{
		startIt();
	}

	@Override
	public double nextSample(double l, double r)
	{
        double[] para = parent.getControllerPanelMapper().lockValuesForSample();

        double s = (r* para[12] + l * (1.0 - para[12])) * 0.5;

        double wellenzahl = para[13];
        double ff = wellenzahl * Math.PI * 2.0;
        double wellendicke = para[14];

        phase += para[15] * fak_para_2_omega;
        if(phase > omega_max)  phase -= omega_max;
        
        double result = s;

        s = Math.sin( s * ff + phase) * wellendicke;
	    result += s;

	    parent.getControllerPanelMapper().freeValuesForSample();
        return result;
	}

	@Override
    public void commitControls()
    {
        ControlReceiveParameter controlReceiveParameter;
        controlReceiveParameter = new ControlReceiveParameter("mix", 0.0, +1.0);
        parent.getControllerPanelMapper().add(controlReceiveParameter, 12);
        controlReceiveParameter = new ControlReceiveParameter("wellenzahl", 0.0, +20.0);
        parent.getControllerPanelMapper().add(controlReceiveParameter, 13);
        controlReceiveParameter = new ControlReceiveParameter("wellendicke", 0.0, +0.1);
        parent.getControllerPanelMapper().add(controlReceiveParameter, 14);
        controlReceiveParameter = new ControlReceiveParameter("ShiftFreq", 0.0, +20.0);
        parent.getControllerPanelMapper().add(controlReceiveParameter, 15);
    }
}
