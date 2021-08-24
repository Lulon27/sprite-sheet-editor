package window;

import java.util.Optional;
import java.util.function.Consumer;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

public class FXUtil
{
	
	//=====================================================================
	//                          Text Fields
	//=====================================================================
	
	public static void textFieldInputFloat(TextField textField, Consumer<Float> onCorrect)
	{
		textField.textProperty().addListener((v, oldVal, newVal) ->
		{
			try
			{
				float f = Float.parseFloat(newVal);
				textField.setStyle(null);
				if (onCorrect != null)
				{
					onCorrect.accept(f);
				}
				return;
			}
			catch (NumberFormatException e)
			{
			}
			textField.setStyle("-fx-text-fill: rgb(224, 32, 32)");
		});
	}
	
	public static void textFieldInputInt(TextField textField, Consumer<Integer> onCorrect)
	{
		textField.textProperty().addListener((v, oldVal, newVal) ->
		{
			try
			{
				int i = Integer.parseInt(newVal);
				textField.setStyle(null);
				if (onCorrect != null)
				{
					onCorrect.accept(i);
				}
				return;
			}
			catch (NumberFormatException e)
			{
			}
			textField.setStyle("-fx-text-fill: rgb(224, 32, 32)");
		});
	}
	
	//=====================================================================
	//                          Message Boxes
	//=====================================================================
	
	public static boolean showWarning(String text, ButtonType confirmButton, ButtonType defaultButton, ButtonType ... buttons)
	{
		Alert alert = new Alert(AlertType.WARNING, text, buttons);
		DialogPane pane = alert.getDialogPane();
		for (ButtonType t : alert.getButtonTypes())
		{
			((Button) pane.lookupButton(t)).setDefaultButton(t == defaultButton);
		}
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == confirmButton)
		{
			return true;
		}
		return false;
	}
	
	public static boolean showError(String text)
	{
		return FXUtil.showError(text, ButtonType.OK, ButtonType.OK, ButtonType.OK);
	}
	
	public static boolean showError(String text, ButtonType confirmButton, ButtonType defaultButton, ButtonType ... buttons)
	{
		Alert alert = new Alert(AlertType.ERROR, text, buttons);
		DialogPane pane = alert.getDialogPane();
		for (ButtonType t : alert.getButtonTypes())
		{
			((Button) pane.lookupButton(t)).setDefaultButton(t == defaultButton);
		}
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == confirmButton)
		{
			return true;
		}
		return false;
	}
}