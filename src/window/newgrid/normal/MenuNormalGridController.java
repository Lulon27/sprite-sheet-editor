package window.newgrid.normal;

import editor.FrameGridGenerator;
import javafx.scene.control.Spinner;
import ui.UIControl;
import ui.UIController;
import window.canvas.FramePatternGenerator;
import window.grid.FrameGrid;
import window.newgrid.GridCreatorController;

public class MenuNormalGridController implements UIController, GridCreatorController
{
	@UIControl
	private Spinner<Integer> spinnerRows;
	
	@UIControl
	private Spinner<Integer> spinnerColumns;
	
	@Override
	public void onReset()
	{
		this.spinnerRows.getValueFactory().setValue(1);
		this.spinnerColumns.getValueFactory().setValue(1);
	}
	
	@Override
	public FramePatternGenerator getGridGenerator(FrameGrid frameGrid)
	{
		return new FrameGridGenerator(this.spinnerRows.getValue(), this.spinnerColumns.getValue());
	}
	
	@Override
	public String getWarnings(FrameGrid frameGrid)
	{
		int rows = this.spinnerRows.getValue();
		int cols = this.spinnerColumns.getValue();
		if(frameGrid.getImageWidth() % rows != 0 || frameGrid.getImageHeight() % cols != 0)
		{
			return "Creating a grid with these values may give results that do not work correctly.\n"
					+ "Try to use numbers which the image dimensions can be divided by.";
		}
		return null;
	}
}
