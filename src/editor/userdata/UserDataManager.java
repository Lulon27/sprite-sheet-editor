package editor.userdata;

import java.util.ArrayList;

public class UserDataManager
{
	/**
	 * The global values including type, name and default value.
	 */
	private ArrayList<UserDataValue> userDataValues;
	
	/**
	 * Handles per frame which contains their individual / current values.
	 */
	private ArrayList<UserDataHandle> handles;
	
	public UserDataManager()
	{
		this.userDataValues = new ArrayList<>();
		this.handles = new ArrayList<>();
	}
	
	public UserDataHandle newHandle()
	{
		UserDataHandle handle = new UserDataHandle(this.userDataValues);
		this.handles.add(handle);
		return handle;
	}
	
	public int getNumGlobalValues()
	{
		return this.userDataValues.size();
	}
	
	public UserDataValue getGlobalValue(int index)
	{
		return this.userDataValues.get(index);
	}
	
	public void addUserDataValue(UserDataValueType dataType, String name, String defaultValueStr)
	{
		UserDataValue value = new UserDataValue(this, dataType, name, defaultValueStr);
		
		//Add the value to the global list and then add an individual value for each frame
		for(int i = 0; i < this.handles.size(); ++i)
		{
			this.handles.get(i).addUserDataValue(value);
		}
		this.userDataValues.add(value);
	}
	
	public void removeUserDataValue(UserDataValue value)
	{
		//Remove the value from the global list and then remove the individual value from each frame
		
		int removeIndex = this.userDataValues.indexOf(value);
		
		for(int i = 0; i < this.handles.size(); ++i)
		{
			this.handles.get(i).removeUserDataValue(removeIndex);
		}
		this.userDataValues.remove(removeIndex);
	}
	
	public boolean setUserDataValueName(UserDataValue value, String name)
	{
		int valueIndex = -1; //To check if exists
		
		for(int i = 0; i < this.userDataValues.size(); ++i)
		{
			if(this.userDataValues.get(i) == value)
			{
				valueIndex = i;
			}
			else if(name.equals(this.userDataValues.get(i).getName()))
			{
				return false;
			}
		}
		if(valueIndex > -1) //Value is inside list (exists)
		{
			value.setName(name);
			return true;
		}
		return false;
	}
	
	public void setUserDataDefaultValue(UserDataValue value, String defaultValueStr, boolean syncDefault)
	{
		if(syncDefault)
		{
			int globalValueIndex = this.userDataValues.indexOf(value);
			
			UserDataHandle handle;
			for(int i = 0; i < this.handles.size(); ++i)
			{
				handle = this.handles.get(i);
				if(handle.getUserDataValue(globalValueIndex).getCurrentValueStr().equals(value.getDefaultValueStr()))
				{
					handle.getUserDataValue(globalValueIndex).setCurrentValue(defaultValueStr);
				}
			}
		}
		
		value.setDefaultValue(defaultValueStr);
	}
	
	public boolean isHandleDefaultValues(UserDataHandle handle)
	{
		UserDataValue currentGlobalValue;
		for(int i = 0; i < this.userDataValues.size(); ++i)
		{
			currentGlobalValue = this.userDataValues.get(i);
			if(!handle.getUserDataValue(i).getCurrentValueStr().equals(currentGlobalValue.getDefaultValueStr()))
			{
				return false;
			}
		}
		return true;
	}
	
	public boolean swapUserDataValues(int index1, int index2)
	{
		if(index1 < 0 || index1 >= this.userDataValues.size() || index2 < 0 || index2 >= this.userDataValues.size())
		{
			return false;
		}
		
		UserDataValue tmp = this.userDataValues.get(index1);
		this.userDataValues.set(index1, this.userDataValues.get(index2));
		this.userDataValues.set(index2, tmp);
		
		for(int i = 0; i < this.handles.size(); ++i)
		{
			this.handles.get(i).swapValues(index1, index2);
		}
		
		return true;
	}
}
