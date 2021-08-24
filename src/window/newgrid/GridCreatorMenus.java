package window.newgrid;

import javafx.scene.Parent;
import ui.UIComponent;
import ui.UILoader;

public enum GridCreatorMenus
{
	NORMAL_GRID("Grid", "new_grid_normal_grid");
	
	private final String name;
	private final String menuUIName;
	
	private GridCreatorMenus(String name, String menuUIName)
	{
		this.name = name;
		this.menuUIName = menuUIName;
	}
	
	public String getMenuUIName()
	{
		return this.menuUIName;
	}
	
	@Override
	public String toString()
	{
		return this.name;
	}
	
	public GridCreatorController getController()
	{
		UIComponent handle = UILoader.getUIHandle(this.menuUIName);
		return ((GridCreatorController)handle.getController());
	}
	
	public Parent getMenuRoot()
	{
		return UILoader.getUIHandle(this.menuUIName).getUIRoot();
	}
}
