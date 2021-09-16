package editor.userdata;

public final class UserDataValue
{
	private UserDataValueType dataType;
	
	private String name;
	
	private String defaultValueStr;
	
	public UserDataValue(UserDataValueType dataType, String name, String defaultValueStr)
	{
		this.dataType = dataType;
		this.name = name;
		this.defaultValueStr = defaultValueStr;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public UserDataValueType getDataType()
	{
		return this.dataType;
	}
	
	public void setDataType(UserDataValueType dataType)
	{
		this.dataType = dataType;
	}
	
	public String getDefaultValueStr()
	{
		return this.defaultValueStr;
	}
	
	public void setDefaultValue(String defaultValueStr)
	{
		this.defaultValueStr = defaultValueStr;
	}
	
	//Write to data methods...
}
