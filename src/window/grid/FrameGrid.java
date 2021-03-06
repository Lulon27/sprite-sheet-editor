package window.grid;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import editor.userdata.UserDataManager;
import window.canvas.FramePatternGenerator;
import window.canvas.SpriteSheetFrame;

/**
 * This class handles the logic and controls of the main sprite sheet display.
 * 
 * @author Andreas Wegner
 */
public class FrameGrid
{
	public enum MergeDirection
	{
		LEFT, RIGHT, ABOVE, BELOW;
	}
	
	private List<SpriteSheetFrame> frames;
	
	private SpriteSheetFrame selectedFrame;
	
	private List<FrameGridListener> eventListeners;
	
	private int imgWidth, imgHeight;
	
	private UserDataManager userDataManager;
	
	public FrameGrid()
	{
		this.frames = new ArrayList<>();
		this.eventListeners = new ArrayList<>();
		this.userDataManager = new UserDataManager();
	}
	
	public UserDataManager getUserDataManager()
	{
		return this.userDataManager;
	}
	
	public void updateImgSize(int w, int h)
	{
		this.imgWidth = w;
		this.imgHeight = h;
	}
	
	public int getImageWidth()
	{
		return this.imgWidth;
	}
	
	public int getImageHeight()
	{
		return this.imgHeight;
	}
	
	private boolean fireEvent(Function<FrameGridListener, Boolean> eventFunc)
	{
		boolean eventSucess = true;
		for(FrameGridListener listener : this.eventListeners)
		{
			if(!eventFunc.apply(listener))
			{
				eventSucess = false;
			}
		}
		return eventSucess;
	}
	
	public void addEventListener(FrameGridListener listener)
	{
		this.eventListeners.add(listener);
		listener.onAttachListener(this);
	}
	
	public void removeEventListener(FrameGridListener listener)
	{
		this.eventListeners.remove(listener);
		listener.onDetachListener(this);
	}
	
	public SpriteSheetFrame getSelectedFrame()
	{
		return this.selectedFrame;
	}
	
	public void setSelectedFrame(SpriteSheetFrame frame)
	{
		this.selectedFrame = frame;
		this.fireEvent(e -> {e.onSelectionChanged(frame); return true;});
	}
	
	public void onMouseClicked(double x, double y, int button)
	{
		SpriteSheetFrame currentFrame;
		for (int i = 0; i < this.frames.size(); ++i)
		{
			currentFrame = this.frames.get(i);
			if (x >= currentFrame.x && x <= currentFrame.x + currentFrame.width && y >= currentFrame.y
					&& y <= currentFrame.y + currentFrame.height)
			{
				this.setSelectedFrame(currentFrame);
				break;
			}
		}
		this.fireEvent(e -> {e.onShouldRedraw(); return true;});
	}
	
	public void generateFrames(FramePatternGenerator generator, int imgWidth, int imgHeight)
	{
		this.frames.clear();
		this.setSelectedFrame(null);
		List<SpriteSheetFrame> generatedFrames = generator.generate(imgWidth, imgHeight);
		for(int i = 0; i < generatedFrames.size(); ++i)
		{
			this.addSpriteSheetFrame(generatedFrames.get(i));
		}
		this.fireEvent(e -> {e.onShouldRedraw(); return true;});
	}
	
	public int getNumFrames()
	{
		return this.frames.size();
	}
	
	public SpriteSheetFrame getFrame(int index)
	{
		return this.frames.get(index);
	}
	
	private void addSpriteSheetFrame(SpriteSheetFrame frame)
	{
		frame.setUserDataHandle(this.userDataManager.newHandle());
		this.frames.add(frame);
	}
	
	public void mergeFrames(MergeDirection mergeDirection, SpriteSheetFrame origin)
	{
		List<SpriteSheetFrame> framesToMerge = new ArrayList<>();
		
		SpriteSheetFrame replacement = switch (mergeDirection) {
			case LEFT -> this.findMergableFramesLeft(origin, framesToMerge);
			case RIGHT -> this.findMergableFramesRight(origin, framesToMerge);
			case ABOVE -> this.findMergableFramesAbove(origin, framesToMerge);
			case BELOW -> this.findMergableFramesBelow(origin, framesToMerge);
			default -> null;
		};
		
		if(replacement == null)
		{
			return;
		}
		
		if(!this.fireEvent(e -> e.onFramesMerging(origin, framesToMerge, replacement)))
		{
			return;
		}
		framesToMerge.add(origin);
		this.frames.removeAll(framesToMerge);
		this.addSpriteSheetFrame(replacement);
		this.setSelectedFrame(replacement);
		this.fireEvent(e -> {e.onShouldRedraw(); return true;});
	}
	
