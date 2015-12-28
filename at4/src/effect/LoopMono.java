package effect;


public class LoopMono extends EffectDialog
{
	private static final long serialVersionUID = 1L;
	
	public LoopMono()
	{
		//thread start before setVisible
		startIt();
		setVisible( true );
	}


	@Override
	public double nextSample(double l, double r)
	{
		return( (l+r) *0.5);
	}


    @Override
    public void commitControls()
    {
        // TODO Auto-generated method stub
        
    }
}

