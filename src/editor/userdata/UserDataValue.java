package editor.userdata;

public final class UserDataValue
{
	private final UserDataManager userDataManager;
	
	private UserDataValueType dataType;
	
	private String name;
	
	private String defaultValueStr;
	
	UserDataValue(UserDataManager userDataManager, UserDataValueType dataType, String name, String defaultValueStr)
	{
		this.userDataManager = userDataManager;
		this.dataType = dataType;
		this.name = name;
		this.defaultValueStr = defaultValueStr;
	}
	
	public UserDataManager getUserDataManager()
	{
		return this.userDataManager;
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
	
	public void setDefaultValue(String defaultValueStr, boolean syncDefault)
	{
		this.userDataManager.setUserDataDefaultValue(this, defaultValueStr, syncDefault);
	}
	
	//Write to data methods...
}
