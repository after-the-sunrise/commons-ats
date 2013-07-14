package info.after_sunrise.commons.base.object;

import info.after_sunrise.commons.base.bean.Reference;
import info.after_sunrise.commons.base.bean.ReferenceCache;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author takanori.takase
 */
public final class DateFormats {

	private DateFormats() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS z";

	private static final TimeZone DEFAULT_TIMEZONE = TimeZone.getDefault();

	private static final TimeZone GMT = TimeZone.getTimeZone("GMT");

	private static final ThreadLocal<ReferenceCache<String, DateFormat>> LOCAL = new ThreadLocal<ReferenceCache<String, DateFormat>>() {
		public ReferenceCache<String, DateFormat> initialValue() {
			return new ReferenceCache<String, DateFormat>();
		}
	};

	public static DateFormat get(String format) {

		DateFormat raw = getRaw(format);

		if (raw == null) {
			return null;
		}

		// Reset time zone
		raw.setTimeZone(DEFAULT_TIMEZONE);

		// Defensive copy
		return (DateFormat) raw.clone();

	}

	private static DateFormat getRaw(String format) {

		if (format == null || format.isEmpty()) {
			return null;
		}

		ReferenceCache<String, DateFormat> cache = LOCAL.get();

		Reference<DateFormat> reference = cache.get(format);

		DateFormat df;

		if (reference == null) {

			try {
				df = new SimpleDateFormat(format);
			} catch (IllegalArgumentException e) {
				df = null;
			}

			cache.put(format, df);

		} else {

			df = reference.get();

		}

		return df;

	}

	public static Date parse(String value) {
		return parse(value, DEFAULT_FORMAT, DEFAULT_TIMEZONE);
	}

	public static Date parse(String value, String format) {
		return parse(value, format, DEFAULT_TIMEZONE);
	}

	public static Date parse(String value, String format, TimeZone timeZone) {

		if (value == null || value.isEmpty() || timeZone == null) {
			return null;
		}

		DateFormat dateFormat = getRaw(format);

		if (dateFormat == null) {
			return null;
		}

		dateFormat.setTimeZone(timeZone);

		try {
			return dateFormat.parse(value);
		} catch (ParseException e) {
			return null;
		}

	}

	public static String format(Long value) {
		return format(value, DEFAULT_FORMAT, DEFAULT_TIMEZONE);
	}

	public static String format(Long value, String format) {
		return format(value, format, DEFAULT_TIMEZONE);
	}

	public static String format(Long value, TimeZone timeZone) {
		return format(value, DEFAULT_FORMAT, timeZone);
	}

	public static String format(Long value, String format, TimeZone timeZone) {
		return format(value == null ? null : new Date(value), format, timeZone);
	}

	public static String format(Date value) {
		return format(value, DEFAULT_FORMAT, DEFAULT_TIMEZONE);
	}

	public static String format(Date value, String format) {
		return format(value, format, DEFAULT_TIMEZONE);
	}

	public static String format(Date value, TimeZone timeZone) {
		return format(value, DEFAULT_FORMAT, timeZone);
	}

	public static String format(Date value, String format, TimeZone timeZone) {

		if (value == null || timeZone == null) {
			return null;
		}

		DateFormat dateFormat = getRaw(format);

		if (dateFormat == null) {
			return null;
		}

		dateFormat.setTimeZone(timeZone);

		return dateFormat.format(value);

	}

	public static String formatGMT(Long value) {
		return format(value, DEFAULT_FORMAT, GMT);
	}

	public static String formatGMT(Long value, String format) {
		return format(value, format, GMT);
	}

	public static String formatGMT(Date value) {
		return format(value, DEFAULT_FORMAT, GMT);
	}

	public static String formatGMT(Date value, String format) {
		return format(value, format, GMT);
	}

}
