package operating_mode;

public interface IOperation extends IChain
{
	//stereo signed int 16 chunk
	void nextChunck_signedInt16Stereo(byte[] in, byte[] out, int sampleCount);
}
