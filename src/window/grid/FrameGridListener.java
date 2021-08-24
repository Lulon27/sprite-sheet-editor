package window.grid;

import java.util.List;
import window.canvas.SpriteSheetFrame;

public interface FrameGridListener
{
	public void onSelectionChanged(SpriteSheetFrame newSelected);
	
	public void onShouldRedraw();
	
	public boolean onFramesMerging(SpriteSheetFrame origin, List<SpriteSheetFrame> mergeWith, SpriteSheetFrame newFrame);
}
