package platform_base;

import platform.asio.PlatformAsio;
import platform.javasound.PlatformJavaSound;

public enum PLATFORM
{
	JAVA_SOUND("JavaSound", PlatformJavaSound.class),
	JASIO_HOST("Asio", PlatformAsio.class),
	;
	
	private final String name;
	private final Class<? extends PlatformBase> platform;
	PLATFORM(String name, Class<? extends PlatformBase> platform)
	{
		this.name = name;
		this.platform = platform;
	}
	@Override
	public String toString()
	{
		return name;
	}
	public Class<? extends PlatformBase> getPlatform()
	{
		return platform;
	}
}
