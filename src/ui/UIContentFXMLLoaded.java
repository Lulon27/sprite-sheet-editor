package ui;

import javafx.scene.Parent;

public class UIContentFXMLLoaded implements UIContent
{
	private Parent root;
	
	UIContentFXMLLoaded(Parent root)
	{
		this.root = root;
	}
	
	@Override
	public Parent getRoot()
	{
		return this.root;
	}

	@Override
	public void initializeUI()
	{
		//Do nothing; UI is already initialized by FXML loader.
	}
}
