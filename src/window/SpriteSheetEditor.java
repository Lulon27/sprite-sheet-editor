package window;

import java.io.IOException;
import java.util.function.Supplier;

import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ui.UILoader;
import ui.UILoader.LoadingBehavior;
import window.newgrid.NewGridPanelController;
import window.newgrid.NewGridPanelView;
import window.newgrid.normal.MenuNormalGridController;
import window.newgrid.normal.MenuNormalGridView;
import window.userdatatable.UserDataTableControllerImpl;
import window.userdatatable.UserDataTableView;

public class SpriteSheetEditor extends Application
{
	public static Supplier<NewGridPanelController> CONTROLLER_NEW_GRID = UILoader.registerUIComponent("new_grid",
			new NewGridPanelView(), NewGridPanelController.class, LoadingBehavior.ON_INIT);

	public static Supplier<MenuNormalGridController> CONTROLLER_MENU_NORMAL_GRID = UILoader.registerUIComponent(
			"new_grid_normal_grid", new MenuNormalGridView(), MenuNormalGridController.class, LoadingBehavior.ON_INIT);

	public static Supplier<UserDataTableControllerImpl> CONTROLLER_USER_DATA_TABLE = UILoader.registerUIComponent(
			"user_data_table", new UserDataTableView(), UserDataTableControllerImpl.class, LoadingBehavior.ON_INIT, 1000);
	
	public static Supplier<MainWindowController> CONTROLLER_MAIN = UILoader.registerUIComponentFXML(
			"main", SpriteSheetEditor.class.getResource("main.fxml"), LoadingBehavior.ON_INIT);
	
	public static Window WINDOW_MAIN;
	
	public static Window WINDOW_NEW_GRID = UILoader.registerWindow("new_grid", null, "new_grid", Modality.WINDOW_MODAL,
			LoadingBehavior.ON_INIT);
	
	public static void main(String[] args)
	{
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage)
	{
		
		WINDOW_MAIN = UILoader.registerWindow(stage, "main", null, "main", null, LoadingBehavior.ON_INIT);
		
		try
		{
			UILoader.load();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		WINDOW_MAIN.setTitle("Sprite Sheet Editor");
		WINDOW_NEW_GRID.setTitle("Create a grid");
		
		CONTROLLER_MAIN.get().setStage(stage);
		
		WINDOW_MAIN.showWindow();
	}
}
