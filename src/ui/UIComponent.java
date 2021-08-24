package ui;

import java.net.URL;
import javafx.scene.Parent;
import ui.UILoader.LoadingBehavior;

public class UIComponent
{
	boolean isLoaded;
	LoadingBehavior loadingBehavior;
	URL loadURL;
	UIController controller;
	UIContent content;
	final String id;
	
	UIComponent(String id)
	{
		this.id = id;
	}
	
	public UIController getController()
	{
		return this.controller;
	}
	
	public URL getURLLoadedFrom()
	{
		return this.loadURL;
	}
	
	public Parent getUIRoot()
	{
		return this.content.getRoot();
	}
	
	@Override
	public String toString()
	{
		return "UIComponent[loadURL: " + this.loadURL + "; Controller: " + this.controller.getClass().getSimpleName()
				+ "; Content: " + this.content.getClass().getSimpleName() +  "]";
	}
}
