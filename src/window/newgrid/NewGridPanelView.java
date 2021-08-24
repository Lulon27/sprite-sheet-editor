package window.newgrid;

import editor.GridType;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import ui.UIContent;
import ui.UIControl;

public class NewGridPanelView implements UIContent
{
	@UIControl
	private VBox root;
	
	@UIControl
	private Label description;
	
	@UIControl
	private HBox optionsBoxUpper;
	
	@UIControl
	private VBox vboxToggleGridKind;
	
	@UIControl
	private ToggleGroup toggleGroupGridKind;
	
	@UIControl
	private RadioButton[] radioGridType;
	
	@UIControl
	private ComboBox<GridCreatorMenus> gridPatternSelector;

	@UIControl
	private Button buttonCreate;
	
	@UIControl
	private int gridSettingsPanelIndex;
	
	@Override
	public Parent getRoot()
	{
		return this.root;
	}

	@Override
	public void initializeUI()
	{
		this.description = new Label("Choose how the grid should be created");
		
		this.gridPatternSelector = new ComboBox<>();
		this.gridPatternSelector.getItems().addAll(GridCreatorMenus.values());
		
		this.toggleGroupGridKind = new ToggleGroup();
		
		final GridType[] gridTypes = GridType.values();
		
		this.radioGridType = new RadioButton[gridTypes.length];
		
		for(int i = 0; i < this.radioGridType.length; ++i)
		{
			this.radioGridType[i] = new RadioButton(gridTypes[i].toString());
			this.radioGridType[i].setToggleGroup(this.toggleGroupGridKind);
		}
		
		this.vboxToggleGridKind = new VBox(10, this.radioGridType);
		
		this.optionsBoxUpper = new HBox(25, this.gridPatternSelector, this.vboxToggleGridKind);
		
		this.buttonCreate = new Button("Create grid");
		
		Node[] mainContents =
		{
			this.description,
			this.optionsBoxUpper,
			new Separator(Orientation.HORIZONTAL),
			new Region(), //Place holder
			new Separator(Orientation.HORIZONTAL),
			this.buttonCreate
		};
		
		gridSettingsPanelIndex = mainContents.length - 3;
		
		this.root = new VBox(10, mainContents);
		this.root.setPadding(new Insets(10));
		this.root.setFillWidth(true);
	}
}
