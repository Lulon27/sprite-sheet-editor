package window.grid;

import java.util.List;

import window.canvas.SpriteSheetFrame;

/**
 * Event listener for the <code>FrameGrid</code>.
 * 
 * @author Andreas Wegner
 */
public interface FrameGridListener
{
	public default void onAttachListener(FrameGrid frameGrid)
	{
		
	}
	
	public default void onDetachListener(FrameGrid frameGrid)
	{
		
	}
	
	public void onSelectionChanged(SpriteSheetFrame newSelected);
	
	public void onShouldRedraw();
	
	public boolean onFramesMerging(SpriteSheetFrame origin, List<SpriteSheetFrame> mergeWith, SpriteSheetFrame newFrame);
}