	private SpriteSheetFrame findMergableFramesLeft(SpriteSheetFrame origin, List<SpriteSheetFrame> framesToMerge)
	{
		SpriteSheetFrame other;
		int lastOtherWidth = -1;
		for (int i = 0, n = this.frames.size(); i < n; ++i)
		{
			other = this.frames.get(i);
			if (origin.y + origin.height > other.y && origin.y < other.y + other.height
					&& other.x + other.width == origin.x)
			{
				if (lastOtherWidth > -1 && lastOtherWidth != other.width)
				{
					throw new IllegalStateException("trying to merge frames with different widths");
				}
				if(other.y < origin.y || other.y + other.height > origin.y + origin.height)
				{
					throw new IllegalStateException("frames on the left side are out of bounds");
				}
				framesToMerge.add(other);
				lastOtherWidth = other.width;
			}
		}
		if(lastOtherWidth < 0)
		{
			//no frames to merge with found
			return null;
		}
		return new SpriteSheetFrame(origin.x - lastOtherWidth, origin.y,
				lastOtherWidth + origin.width, origin.height);
	}
	
	private SpriteSheetFrame findMergableFramesRight(SpriteSheetFrame origin, List<SpriteSheetFrame> framesToMerge)
	{
		SpriteSheetFrame other;
		int lastOtherWidth = -1;
		for (int i = 0, n = this.frames.size(); i < n; ++i)
		{
			other = this.frames.get(i);
			if (origin.y + origin.height > other.y && origin.y < other.y + other.height
					&& other.x == origin.x + origin.width)
			{
				if (lastOtherWidth > -1 && lastOtherWidth != other.width)
				{
					throw new IllegalStateException("trying to merge frames with different widths");
				}
				if(other.y < origin.y || other.y + other.height > origin.y + origin.height)
				{
					throw new IllegalStateException("frames on the right side are out of bounds");
				}
				framesToMerge.add(other);
				lastOtherWidth = other.width;
			}
		}
		if(lastOtherWidth < 0)
		{
			//no frames to merge with found
			return null;
		}
		return new SpriteSheetFrame(origin.x, origin.y,
				lastOtherWidth + origin.width, origin.height);
	}
	
	private SpriteSheetFrame findMergableFramesAbove(SpriteSheetFrame origin, List<SpriteSheetFrame> framesToMerge)
	{
		SpriteSheetFrame other;
		int lastOtherHeight = -1;
		for (int i = 0, n = this.frames.size(); i < n; ++i)
		{
			other = this.frames.get(i);
			if (origin.x < other.x + other.width && origin.x + origin.width > other.x
					&& other.y + other.height == origin.y)
			{
				if (lastOtherHeight > -1 && lastOtherHeight != other.height)
				{
					throw new IllegalStateException("trying to merge frames with different heights");
				}
				if(other.x < origin.x || other.x + other.width > origin.x + origin.width)
				{
					throw new IllegalStateException("frames on the top side are out of bounds");
				}
				framesToMerge.add(other);
				lastOtherHeight = other.height;
			}
		}
		if(lastOtherHeight < 0)
		{
			//no frames to merge with found
			return null;
		}
		return new SpriteSheetFrame(origin.x, origin.y - lastOtherHeight,
				origin.width, origin.height + lastOtherHeight);
	}
	
	private SpriteSheetFrame findMergableFramesBelow(SpriteSheetFrame origin, List<SpriteSheetFrame> framesToMerge)
	{
		SpriteSheetFrame other;
		int lastOtherHeight = -1;
		for (int i = 0, n = this.frames.size(); i < n; ++i)
		{
			other = this.frames.get(i);
			if (origin.x < other.x + other.width && origin.x + origin.width > other.x
					&& other.y == origin.y + origin.height)
			{
				if (lastOtherHeight > -1 && lastOtherHeight != other.height)
				{
					throw new IllegalStateException("trying to merge frames with different heights");
				}
				if(other.x < origin.x || other.x + other.width > origin.x + origin.width)
				{
					throw new IllegalStateException("frames on the bottom side are out of bounds");
				}
				framesToMerge.add(other);
				lastOtherHeight = other.height;
			}
		}
		if(lastOtherHeight < 0)
		{
			//no frames to merge with found
			return null;
		}
		return new SpriteSheetFrame(origin.x, origin.y,
				origin.width, origin.height + lastOtherHeight);
	}
	
	public boolean isFrameDefaultValues(SpriteSheetFrame frame)
	{
		return this.userDataManager.isHandleDefaultValues(frame.getUserDataHandle());
	}
	
	public boolean isFrameDefaultValues(int frameIndex)
	{
		return this.isFrameDefaultValues(this.frames.get(frameIndex));
	}
}
