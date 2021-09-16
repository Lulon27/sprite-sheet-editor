package window.userdatatable;

import java.util.function.Consumer;

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
	
	UserDataValueTableItem(int indexInTable, UserDataValue globalValueRef,
		UserDataValueIndividual frameIndividualValue)
	{
		this.indexInTable = indexInTable;
		this.globalValueRef = globalValueRef;
		this.frameIndividualValue = frameIndividualValue;
		
		this.dataTypeProperty = new SimpleObjectProperty<>(this.globalValueRef.getDataType());
		this.nameProperty = new SimpleStringProperty(this.globalValueRef.getName());
		this.defaultValueProperty = new SimpleStringProperty(this.globalValueRef.getDefaultValueStr());
		this.currentValueProperty = new SimpleStringProperty(
			this.frameIndividualValue != null ? this.frameIndividualValue.getCurrentValueStr() : "");
	}
	
	public void addOnDataTypeChanged(Consumer<UserDataValueType> callback)
	{
		this.dataTypeProperty.addListener((v, oldVal, newVal) ->
		{
			if(oldVal != null)
			{
				callback.accept(newVal);
			}
		});
	}
	
	public void addOnNameChanged(Consumer<String> callback)
	{
		this.nameProperty.addListener((v, oldVal, newVal) ->
		{
			if(oldVal != null)
			{
				callback.accept(newVal);
			}
		});
	}
	
	public void addOnDefaultValueChanged(Consumer<String> callback)
	{
		this.defaultValueProperty.addListener((v, oldVal, newVal) ->
		{
			if(oldVal != null)
			{
				callback.accept(newVal);
			}
		});
	}
	
	public void addOnCurrentValueChanged(Consumer<String> callback)
	{
		this.currentValueProperty.addListener((v, oldVal, newVal) ->
		{
			if(oldVal != null)
			{
				callback.accept(newVal);
			}
		});
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
