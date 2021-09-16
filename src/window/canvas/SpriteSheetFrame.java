package window.canvas;

import editor.userdata.UserDataHandle;

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
	
	private UserDataHandle userDataHandle;
	
	public SpriteSheetFrame(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void setUserDataHandle(UserDataHandle handle)
	{
		this.userDataHandle = handle;
	}
	
	public UserDataHandle getUserDataHandle()
	{
		return this.userDataHandle;
	}
}
