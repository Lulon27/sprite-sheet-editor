package window.canvas;

/**
 * This class represents a single frame in a <code>FrameGrid</code>.
 * 
 * @author Andreas Wegner
 */

public class SpriteSheetFrame
{
	public int x;
	public int y;
	public int width;
	public int height;
	
	public Object userData;
	
	public SpriteSheetFrame(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}
