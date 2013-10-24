package mx.com.lux.control.trabajos.util;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static String formatDate( Date date, String format ) {
        if ( date != null && StringUtils.isNotBlank( format ) ) {
            return new SimpleDateFormat( format ).format( date );
        }
        return "";
    }

    public static DateTime addDays( Date date, Integer days ) {
        date = org.apache.commons.lang.time.DateUtils.addDays( new Date(), days );
        DateTime realDate = new DateTime( date );
        return realDate;
    }

    public static DateTime addDays( Date date, String days ) {
        date = org.apache.commons.lang.time.DateUtils.addDays( new Date(), Integer.parseInt( days ) );
        DateTime realDate = new DateTime( date );
        return realDate;
    }

    public static String builToTimestampFormat( Integer year, Integer months, Integer days, Integer hours, Integer minutes, Integer seconds ) {
        String fechaInTimestampFormat = year + "-" + ( ( ( months + 1 ) < 10 ) ? "0" : "" ) + ( months + 1 ) + "-" + ( ( days < 10 ) ? "0" : "" ) + days + " " + hours + ":" + minutes + ":" + seconds;
        return fechaInTimestampFormat;
    }

    public static Integer daysBetweenCeilDayHours( Date fisrtDate, Date secondDate ) {
        DateTime today = new DateTime( org.apache.commons.lang.time.DateUtils.truncate( fisrtDate, Calendar.DAY_OF_MONTH ) );
        DateTime currentDaySelected = new DateTime( org.apache.commons.lang.time.DateUtils.truncate( secondDate, Calendar.DAY_OF_MONTH ) );
        Integer daysBetween = Days.daysBetween( today, currentDaySelected ).getDays();
        return daysBetween;
    }

    public static Integer isMayor( Date fisrtDate, Date secondDate ) {
        DateTime today = new DateTime( org.apache.commons.lang.time.DateUtils.truncate( fisrtDate, Calendar.DAY_OF_MONTH ) );
        DateTime currentDaySelected = new DateTime( org.apache.commons.lang.time.DateUtils.truncate( secondDate, Calendar.DAY_OF_MONTH ) );
        Integer daysBetween = Days.daysBetween( today, currentDaySelected ).getDays();
        return daysBetween;
    }
}
