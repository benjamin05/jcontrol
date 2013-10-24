package mx.com.lux.control.trabajos.view.listener;

import org.apache.commons.lang.time.DateUtils;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatePickerDaysUpdater extends KeyAdapter {

	private Text textDays;
	private Text textDate;
	private DateTime dateTime;

	/**
	 * @param textDays
	 * @param textDate
	 * @param dateTime
	 */
	public DatePickerDaysUpdater( Text textDays, Text textDate, DateTime dateTime ) {
		super();
		this.textDays = textDays;
		this.textDate = textDate;
		this.dateTime = dateTime;
	}

	@Override
	public void keyPressed( KeyEvent e ) {
		if ( !textDays.getText().isEmpty() ) {
			Date date = DateUtils.addDays( new Date(), Integer.parseInt( textDays.getText() ) );
			org.joda.time.DateTime today = new org.joda.time.DateTime( date );
			dateTime.setDate( today.getYear(), today.getMonthOfYear(), today.getDayOfMonth() );
			SimpleDateFormat dateFormat = new SimpleDateFormat( "dd/MM/yyyy" );
			textDate.setText( dateFormat.format( date ) );

		}
	}

	/**
	 * @return the textDays
	 */
	public Text getTextDays() {
		return textDays;
	}

	/**
	 * @param textDays the textDays to set
	 */
	public void setTextDays( Text textDays ) {
		this.textDays = textDays;
	}

	/**
	 * @return the textDate
	 */
	public Text getTextDate() {
		return textDate;
	}

	/**
	 * @param textDate the textDate to set
	 */
	public void setTextDate( Text textDate ) {
		this.textDate = textDate;
	}

	/**
	 * @return the dateTime
	 */
	public DateTime getDateTime() {
		return dateTime;
	}

	/**
	 * @param dateTime the dateTime to set
	 */
	public void setDateTime( DateTime dateTime ) {
		this.dateTime = dateTime;
	}

}
