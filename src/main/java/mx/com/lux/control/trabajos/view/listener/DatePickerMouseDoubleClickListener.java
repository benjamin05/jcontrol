package mx.com.lux.control.trabajos.view.listener;

import org.apache.commons.lang.time.DateUtils;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Text;
import org.joda.time.Days;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePickerMouseDoubleClickListener extends MouseAdapter {

	private Text textDays;
	private Text textDate;
	private DateTime dateTime;

	/**
	 * @param textDays
	 * @param textDate
	 * @param dateTime
	 */
	public DatePickerMouseDoubleClickListener( Text textDays, Text textDate, DateTime dateTime ) {
		super();
		this.textDays = textDays;
		this.textDate = textDate;
		this.dateTime = dateTime;
	}

	@Override
	public void mouseDoubleClick( MouseEvent e ) {
		dateTime.setVisible( false );
		String fecha = dateTime.getYear() + "-" + ( ( ( dateTime.getMonth() + 1 ) < 10 ) ? "0" : "" ) + ( dateTime.getMonth() + 1 ) + "-" + ( ( dateTime.getDay() < 10 ) ? "0" : "" ) + dateTime.getDay() + " " + dateTime.getHours() + ":" + dateTime.getMinutes() + ":" + dateTime.getSeconds();
		System.out.println( fecha );
		Timestamp timestamp = Timestamp.valueOf( fecha );
		SimpleDateFormat dateFormat = new SimpleDateFormat( "dd/MM/yyyy" );
		textDate.setText( dateFormat.format( timestamp ) );
		org.joda.time.DateTime today = new org.joda.time.DateTime( DateUtils.truncate( new Date(), Calendar.DAY_OF_MONTH ) );
		org.joda.time.DateTime currentDaySelected = new org.joda.time.DateTime( timestamp );
		Integer daysBetween = Days.daysBetween( today, currentDaySelected ).getDays();
		textDays.setText( daysBetween.toString() );
	}
}
