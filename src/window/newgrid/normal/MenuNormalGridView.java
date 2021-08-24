package window.newgrid.normal;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import ui.UIContent;
import ui.UIControl;

public class MenuNormalGridView implements UIContent
{
	private GridPane settingsPane;
	
	@UIControl
	private Spinner<Integer> spinnerRows;
	
	@UIControl
	private Spinner<Integer> spinnerColumns;
	
	private Label status;
	
	@Override
	public Parent getRoot()
	{
		return this.settingsPane;
	}

	@Override
	public void initializeUI()
	{
		this.spinnerRows = new Spinner<>(1, 64, 1);
		this.spinnerColumns = new Spinner<>(1, 64, 1);

		this.spinnerRows.setEditable(true);
		this.spinnerColumns.setEditable(true);
		
		this.status = new Label();
		
		this.settingsPane = new GridPane();
		this.settingsPane.setVgap(10);
		this.settingsPane.setHgap(10);
		this.settingsPane.add(new Label("Rows"), 0, 0);
		this.settingsPane.add(new Label("Columns"), 1, 0);
		this.settingsPane.add(this.spinnerRows, 0, 1);
		this.settingsPane.add(this.spinnerColumns, 1, 1);
		this.settingsPane.add(this.status, 0, 2, 2, 1);
	}
}
