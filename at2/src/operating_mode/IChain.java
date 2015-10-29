package operating_mode;

import platform.PlatformBase;

public interface IChain
{
	void init( PlatformBase platformBase, double sampleFrequency);
	void start();
	void drain();
	void stop();
}
