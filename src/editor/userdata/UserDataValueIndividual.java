package editor.userdata;

public class UserDataValueIndividual
{
	private UserDataValue ref;
	
	private String currentValueStr;
	
	public UserDataValueIndividual(UserDataValue ref)
	{
		this.currentValueStr = ref.getDefaultValueStr();
		this.ref = ref;
	}
	
	public String getCurrentValueStr()
	{
		return this.currentValueStr;
	}
	
	public String getDefaultValueStr()
	{
		return this.ref.getDefaultValueStr();
	}
	
	public UserDataValueType getDataType()
	{
		return this.ref.getDataType();
	}
	
	public String getName()
	{
		return this.ref.getName();
	}
	
	public void setCurrentValue(String currentValueStr)
	{
		this.currentValueStr = currentValueStr;
	}
	
	public boolean checkParseError()
	{
		return this.ref.getDataType().getDataTypeVerifier().parseValue(this.currentValueStr) == null;
	}
}
