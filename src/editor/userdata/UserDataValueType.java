package editor.userdata;

public enum UserDataValueType
{
	INT8("int8", new DataType_int8()),
	UINT8("uint8", new DataType_uint8());
	
	private final String displayName;
	
	private final DataTypeVerifier dataTypeVerifier;
	
	UserDataValueType(String displayName, DataTypeVerifier dataTypeVerifier)
	{
		this.displayName = displayName;
		this.dataTypeVerifier = dataTypeVerifier;
	}
	
	public DataTypeVerifier getDataTypeVerifier()
	{
		return this.dataTypeVerifier;
	}
	
	public String getDisplayName()
	{
		return this.displayName;
	}
	
	@Override
	public String toString()
	{
		return this.displayName;
	}
}
