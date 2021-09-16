package window.grid;

import java.util.ArrayList;
import java.util.function.BiFunction;

import javafx.scene.paint.Color;
import window.canvas.SpriteSheetFrame;

public final class FrameHighlightColors
{
	private static ArrayList<HighlightColor> highlightColors = new ArrayList<>();
	
	static
	{
		//Selected color
		addHighlightColor(new HighlightColor(new Color(0.9F, 0.75F, 0.0F, 0.5F), 100, (g, f) -> g.getSelectedFrame() == f));
		
		//Error color
		addHighlightColor(new HighlightColor(new Color(1.0D, 0.0D, 0.0D, 0.05D), 90, (g, f) -> f.getUserDataHandle().checkParseErrors()));
		
		//"Values differ from default" color
		addHighlightColor(new HighlightColor(Color.MAGENTA.deriveColor(0, 1, 1, 0.2), 20, (g, f) -> !g.getUserDataManager().isHandleDefaultValues(f.getUserDataHandle())));
		
		//Default color
		addHighlightColor(new HighlightColor(new Color(0.2D, 0.7D, 0.2D, 0.1D), 10, (g, f) -> true));
	}
	
	private FrameHighlightColors()
	{
		
	}
	
	private static class HighlightColor
	{
		final int priority;
		
		final Color color;
		
		final BiFunction<FrameGrid, SpriteSheetFrame, Boolean> colorChooseFunction;
		
		HighlightColor(Color color, int priority, BiFunction<FrameGrid, SpriteSheetFrame, Boolean> colorChooseFunction)
		{
			this.color = color;
			this.priority = priority;
			this.colorChooseFunction = colorChooseFunction;
		}
		
		@Override
		public String toString()
		{
			return "HighlightColor[prio=" + this.priority + ", color=" + this.color + ", func=" + this.colorChooseFunction + "]";
		}
	}
	
	private static void addHighlightColor(HighlightColor color)
	{
		for(int i = 0; i < highlightColors.size(); ++i)
		{
			if(highlightColors.get(i).priority < color.priority)
			{
				highlightColors.add(i, color);
				return;
			}
		}
		highlightColors.add(color);
	}
	
	public static Color getFrameHighlightColor(FrameGrid frameGrid, SpriteSheetFrame frame)
	{
		for(int i = 0; i < highlightColors.size(); ++i)
		{
			if(highlightColors.get(i).colorChooseFunction.apply(frameGrid, frame))
			{
				return highlightColors.get(i).color;
			}
		}
		throw new IllegalStateException("Could not find a highlight color for this frame");
	}
}
