package at1;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import javax.swing.JFrame;

public abstract class LoopConvert extends LoopBase
{
	private static final long serialVersionUID = 1L;

	public LoopConvert(JFrame parent)
	{
		super(parent);
	}

	
	protected abstract double effect(double l, double r);
	
	@Override
	protected byte[] OnBuffer(byte[] bin)
	{
		byte[] answer = new byte[TRANSFER_SIZE];

		ByteBuffer bbin = ByteBuffer.wrap(bin).order(ByteOrder.LITTLE_ENDIAN);
		ByteBuffer bbout = ByteBuffer.wrap(answer).order(ByteOrder.LITTLE_ENDIAN);
		
		ShortBuffer sbIn = bbin.asShortBuffer();
		ShortBuffer sbOut = bbout.asShortBuffer();
		sbIn.limit( sbIn.capacity() );
		sbIn.position(0);
		sbOut.clear();
		int sampleCount = TRANSFER_SIZE / 4;
		for(int i = 0; i < sampleCount; i++)
		{
			short iil =sbIn.get();
			short iir = sbIn.get();
			double l = (double)iil * ( 1.0 / 32768.0);
			double r = (double)iir * ( 1.0 / 32768.0);
			double a = effect(l,r);
			short ia = (short)( a * 32767.0);
			sbOut.put(ia);
			sbOut.put(ia);
		}
				
		return answer;
	}

}
