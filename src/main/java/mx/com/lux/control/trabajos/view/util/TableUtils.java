package mx.com.lux.control.trabajos.view.util;

import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class TableUtils {

	public static void createTableColumn( Table table, int style, int width, String colName ) {
		TableColumn tblclmnNombre = new TableColumn( table, style );
		tblclmnNombre.setWidth( width );
		tblclmnNombre.setText( colName );
	}

	public static void configTableColumn( TableColumn tableColumn, int width, String colName ) {
		tableColumn.setWidth( width );
		tableColumn.setText( colName );
	}
}
