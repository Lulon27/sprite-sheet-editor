package window;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import editor.SpriteSheetFrameInfo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ui.UIController;
import window.canvas.SpriteSheetDisplay;
import window.canvas.SpriteSheetDisplayListener;
import window.canvas.SpriteSheetFrame;
import window.grid.FrameGrid;
import window.grid.FrameGrid.MergeDirection;

public class MainWindowController implements Initializable, SpriteSheetDisplayListener, UIController
{
	@FXML
	private Canvas drawingCanvas;
	
	@FXML
	private Button btnLoadImg;
	
	@FXML
	private Button btnCreateGrid;
	
	@FXML
	private Button btnMergeLeft;
	
	@FXML
	private Button btnMergeRight;
	
	@FXML
	private Button btnMergeAbove;
	
	@FXML
	private Button btnMergeBelow;
	
	private Stage stage;
	
	private SpriteSheetDisplay spriteSheetDisplay;
	
	private FileChooser fileChooser;
	
	private static final Color COLOR_DEFAULT_VAL_CHANGED = new Color(0.9F, 0.75F, 0.0F, 0.5F);
	
	private FrameGrid frameGrid;
	
	public MainWindowController()
	{
		
	}
	
	public void setStage(Stage stage)
	{
		this.stage = stage;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		this.fileChooser = new FileChooser();
		
		this.spriteSheetDisplay = new SpriteSheetDisplay(this.drawingCanvas);
		this.drawingCanvas.setOnMouseClicked(
				e -> this.spriteSheetDisplay.onMouseClicked(e.getX(), e.getY(), e.getButton().ordinal()));

		this.btnMergeLeft.disableProperty().bind(this.spriteSheetDisplay.selectedFrameProperty().isNull());
		this.btnMergeRight.disableProperty().bind(this.spriteSheetDisplay.selectedFrameProperty().isNull());
		this.btnMergeAbove.disableProperty().bind(this.spriteSheetDisplay.selectedFrameProperty().isNull());
		this.btnMergeBelow.disableProperty().bind(this.spriteSheetDisplay.selectedFrameProperty().isNull());
		
		this.btnCreateGrid.disableProperty().bind(this.spriteSheetDisplay.currentImageProperty().isNull());

		this.btnMergeLeft.setOnAction(e -> this.spriteSheetDisplay.mergeFrames(MergeDirection.LEFT, this.spriteSheetDisplay.getSelectedFrame()));
		this.btnMergeRight.setOnAction(e -> this.spriteSheetDisplay.mergeFrames(MergeDirection.RIGHT, this.spriteSheetDisplay.getSelectedFrame()));
		this.btnMergeAbove.setOnAction(e -> this.spriteSheetDisplay.mergeFrames(MergeDirection.ABOVE, this.spriteSheetDisplay.getSelectedFrame()));
		this.btnMergeBelow.setOnAction(e -> this.spriteSheetDisplay.mergeFrames(MergeDirection.BELOW, this.spriteSheetDisplay.getSelectedFrame()));
		
		this.spriteSheetDisplay.setColorFunction(frame ->
		{
			if(frame == this.spriteSheetDisplay.getSelectedFrame())
			{
				return SpriteSheetDisplay.COLOR_FRAME_FILL_SELECTED;
			}
			if(!((SpriteSheetFrameInfo)frame.userData).isDefaultValues())
			{
				return COLOR_DEFAULT_VAL_CHANGED;
			}
			return SpriteSheetDisplay.COLOR_FRAME_FILL_UNSELECTED;
		});
		
		this.spriteSheetDisplay.addEventListener(this);
		
		this.frameGrid = new FrameGrid();
		this.spriteSheetDisplay.setFrameGrid(frameGrid);
	}
	
	@FXML
	private void onClickLoadImg()
	{
		if(this.stage == null)
		{
			FXUtil.showError("No stage is set for " + this.getClass().getSimpleName());
			return;
		}
		
		if(this.spriteSheetDisplay.isImageLoaded())
		{
			if(!FXUtil.showWarning("An image is already loaded. If you want to load a new image, all unsaved progress will be lost.",
					ButtonType.OK, ButtonType.CANCEL, ButtonType.OK, ButtonType.CANCEL))
			{
				return;
			}
		}
		
		File file = this.fileChooser.showOpenDialog(this.stage);
		if(file != null)
		{
			try(BufferedInputStream in = new BufferedInputStream(new FileInputStream(file)))
			{
				this.spriteSheetDisplay.setImage(in);
			}
			catch(IOException e)
			{
				FXUtil.showError("Could not open the selected file:" + e.getMessage());
			}
		}
	}
	
	@FXML
	private void onClickCreateGrid()
	{
		SpriteSheetEditor.CONTROLLER_NEW_GRID.get().setSpriteSheetDisplay(spriteSheetDisplay);
		SpriteSheetEditor.CONTROLLER_NEW_GRID.get().onReset();
		SpriteSheetEditor.WINDOW_NEW_GRID.showWindow();
	}
	
	@Override
	public void onSelectedFrame(SpriteSheetFrame frame)
	{
		//SpriteSheetFrameInfo info = (SpriteSheetFrameInfo) frame.userData;
	}

	@Override
	public boolean onFramesMerging(SpriteSheetFrame origin, List<SpriteSheetFrame> mergeWith, SpriteSheetFrame newFrame)
	{
		newFrame.userData = new SpriteSheetFrameInfo();
		return true;
	}
}
