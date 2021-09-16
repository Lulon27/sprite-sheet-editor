package window.userdatatable;

import editor.userdata.UserDataValue;
import editor.userdata.UserDataValueIndividual;
import editor.userdata.UserDataValueType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserDataValueTableItem
{
	private UserDataValueIndividual frameIndividualValue;
	
	private UserDataValue globalValueRef;
	
	private ObjectProperty<UserDataValueType> dataTypeProperty;
	private StringProperty nameProperty;
	private StringProperty defaultValueProperty;
	private StringProperty currentValueProperty;
	
	private int indexInTable;
	
	UserDataValueTableItem(int indexInTable, UserDataValue globalValueRef, UserDataValueIndividual frameIndividualValue)
	{
		this.dataTypeProperty = new SimpleObjectProperty<>();
		this.nameProperty = new SimpleStringProperty();
		this.defaultValueProperty = new SimpleStringProperty();
		this.currentValueProperty = new SimpleStringProperty();
		
		this.dataTypeProperty.addListener((v, oldVal, newVal) ->
		{
			if(oldVal != null)
			{
				globalValueRef.setDataType(newVal);
			}
		});
		
		this.nameProperty.addListener((v, oldVal, newVal) ->
		{
			if(oldVal != null)
			{
				globalValueRef.setName(newVal);
			}
		});
		
		this.defaultValueProperty.addListener((v, oldVal, newVal) ->
		{
			if(oldVal != null)
			{
				globalValueRef.setDefaultValue(newVal);
			}
		});
		
		this.currentValueProperty.addListener((v, oldVal, newVal) ->
		{
			if(oldVal != null)
			{
				frameIndividualValue.setCurrentValue(newVal);
			}
		});
		
		this.updateUserDataValueInstance(indexInTable, globalValueRef, frameIndividualValue);
	}
	
	void updateUserDataValueInstance(int indexInTable, UserDataValue globalValueRef, UserDataValueIndividual frameIndividualValue)
	{
		this.globalValueRef = globalValueRef;
		this.frameIndividualValue = frameIndividualValue;
		this.nameProperty.setValue(this.globalValueRef.getName());
		this.dataTypeProperty.setValue(this.globalValueRef.getDataType());
		this.defaultValueProperty.setValue(this.globalValueRef.getDefaultValueStr());
		if(this.frameIndividualValue != null)
		{
			this.currentValueProperty.setValue(this.frameIndividualValue.getCurrentValueStr());
		}
		else
		{
			this.currentValueProperty.setValue("");
		}
		this.indexInTable = indexInTable;
	}
	
	UserDataValue getGlobalValueRef()
	{
		return this.globalValueRef;
	}
	
	UserDataValueIndividual getCurrentValue()
	{
		return this.frameIndividualValue;
	}
	
	ObjectProperty<UserDataValueType> dataTypeProperty()
	{
		return this.dataTypeProperty;
	}
	
	StringProperty nameProperty()
	{
		return this.nameProperty;
	}
	
	StringProperty currentValueProperty()
	{
		return this.currentValueProperty;
	}
	
	StringProperty defaultValueProperty()
	{
		return this.defaultValueProperty;
	}
	
	int getIndexInTable()
	{
		return this.indexInTable;
	}
}
