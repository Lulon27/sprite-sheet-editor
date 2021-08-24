package window.canvas;

import java.util.List;

@FunctionalInterface
public interface FramePatternGenerator
{
	public List<SpriteSheetFrame> generate(double imgWidth, double imgHeight);
}
