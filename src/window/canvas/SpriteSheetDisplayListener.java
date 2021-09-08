package window.canvas;

import java.util.List;

/**
 * Event listener for <code>SpriteSheetDisplay</code>
 * 
 * @author Andreas Wegner
 *
 */
public interface SpriteSheetDisplayListener
{
	public void onSelectedFrame(SpriteSheetFrame frame);
	
	public boolean onFramesMerging(SpriteSheetFrame origin, List<SpriteSheetFrame> mergeWith, SpriteSheetFrame newFrame);
}
