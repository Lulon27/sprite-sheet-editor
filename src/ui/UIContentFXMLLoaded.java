package ui;

import javafx.scene.Parent;

/**
 * <code>UIContent</code> implementation of JavaFX elements that have been
 * initialized by the <code>FXMLLoader</code>.
 * @author Andreas Wegner
 */
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
