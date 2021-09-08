package ui;

import javafx.scene.Parent;

/**
 * Interface that represents JavaFX UI elements.
 * This is used by the <code>UILoader</code>.
 * 
 * @author Andreas Wegner
 */
public interface UIContent
{
	public Parent getRoot();
	
	public void initializeUI();
}
