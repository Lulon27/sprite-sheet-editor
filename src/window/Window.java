package window;

import javafx.stage.Stage;

public class Window
{
	protected Stage fxStage;
	
	public Window(Stage stage)
	{
		this.fxStage = stage;
	}
	
	public void init()
	{
		if(this.fxStage == null)
		{
			this.fxStage = new Stage();
		}
		this.fxStage.setOnShowing(e -> this.fxStage.hide());
		this.fxStage.setOnShown(e ->
		{
			final javafx.stage.Window owner = this.fxStage.getOwner();
			if(owner != null)
			{
				this.fxStage.setX(owner.getX() + owner.getWidth() / 2 - this.fxStage.getWidth() / 2);
				this.fxStage.setY(owner.getY() + owner.getHeight() / 2 - this.fxStage.getHeight() / 2);
				System.out.println("sfesf");
			}
			this.fxStage.show();
		});
	}
	
	public Stage getFXStage()
	{
		return this.fxStage;
	}
	
	public void setWidth(double width)
	{
		this.fxStage.setWidth(width);
	}
	
	public void setHeight(double height)
	{
		this.fxStage.setHeight(height);
	}
	
	public void setTitle(String title)
	{
		this.fxStage.setTitle(title);
	}
	
	public void setResizable(boolean resizable)
	{
		this.fxStage.setResizable(resizable);
	}
	
	public void showWindow()
	{
		this.fxStage.show();
	}
	
	public void hideWindow()
	{
		this.fxStage.hide();
	}
}
