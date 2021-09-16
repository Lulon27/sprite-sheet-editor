package editor;

import java.util.ArrayList;
import java.util.List;
import window.canvas.FramePatternGenerator;
import window.canvas.SpriteSheetFrame;

/**
 * This is a frame pattern generator that creates a grid pattern
 * with a specified number of rows and columns.
 * 
 * @author Andreas Wegner
 */
public class FrameGridGenerator implements FramePatternGenerator
{
	private final int rows;
	private final int columns;
	
	public FrameGridGenerator(int rows, int columns)
	{
		this.rows = rows;
		this.columns = columns;
	}
	
	@Override
	public List<SpriteSheetFrame> generate(double imgWidth, double imgHeight)
	{
		ArrayList<SpriteSheetFrame> frames = new ArrayList<>(this.rows * this.columns);
		int frameWidth = (int) (imgWidth / columns);
		int frameHeight = (int) (imgHeight / rows);
		SpriteSheetFrame currentFrame;
		for(int x, y = 0; y < rows; ++y)
		{
			for(x = 0; x < columns; ++x)
			{
				currentFrame = new SpriteSheetFrame(x * frameWidth, y * frameHeight, frameWidth, frameHeight);
				frames.add(currentFrame);
			}
		}
		return frames;
	}
	
}
