package editor.userdata;

public class DataType_uint8 implements DataTypeVerifier
{	
	@Override
	public String getDisplayName(Object data)
	{
		return Integer.toString((int) data);
	}

	@Override
	public Integer parseValue(String inputStr)
	{
		int val;
		try
		{
			val = Integer.parseInt(inputStr);
		}
		catch(NumberFormatException e)
		{
			return null;
		}
		
		if(val < 0 || val > 255)
		{
			return null;
		}
		return val;
	}
}
