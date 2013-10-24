package mx.com.lux.control.trabajos.view.listener;

import mx.com.lux.control.trabajos.util.DateUtils;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Text;

import java.sql.Timestamp;
import java.util.Date;

public class DateTimeTextUpdaterSelectionListener extends SelectionAdapter {

	private DateTime dateTime;
	private Text txtDias;
	private Button buttonGuardar;

	/**
	 * @param dateTime
	 * @param txtDias
	 * @param buttonGuardar
	 */
	public DateTimeTextUpdaterSelectionListener( DateTime dateTime, Text txtDias, Button buttonGuardar ) {
		super();
		this.dateTime = dateTime;
		this.txtDias = txtDias;
		this.buttonGuardar = buttonGuardar;
	}

	@Override
	public void widgetSelected( SelectionEvent arg0 ) {
		String fecha = DateUtils.builToTimestampFormat( dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), dateTime.getHours(), dateTime.getMinutes(), dateTime.getSeconds() );
		Timestamp timestamp = Timestamp.valueOf( fecha );
		Integer daysBetween = mx.com.lux.control.trabajos.util.DateUtils.daysBetweenCeilDayHours( new Date(), timestamp );
		txtDias.setText( daysBetween.toString() );
	}

	@Override
	public void widgetDefaultSelected( SelectionEvent arg0 ) {

	}

	/**
	 * @param buttonGuardar the buttonGuardar to set
	 */
	public void setButtonGuardar( Button buttonGuardar ) {
		this.buttonGuardar = buttonGuardar;
	}
}
