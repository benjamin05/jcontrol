package mx.com.lux.control.trabajos.view.listener;

import mx.com.lux.control.trabajos.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Text;

import java.util.Date;

public class DateTimeTextUpdaterKeyListener extends KeyAdapter {

	private DateTime dateTime;
	private Text txtDias;
	private Button buttonGuardar;

	/**
	 * @param dateTime
	 * @param txtDias
	 * @param buttonGuardar
	 */
	public DateTimeTextUpdaterKeyListener( DateTime dateTime, Text txtDias, Button buttonGuardar ) {
		super();
		this.dateTime = dateTime;
		this.txtDias = txtDias;
		this.buttonGuardar = buttonGuardar;
	}

	@Override
	public void keyReleased( KeyEvent arg0 ) {
		if ( !txtDias.getText().isEmpty() && StringUtils.isNumeric( txtDias.getText() ) ) {
			org.joda.time.DateTime today = DateUtils.addDays( new Date(), txtDias.getText() );
			dateTime.setDate( today.getYear(), today.getMonthOfYear() - 1, today.getDayOfMonth() );
		}
	}

	@Override
	public void keyPressed( KeyEvent arg0 ) {
	}

	/**
	 * @param buttonGuardar the buttonGuardar to set
	 */
	public void setButtonGuardar( Button buttonGuardar ) {
		this.buttonGuardar = buttonGuardar;
	}

}
