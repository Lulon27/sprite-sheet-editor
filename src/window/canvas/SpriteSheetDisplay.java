package window.canvas;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import window.grid.FrameGrid;
import window.grid.FrameGridListener;

public class SpriteSheetDisplay implements FrameGridListener
{
	private Canvas canvas;
	private GraphicsContext canvasGraphics;
	
	public static final Color COLOR_FRAME_FILL_UNSELECTED = new Color(1.0D, 0.0D, 0.0D, 0.05D);
	
	public static final Color COLOR_FRAME_FILL_SELECTED = new Color(0.0D, 1.0D, 0.0D, 0.25D);
	
	private ObjectProperty<Image> currentImage;

	private FrameGrid frameGrid;
	
	private ObjectProperty<SpriteSheetFrame> selectedFrame;
	
	private List<SpriteSheetDisplayListener> eventListeners;
	
	private Function<SpriteSheetFrame, Color> colorFunction;
	
	public SpriteSheetDisplay(Canvas canvas)
	{
		this.eventListeners = new ArrayList<>();
		this.canvas = canvas;
		this.canvasGraphics = this.canvas.getGraphicsContext2D();
		this.selectedFrame = new SimpleObjectProperty<>(null);
		this.currentImage = new SimpleObjectProperty<Image>(null);
		this.colorFunction = frame ->
		{
			if (frame == this.selectedFrame.getValue())
			{
				return COLOR_FRAME_FILL_SELECTED;
			}
			return COLOR_FRAME_FILL_UNSELECTED;
		};
	}
	
	private void fireEventSelectedFrame(SpriteSheetFrame frame)
	{
		this.eventListeners.forEach(e -> e.onSelectedFrame(frame));
	}
	
	private boolean fireEventMerging(SpriteSheetFrame origin, List<SpriteSheetFrame> mergeWith, SpriteSheetFrame newFrame)
	{
		boolean eventSuccess = true;
		for(SpriteSheetDisplayListener e : this.eventListeners)
		{
			eventSuccess = e.onFramesMerging(origin, mergeWith, newFrame);
		}
		return eventSuccess;
	}
	
	public void addEventListener(SpriteSheetDisplayListener eventListener)
	{
		this.eventListeners.add(eventListener);
	}
	
	public void setColorFunction(Function<SpriteSheetFrame, Color> func)
	{
		this.colorFunction = func;
	}
	
	public void setFrameGrid(FrameGrid frameGrid)
	{
		if(this.frameGrid != null)
		{
			this.frameGrid.setEventListener(null);
		}
		
		this.frameGrid = frameGrid;
		this.frameGrid.setEventListener(this);
	}
	
	public FrameGrid getFrameGrid()
	{
		return this.frameGrid;
	}
	
	public void mergeFrames(FrameGrid.MergeDirection mergeDirection, SpriteSheetFrame origin)
	{
		this.frameGrid.mergeFrames(mergeDirection, origin);
	}
	
	public void onMouseClicked(double x, double y, int button)
	{
		this.frameGrid.onMouseClicked(x, y, button);
	}
	
	public void setSize(double width, double height)
	{
		this.canvas.setWidth(width);
		this.canvas.setHeight(height);
		this.frameGrid.updateImgSize((int)width, (int)height);
	}
	
	public int getImageWidth()
	{
		return (int) this.currentImage.getValue().getWidth();
	}
	
	public int getImageHeight()
	{
		return (int) this.currentImage.getValue().getHeight();
	}
	
	public void setImage(String filePath) throws IOException
	{
		try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(filePath)))
		{
			this.currentImage.setValue(new Image(in));
			this.setSize(this.getImageWidth(), this.getImageHeight());
			this.redrawAll();
		}
	}
	
	public void setImage(InputStream input) throws IOException
	{
		this.currentImage.setValue(new Image(input));
		this.setSize(this.getImageWidth(), this.getImageHeight());
		this.redrawAll();
	}
	
	public ObjectProperty<Image> currentImageProperty()
	{
		return this.currentImage;
	}
	
	public boolean isImageLoaded()
	{
		return this.currentImage != null;
	}
	
	public void generateFrames(FramePatternGenerator generator)
	{
		this.frameGrid.generateFrames(generator, this.getImageWidth(), this.getImageHeight());
	}
	
	public SpriteSheetFrame getSelectedFrame()
	{
		return this.selectedFrame.getValue();
	}
	
	public void setSelectedFrame(SpriteSheetFrame frame)
	{
		this.selectedFrame.setValue(frame);
	}
	
	public ObjectProperty<SpriteSheetFrame> selectedFrameProperty()
	{
		return this.selectedFrame;
	}
	
	private void drawFrameOverlay(SpriteSheetFrame frame)
	{
		final Color c = this.colorFunction.apply(frame);
		this.canvasGraphics.setFill(c);
		this.canvasGraphics.fillRect(frame.x, frame.y, frame.width, frame.height);
		this.canvasGraphics.setStroke(new Color(c.getRed() * 0.5F, c.getGreen() * 0.5F, c.getBlue() * 0.5F, 1.0F));
		this.canvasGraphics.strokeRect(frame.x, frame.y, frame.width, frame.height);
	}
	
	private void redrawAll()
	{
		long timeStart = System.nanoTime();
		this.canvasGraphics.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
		this.canvasGraphics.drawImage(this.currentImage.getValue(), 0, 0);
		SpriteSheetFrame currentFrame;
		for (int i = 0; i < this.frameGrid.getNumFrames(); ++i)
		{
			currentFrame = this.frameGrid.getFrame(i);
			this.drawFrameOverlay(currentFrame);
		}
		this.canvasGraphics.setFill(Color.BLACK);
		this.canvasGraphics.fillText("fsefef", 100, 100);
		System.out.println("Redraw took: " + ((System.nanoTime() - timeStart) / 1_000_000) + " ms");
	}

	@Override
	public void onSelectionChanged(SpriteSheetFrame newSelected)
	{
		this.setSelectedFrame(newSelected);
		this.fireEventSelectedFrame(newSelected);
	}

	@Override
	public void onShouldRedraw()
	{
		this.redrawAll();
	}

	@Override
	public boolean onFramesMerging(SpriteSheetFrame origin, List<SpriteSheetFrame> mergeWith, SpriteSheetFrame newFrame)
	{
		return this.fireEventMerging(origin, mergeWith, newFrame);
	}
}
