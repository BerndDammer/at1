package effect;

import controller.interfaces.IControlOutReceiver;
import controller.interfaces.IControlOutTransmitter.ControlReceiveParameter;

public class DualWashboard extends EffectDialog implements IControlOutReceiver
{
	private static final long serialVersionUID = 1L;
	//private static final double SAMPLE_FREQ = 48000.0;

	private final double omega_max = Math.PI * 2.0;
    private final double fak_para_2_omega = Math.PI * 2.0 / 48000.0;
    private double l_phase = 0.0;
    private double r_phase = 0.0;
	

	public DualWashboard()
	{
		startIt();
	}

	@Override
	public double nextSample(double l, double r)
	{
        double result;
        double lp, rp;
        double[] para = parent.getControllerPanelMapper().lockValuesForSample();

        // left = bridge pu
        
        {
            double wellenzahl = para[1];
            double ff = wellenzahl * Math.PI * 2.0;
            double wellendicke = para[2];
            
            l_phase += para[3] * fak_para_2_omega;
            if(l_phase > omega_max)  l_phase -= omega_max;
            
            lp = Math.sin( l * ff + l_phase) * wellendicke;
        }

        
        {
            double wellenzahl = para[4];
            double ff = wellenzahl * Math.PI * 2.0;
            double wellendicke = para[5];
            
            r_phase += para[6] * fak_para_2_omega;
            if(r_phase > omega_max)  r_phase -= omega_max;
            
            rp = Math.sin( r * ff + r_phase) * wellendicke;
        }

        
        
        
        result = (rp* para[7] + lp * (1.0 - para[7])) * 0.5;

	    parent.getControllerPanelMapper().freeValuesForSample();
        return result;
	}

	@Override
    public void commitControls()
    {
        ControlReceiveParameter controlReceiveParameter;

        
        controlReceiveParameter = new ControlReceiveParameter("L.wellenzahl", 0.0, +20.0);
        parent.getControllerPanelMapper().add(controlReceiveParameter, 1);
        controlReceiveParameter = new ControlReceiveParameter("L.wellendicke", 0.0, +0.05);
        parent.getControllerPanelMapper().add(controlReceiveParameter, 2);
        controlReceiveParameter = new ControlReceiveParameter("L.ShiftFreq", 0.0, +20.0);
        parent.getControllerPanelMapper().add(controlReceiveParameter, 3);
        
        controlReceiveParameter = new ControlReceiveParameter("R.wellenzahl", 0.0, +20.0);
        parent.getControllerPanelMapper().add(controlReceiveParameter, 4);
        controlReceiveParameter = new ControlReceiveParameter("R.wellendicke", 0.0, +0.05);
        parent.getControllerPanelMapper().add(controlReceiveParameter, 5);
        controlReceiveParameter = new ControlReceiveParameter("R.ShiftFreq", 0.0, +20.0);
        parent.getControllerPanelMapper().add(controlReceiveParameter, 6);
        
        controlReceiveParameter = new ControlReceiveParameter("mix", 0.0, +1.0);
        parent.getControllerPanelMapper().add(controlReceiveParameter, 7);

    }
}
