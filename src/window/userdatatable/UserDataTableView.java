package window.userdatatable;

import editor.userdata.UserDataValueType;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.UIContent;
import ui.UIControl;

public class UserDataTableView implements UIContent
{
	private VBox root;
	
	@UIControl
	private TableView<UserDataValueTableItem> userDataTable;
	
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
	
	@Override
	public Parent getRoot()
	{
		return this.root;
	}

	@Override
	public void initializeUI()
	{
		this.userDataTable = new TableView<>();
		
		this.valueType = new ComboBox<>();
		this.valueType.getItems().addAll(UserDataValueType.values());
		this.valueType.setPrefWidth(150);
		
		this.inputName = new TextField();
		this.inputDefaultValue = new TextField();
		this.buttonAddVariable = new Button("Add variable");
		this.statusText = new Label();
		this.statusText.setStyle("-fx-text-fill: red");

		VBox vboxDataType = new VBox(5, new Label("Data type"), this.valueType);
		VBox vboxName = new VBox(5, new Label("Name"), this.inputName);
		VBox vboxDefaultValue = new VBox(5, new Label("Default value"), this.inputDefaultValue);
		
		HBox hboxVarSettings = new HBox(5, vboxDataType, vboxName, vboxDefaultValue);
		
		this.root = new VBox(5, this.userDataTable, hboxVarSettings, this.buttonAddVariable, this.statusText);
	}
}
