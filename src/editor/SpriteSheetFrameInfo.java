package editor;

public class SpriteSheetFrameInfo
{
	public float renderWidth;
	public float renderHeight;
	public float renderOffsetX;
	public float renderOffsetY;
	
	public SpriteSheetFrameInfo()
	{
		this.renderWidth = 1.0F;
		this.renderHeight = 1.0F;
		this.renderOffsetX = 0.0F;
		this.renderOffsetY = 0.0F;
	}
	
	public boolean isDefaultValues()
	{
		return this.renderWidth == 1.0F && this.renderHeight == 1.0F && this.renderOffsetX == 0.0F && this.renderOffsetY == 0.0F;
	}
}
