package window.canvas;

import java.util.List;

public interface SpriteSheetDisplayListener
{
	public void onSelectedFrame(SpriteSheetFrame frame);
	
	public boolean onFramesMerging(SpriteSheetFrame origin, List<SpriteSheetFrame> mergeWith, SpriteSheetFrame newFrame);
}
