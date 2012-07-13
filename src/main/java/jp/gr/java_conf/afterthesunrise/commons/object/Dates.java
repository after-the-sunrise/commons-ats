package jp.gr.java_conf.afterthesunrise.commons.object;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author takanori.takase
 */
public class Dates {

	private Dates() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	private static final ThreadLocal<Calendar> CALS = new ThreadLocal<Calendar>() {
		@Override
		protected Calendar initialValue() {
			return Calendar.getInstance();
		}
	};

	public static long adjustStartOfDay(long timestamp, TimeZone timeZone) {

		checkNotNull(timeZone, "TimeZone is null.");

		Calendar cal = CALS.get();

		cal.setTimeZone(timeZone);
		cal.setTimeInMillis(timestamp);

		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTimeInMillis();

	}

	public static long adjustEndOfDay(long timestamp, TimeZone timeZone) {

		long sod = adjustStartOfDay(timestamp, timeZone);

		Calendar cal = CALS.get();

		cal.setTimeInMillis(sod);

		cal.add(Calendar.DATE, 1);

		return cal.getTimeInMillis() - 1L;

	}

	public static long swapTimeZone(long timestamp, TimeZone from, TimeZone to) {

		checkNotNull(from, "From-TimeZone is null.");

		checkNotNull(to, "To-TimeZone is null.");

		return timestamp + from.getOffset(timestamp) - to.getOffset(timestamp);

	}

	public static java.util.Date sanitize(Date date) {

		if (date == null) {
			return null;
		}

		if (date.getClass() == Date.class) {
			return date;
		}

		return new Date(date.getTime());

	}

	public static java.sql.Date toSqlDate(Date date) {

		if (date == null) {
			return null;
		}

		if (date.getClass() == java.sql.Date.class) {
			return (java.sql.Date) date;
		}

		return new java.sql.Date(date.getTime());

	}

	public static java.sql.Timestamp toSqlTime(Date date) {

		if (date == null) {
			return null;
		}

		if (date.getClass() == java.sql.Timestamp.class) {
			return (java.sql.Timestamp) date;
		}

		return new java.sql.Timestamp(date.getTime());

	}

}
