package ui;


/**
 * All controllers loaded by the <code>UILoader</code> need to implement this interface.
 * 
 * @author Andreas Wegner
 */
public interface UIController
{
	public default void onReset()
	{
		
	}
}
