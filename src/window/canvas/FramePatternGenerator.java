package window.canvas;

import java.util.List;

/**
 * 
 * The <code>FramePatternGenerator</code> is an interface that is used by the <code>FrameGrid</code>
 * to conveniently generate frames for the sprite sheet or tile set.
 * For example the <code>FrameGridGenerator</code> implements this to create a simple grid
 * of frames.
 * 
 * @author Andreas Wegner
 */

@FunctionalInterface
public interface FramePatternGenerator
{
	/**
	 * Generates a custom pattern of frames.
	 * @param imgWidth the width of the image (e.g. sprite sheet) in pixels
	 * @param imgHeight the height of the image (e.g. sprite sheet) in pixels
	 * @return a list of the frames that have been generated
	 */
	public List<SpriteSheetFrame> generate(double imgWidth, double imgHeight);
}
