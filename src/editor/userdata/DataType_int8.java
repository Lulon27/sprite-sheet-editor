package editor.userdata;

public class DataType_int8 implements DataTypeVerifier
{	
	@Override
	public String getDisplayName(Object data)
	{
		return Integer.toString((int) data);
	}

	@Override
	public Integer parseValue(String inputStr)
	{
		try
		{
			return (int) Byte.parseByte(inputStr);
		}
		catch(NumberFormatException e)
		{
			return null;
		}
	}
}
