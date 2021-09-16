package editor.userdata;

import java.util.ArrayList;

public class UserDataHandle
{
	private ArrayList<UserDataValueIndividual> individualValues;
	
	public UserDataHandle(ArrayList<UserDataValue> globalValues)
	{	
		this.individualValues = new ArrayList<>();
		
		for(int i = 0; i < globalValues.size(); ++i)
		{
			this.addUserDataValue(globalValues.get(i));
		}
	}
	
	void setUserDataValue(int globalValueIndex, String inputStr)
	{
		this.individualValues.get(globalValueIndex).setCurrentValue(inputStr);
	}
	
	public void addUserDataValue(UserDataValue globalValue)
	{
		this.individualValues.add(new UserDataValueIndividual(globalValue));
	}
	
	void removeUserDataValue(int globalValueIndex)
	{
		this.individualValues.remove(globalValueIndex);
	}
	
	public UserDataValueIndividual getUserDataValue(int globalValueIndex)
	{
		return this.individualValues.get(globalValueIndex);
	}
	
	void swapValues(int index1, int index2)
	{
		UserDataValueIndividual tmp = this.individualValues.get(index1);
		this.individualValues.set(index1, this.individualValues.get(index2));
		this.individualValues.set(index2, tmp);
	}
}
