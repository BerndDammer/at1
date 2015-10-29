package platform_base;


public enum PLATFORM
{
	JAVA_SOUND("JavaSound" ),
	JASIO_HOST("Asio" ),
	;
	
	private final String name;

	PLATFORM(String name )
	{
		this.name = name;
	}
	@Override
	public String toString()
	{
		return name;
	}
}
