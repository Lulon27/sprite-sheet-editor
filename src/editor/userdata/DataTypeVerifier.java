package editor.userdata;

public interface DataTypeVerifier
{
	public String getDisplayName(Object data);
	
	public Object parseValue(String inputStr);
}
