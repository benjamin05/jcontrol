package mx.com.lux.control.trabajos.view.listener;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.DateTime;

public class DatePickerButtonSelectedListener extends SelectionAdapter {

	private DateTime dateTime;

	/**
	 * @param dateTime
	 */
	public DatePickerButtonSelectedListener( DateTime dateTime ) {
		super();
		this.dateTime = dateTime;
	}

	@Override
	public void widgetSelected( SelectionEvent e ) {
		dateTime.setVisible( true );
	}

	@Override
	public void widgetDefaultSelected( SelectionEvent e ) {
	}
}
