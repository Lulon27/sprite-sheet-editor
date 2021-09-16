package window.userdatatable;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import editor.userdata.UserDataValue;
import editor.userdata.UserDataValueIndividual;
import editor.userdata.UserDataValueType;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.VBox;
import ui.UIControl;
import ui.UIController;
import window.canvas.SpriteSheetFrame;
import window.grid.FrameGrid;
import window.grid.FrameGridListener;
import window.userdatatable.UserDataTableCell.Column;

public class UserDataTableControllerImpl implements UIController, FrameGridListener, Initializable
{
	@UIControl
	private VBox root;
	
	@UIControl
	private TableView<UserDataValueTableItem> userDataTable;
	
	private TableColumn<UserDataValueTableItem, UserDataValueType> tableColumnType;
	private TableColumn<UserDataValueTableItem, String> tableColumnName;
	private TableColumn<UserDataValueTableItem, String> tableColumnDefaultValue;
	private TableColumn<UserDataValueTableItem, String> tableColumnValue;
	
	@UIControl
	private ComboBox<UserDataValueType> valueType;
	
	@UIControl
	private TextField inputName;
	
	@UIControl
	private TextField inputDefaultValue;
	
	@UIControl
	private Button buttonAddVariable;
	
	@UIControl
	private Label statusText;
	
	@UIControl
	private CheckBox checkboxSyncDefaultValues;
	
	private FrameGrid frameGrid;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		this.checkboxSyncDefaultValues.maxWidthProperty().bind(this.root.widthProperty());
		
		this.userDataTable.setEditable(true);
		this.userDataTable.getSelectionModel().cellSelectionEnabledProperty().set(true);
		
		this.tableColumnType = new TableColumn<>("Data type");
		this.tableColumnName = new TableColumn<>("Name");
		this.tableColumnDefaultValue = new TableColumn<>("Default value");
		this.tableColumnValue = new TableColumn<>("Current value");
		this.tableColumnValue.setEditable(false);
		
		this.tableColumnType.setCellValueFactory(p -> p.getValue().dataTypeProperty());
		this.tableColumnName.setCellValueFactory(p -> p.getValue().nameProperty());
		this.tableColumnDefaultValue.setCellValueFactory(p -> p.getValue().defaultValueProperty());
		this.tableColumnValue.setCellValueFactory(p -> p.getValue().currentValueProperty());
		
		this.userDataTable.getColumns().add(this.tableColumnType);
		this.userDataTable.getColumns().add(this.tableColumnName);
		this.userDataTable.getColumns().add(this.tableColumnDefaultValue);
		this.userDataTable.getColumns().add(this.tableColumnValue);
		
		this.tableColumnType.setCellFactory(ComboBoxTableCell.forTableColumn(UserDataValueType.values()));
		this.tableColumnName.setCellFactory(UserDataTableCell.forTableColumn(Column.NAME));
		this.tableColumnDefaultValue.setCellFactory(UserDataTableCell.forTableColumn(Column.DEF_VAL));
		this.tableColumnValue.setCellFactory(UserDataTableCell.forTableColumn(Column.CUR_VAL));
		
		this.userDataTable.setRowFactory(t ->
		{
			final TableRow<UserDataValueTableItem> row = new TableRow<>();
			final ContextMenu contextMenu = new ContextMenu();
			final MenuItem moveUpMenuItem = new MenuItem("Move up");
			final MenuItem moveDownMenuItem = new MenuItem("Move down");
			final MenuItem removeMenuItem = new MenuItem("Remove variable");
			removeMenuItem.setOnAction(e ->
			{
				UserDataTableControllerImpl.this.frameGrid.getUserDataManager().removeUserDataValue(row.getItem().getGlobalValueRef());
				UserDataTableControllerImpl.this.updateTableContent();
			});
			moveUpMenuItem.setOnAction(e ->
			{
				final int index = row.getItem().getIndexInTable();
				this.frameGrid.getUserDataManager().swapUserDataValues(index, index - 1);
				this.updateTableContent();
			});
			moveDownMenuItem.setOnAction(e ->
			{
				final int index = row.getItem().getIndexInTable();
				this.frameGrid.getUserDataManager().swapUserDataValues(index, index + 1);
				this.updateTableContent();
			});
			
			row.itemProperty().addListener((v, oldVal, newVal) ->
			{
				if(newVal != null)
				{
					moveUpMenuItem.setDisable(newVal.getIndexInTable() == 0);
					moveDownMenuItem.setDisable(newVal.getIndexInTable() == row.getTableView().getItems().size() - 1);
				}
			});
			
			contextMenu.getItems().addAll(moveUpMenuItem, moveDownMenuItem, removeMenuItem);
			row.contextMenuProperty().bind(
				Bindings.when(row.emptyProperty())
					.then((ContextMenu)null)
					.otherwise(contextMenu));
			return row;
		});

