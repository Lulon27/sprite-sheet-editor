package ui;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.function.Supplier;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import window.Window;

/**
 * Main class for all UI related functions.
 * 
 * Windows and UI components can be registered here in order to allow better information transfer
 * between windows and UI components.
 * 
 * @author Andreas Wegner
 */
public class UILoader
{
	private UILoader()
	{
	}
	
	public enum LoadingBehavior
	{
		ON_INIT, ON_REQUEST
	}
	
	static class WindowHandle
	{
		Window window;
		String parentID;
		String autoComponentID;
		Modality modality;
		LoadingBehavior loadingBehavior;
		boolean isLoaded;
	}
	
	private static HashMap<String, UIComponent> registeredComponents = new HashMap<>();
	
	private static HashMap<String, WindowHandle> registeredWindows = new HashMap<>();
	
	public static Window registerWindow(String id, String parentID, String autoComponentID, Modality modality, LoadingBehavior loadingBehavior)
	{
		return registerWindow(null, id, parentID, autoComponentID, modality, loadingBehavior);
	}
	
	public static Window registerWindow(Stage stage, String id, String parentID, String autoComponentID, Modality modality, LoadingBehavior loadingBehavior)
	{
		WindowHandle h = new WindowHandle();
		h.window = new Window(stage);
		h.parentID = parentID;
		h.autoComponentID = autoComponentID;
		h.modality = modality;
		h.loadingBehavior = loadingBehavior;
		registeredWindows.put(id, h);
		return h.window;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends UIController> Supplier<T> registerUIComponentFXML(String id, URL fxmlURL, LoadingBehavior loadingBehavior)
	{
		if(fxmlURL == null)
		{
			throw new IllegalArgumentException("FXML URL cannot be null (component id: '" + id + "'");
		}
		UIComponent comp = new UIComponent(id);
		comp.loadURL = fxmlURL;
		comp.loadingBehavior = loadingBehavior;
		registeredComponents.put(id, comp);
		return () -> (T)comp.controller;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends UIController> Supplier<T> registerUIComponent(String id, UIContent content, Class<? extends UIController> controllerClass,
			LoadingBehavior loadingBehavior)
	{
		UIComponent comp = new UIComponent(id);
		comp.loadURL = null;
		comp.loadingBehavior = loadingBehavior;
		comp.content = content;
		if (controllerClass != null)
		{
			try
			{
				comp.controller = controllerClass.getDeclaredConstructor().newInstance();
			}
			catch (Exception e)
			{
				throw new IllegalArgumentException(
						"Could not instantiate controller class " + controllerClass.getSimpleName() + ": " + e);
			}
		}
		registeredComponents.put(id, comp);
		return () -> (T)comp.controller;
	}
	
	public static UIComponent getUIHandle(String id)
	{
		return registeredComponents.get(id);
	}
	
	public static void load() throws IOException
	{
		for (UIComponent comp : registeredComponents.values())
		{
			if (comp.loadingBehavior == LoadingBehavior.ON_INIT)
			{
				initComponent(comp);
			}
		}
		
		for (WindowHandle wh : registeredWindows.values())
		{
			if (wh.loadingBehavior == LoadingBehavior.ON_INIT)
			{
				initWindow(wh);
			}
		}
		for (WindowHandle wh : registeredWindows.values())
		{
			if (wh.loadingBehavior == LoadingBehavior.ON_INIT)
			{
				initWindowPost(wh);
			}
		}
	}
	
	private static void initComponent(UIComponent component) throws IOException
	{
		if (component.isLoaded)
		{
			System.out.println("Tried to init an already loaded component: " + component);
			return;
		}
		System.out.println("Initializing component with id: " + component.id);
		if (component.loadURL != null)
		{
			// Load from FXML
			final FXMLLoader loader = new FXMLLoader(component.loadURL);
			component.content = new UIContentFXMLLoaded(loader.load());
			final Object ctrl = loader.getController();
			if (!(ctrl instanceof UIController))
			{
				throw new IllegalStateException(
						"Controller of FXML (" + component.loadURL + ") must be of type UIController.");
			}
			component.controller = (UIController) ctrl;
		}
		else
		{
			// Load from code
			component.content.initializeUI();
			
			if (component.controller != null)
			{
				linkControllerToContent(component);
				
				if(component.controller instanceof Initializable)
				{
					((Initializable)component.controller).initialize(null, null);
				}
			}
		}
		component.isLoaded = true;
	}
	
	private static void linkControllerToContent(UIComponent component)
	{
		Class<? extends UIController> ctrlClass = component.controller.getClass();
		
		Field[] fieldsContent = component.content.getClass().getDeclaredFields();
		Field currentFieldCtrl;
		for (Field field : fieldsContent)
		{
			if (!field.isAnnotationPresent(UIControl.class))
			{
				continue;
			}
			
			try
			{
				currentFieldCtrl = ctrlClass.getDeclaredField(field.getName());
			}
			catch (NoSuchFieldException | SecurityException e)
			{
				System.out.println("Could not link field '" + field.getName() + "' of '"
						+ component.content.getClass().getSimpleName() + "' to controller '" + ctrlClass.getSimpleName() + "': " + e);
				continue;
			}
			
			if (!currentFieldCtrl.isAnnotationPresent(UIControl.class))
			{
				System.out.println("Can not link field '" + field.getName() + "' of '"
						+ component.content.getClass().getSimpleName() + "' to controller '" + ctrlClass.getSimpleName() + "' because the controller's field is not annotated with @" + UIControl.class.getSimpleName());
				continue;
			}
			
			currentFieldCtrl.trySetAccessible();
			field.trySetAccessible();
			
			try
			{
				currentFieldCtrl.set(component.controller, field.get(component.content));
			}
			catch (IllegalArgumentException | IllegalAccessException e)
			{
				System.out.println("Could not set field '" + currentFieldCtrl.getName() + "': " + e);
				System.out.println("Controller: " + ctrlClass.getSimpleName() + "; Content: "
						+ component.content.getClass().getSimpleName());
			}
		}
	}
	
	private static void initWindow(WindowHandle wh)
	{
		if (wh.isLoaded)
		{
			System.out.println("Tried to init an already loaded window");
			return;
		}
		wh.window.init();
		Stage stage = wh.window.getFXStage();
		if(wh.modality != null)
		{
			stage.initModality(wh.modality);
		}
	}
	
	private static void initWindowPost(WindowHandle wh)
	{
		if (wh.isLoaded)
		{
			System.out.println("Tried to post init an already loaded window");
			return;
		}
		Stage stage = wh.window.getFXStage();
		if(wh.parentID != null)
		{
			WindowHandle parent = registeredWindows.get(wh.parentID);
			if(parent == null)
			{
				System.out.println("Cannot find window with ID '" + wh.parentID + "' to use as parent");
			}
			else if(!parent.isLoaded)
			{
				System.out.println("Cannot use window with ID '" + wh.parentID + "' as parent because it is not loaded");
			}
			else
			{
				stage.initOwner(parent.window.getFXStage());
			}
		}
		
		if(wh.autoComponentID != null)
		{
			UIComponent autoComp = registeredComponents.get(wh.autoComponentID);
			if(autoComp == null)
			{
				System.out.println("Cannot find component with ID '" + wh.autoComponentID + "' to use as window default");
			}
			else if(!autoComp.isLoaded)
			{
				System.out.println("Cannot use component with ID '" + wh.autoComponentID + "' as window default because it is not loaded");
			}
			else
			{
				stage.setScene(new Scene(autoComp.content.getRoot()));
			}
		}
		
		wh.isLoaded = true;
	}
}
