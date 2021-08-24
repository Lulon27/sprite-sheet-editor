package window.newgrid;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.UIControl;
import ui.UIController;
import window.FXUtil;
import window.SpriteSheetEditor;
import window.canvas.SpriteSheetDisplay;

public class NewGridPanelController implements UIController, Initializable
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
	
	private SpriteSheetDisplay spriteSheetDisplay;
	
	@Override
	public void onReset()
	{
		this.gridPatternSelector.getSelectionModel().select(0);
		this.gridPatternSelector.getSelectionModel().getSelectedItem().getController().onReset();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		this.gridPatternSelector.getSelectionModel().selectedItemProperty().addListener((v, oldVal, newVal) ->
		{
			if (newVal == null)
			{
				return;
			}
			this.root.getChildren().set(gridSettingsPanelIndex, newVal.getMenuRoot());
			newVal.getController().onReset();
		});
		
		this.buttonCreate.setOnAction(e ->
		{
			final GridCreatorMenus selected = this.gridPatternSelector.getSelectionModel().getSelectedItem();
			final String warnings = selected.getController().getWarnings(this.spriteSheetDisplay.getFrameGrid());
			if (warnings != null && !FXUtil.showWarning(warnings + "\nDo you still want to continue?", ButtonType.YES,
					ButtonType.CANCEL, ButtonType.YES, ButtonType.CANCEL))
			{
				return;
			}
			if (this.spriteSheetDisplay.getFrameGrid().getNumFrames() > 0
					&& !FXUtil.showWarning("There already is a grid. Creating a new grid will overwrite the old grid.",
							ButtonType.OK, ButtonType.CANCEL, ButtonType.OK, ButtonType.CANCEL))
			{
				return;
			}
			this.spriteSheetDisplay
					.generateFrames(selected.getController().getGridGenerator(this.spriteSheetDisplay.getFrameGrid()));
			SpriteSheetEditor.WINDOW_NEW_GRID.hideWindow();
		});
		
	}
	
	public void setSpriteSheetDisplay(SpriteSheetDisplay d)
	{
		this.spriteSheetDisplay = d;
	}
}