		this.tableColumnName.setOnEditCommit(e -> this.onEditCommitString(e, "name", e.getRowValue().nameProperty()));
		this.tableColumnDefaultValue.setOnEditCommit(e -> this.onEditCommitString(e, "default value", e.getRowValue().defaultValueProperty()));
		this.tableColumnValue.setOnEditCommit(e -> this.onEditCommitString(e, "current value", e.getRowValue().currentValueProperty()));
		
		this.buttonAddVariable.setOnAction(e -> this.onPressAddVariable());
		
		var selectedItem = this.valueType.getSelectionModel().selectedItemProperty();
		var defaultValEmpty = this.inputDefaultValue.textProperty().isEmpty();
		var nameEmpty = this.inputName.textProperty().isEmpty();
		this.buttonAddVariable.disableProperty().bind(selectedItem.isNull().or(defaultValEmpty).or(nameEmpty));
	}
	
	@Override
	public void onReset()
	{
		
	}
	
	@Override
	public void onSelectionChanged(SpriteSheetFrame newSelected)
	{
		this.tableColumnValue.setEditable(newSelected != null);
		this.updateTableContent();
	}
	
	@Override
	public void onShouldRedraw()
	{
		
	}
	
	@Override
	public boolean onFramesMerging(SpriteSheetFrame origin, List<SpriteSheetFrame> mergeWith,
		SpriteSheetFrame newFrame)
	{
		return true;
	}
	
	@Override
	public void onAttachListener(FrameGrid frameGrid)
	{
		this.frameGrid = frameGrid;
		
	}
	
	@Override
	public void onDetachListener(FrameGrid frameGrid)
	{
		this.frameGrid = null;
		
	}
	
	private void onPressAddVariable()
	{
		String enteredName = this.inputName.getText().strip();
		if(enteredName.isEmpty())
		{
			this.statusText.setText("Name cannot be empty");
			return;
		}
		String enteredValue = this.inputDefaultValue.getText().strip();
		if(enteredValue.isBlank())
		{
			this.statusText.setText("Value cannot be empty");
			return;
		}
		UserDataValueType selectedType = this.valueType.getSelectionModel().getSelectedItem();
		if(selectedType.getDataTypeVerifier().parseValue(enteredValue) == null)
		{
			this.statusText.setText("\"" + enteredValue + "\" is not a valid " + selectedType.getDisplayName() + " value");
			return;
		}
		this.statusText.setText("");
		this.frameGrid.getUserDataManager().addUserDataValue(selectedType, enteredName, enteredValue);
		this.updateTableContent();
		
		this.inputName.setText("");
		this.inputDefaultValue.setText("");
	}
	
	private void updateTableContent()
	{
		this.userDataTable.getItems().clear();
		SpriteSheetFrame selected = this.frameGrid.getSelectedFrame();
		UserDataValue currentGlobalValue;
		UserDataValueIndividual individualValue;
		for(int i = 0; i < this.frameGrid.getUserDataManager().getNumGlobalValues(); ++i)
		{
			currentGlobalValue = this.frameGrid.getUserDataManager().getGlobalValue(i);
			
			individualValue = null;
			if(selected != null)
			{
				individualValue = selected.getUserDataHandle().getUserDataValue(i);
			}
			
			final UserDataValueTableItem newItem = new UserDataValueTableItem(i, currentGlobalValue, individualValue);
			newItem.addOnDataTypeChanged(dataType -> this.onDataTypeChanged(newItem, dataType));
			newItem.addOnNameChanged(name -> this.onNameChanged(newItem, name));
			newItem.addOnDefaultValueChanged(defVal -> this.onDefaultValueChanged(newItem, defVal));
			newItem.addOnCurrentValueChanged(curVal -> this.onCurrentValueChanged(newItem, curVal));
			this.userDataTable.getItems().add(newItem);
		}
	}
	
	private void onEditCommitString(CellEditEvent<UserDataValueTableItem, String> event, String fieldName, StringProperty property)
	{
		String v = event.getNewValue().strip();
		if(v.isEmpty())
		{
			this.statusText.setText("The " + fieldName + " field cannot be blank");
		}
		else
		{
			property.set(v);
		}
		this.userDataTable.refresh();
	}
	
	private void onDataTypeChanged(UserDataValueTableItem item, UserDataValueType dataType)
	{
		item.getGlobalValueRef().setDataType(dataType);
		this.userDataTable.refresh();
	}
	
	private void onNameChanged(UserDataValueTableItem item, String name)
	{
		this.frameGrid.getUserDataManager().setUserDataValueName(item.getGlobalValueRef(), name);
	}
	
	private void onDefaultValueChanged(UserDataValueTableItem item, String defaultValue)
	{
		item.getGlobalValueRef().setDefaultValue(defaultValue, this.checkboxSyncDefaultValues.isSelected());
		if(this.checkboxSyncDefaultValues.isSelected())
		{
			this.updateTableContent();
		}
	}
	
	private void onCurrentValueChanged(UserDataValueTableItem item, String currentValue)
	{
		item.getCurrentValue().setCurrentValue(currentValue);
	}
}
