package window.userdatatable;

import editor.userdata.UserDataValueType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;

public class UserDataTableCell extends TextFieldTableCell<UserDataValueTableItem, String>
{
	public enum Column
	{
		NAME, DEF_VAL, CUR_VAL;
	}
	
	public static <S> Callback<TableColumn<S,String>, TableCell<S,String>> forTableColumn() {
        throw new UnsupportedOperationException("Do not use UserDataTableCell.forTableColumn(). Use UserDataTableCell.forTableColumn(Column) instead.");
    }
	
	public static <S, T> Callback<TableColumn<S, T>, TableCell<UserDataValueTableItem, String>> forTableColumn(Column column)
	{
		return list -> new UserDataTableCell(column);
	}
	
	private static final Color COLOR_DEFAULT = Color.BLACK;
	
	private final Column column;
	
	public UserDataTableCell(Column column)
	{
		super(new DefaultStringConverter());
		this.column = column;
	}
	
	@Override
	public void updateItem(String item, boolean empty)
	{
		super.updateItem(item, empty);
		
		if(empty || item == null)
		{
			setText(null);
			setGraphic(null);
		}
		else
		{
			setText(item.toString());
			final UserDataValueTableItem userDataValue = getTableRow().getItem();
			if(userDataValue == null)
			{
				return;
			}
			final UserDataValueType dataType = userDataValue.getGlobalValueRef().getDataType();
			if(this.column == UserDataTableCell.Column.DEF_VAL || this.column == UserDataTableCell.Column.CUR_VAL)
			{
				setTextFill(dataType.getDataTypeVerifier().parseValue(item) == null ? Color.RED : COLOR_DEFAULT);
			}
		}
	}
}
