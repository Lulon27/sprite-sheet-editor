package window.newgrid;

import ui.UIController;
import window.canvas.FramePatternGenerator;
import window.grid.FrameGrid;

public interface GridCreatorController extends UIController
{
	public FramePatternGenerator getGridGenerator(FrameGrid frameGrid);
	
	public default String getWarnings(FrameGrid frameGrid)
	{
		return null;
	}
}
