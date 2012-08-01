package jp.gr.java_conf.afterthesunrise.commons.object;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * @author takanori.takase
 */
public final class DateFormats {

	private DateFormats() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS z";

	private static final TimeZone DEFAULT_TIMEZONE = TimeZone.getDefault();

	private static final DateFormat NULL = new SimpleDateFormat(DEFAULT_FORMAT);

	private static final TimeZone GMT = TimeZone.getTimeZone("GMT");

	private static final int MAX = 20;

	private static final long DURATION = 1L;

	private static final TimeUnit UNIT = TimeUnit.MINUTES;

	private static final ThreadLocal<LoadingCache<String, DateFormat>> LOCAL;

	static {

		final CacheLoader<String, DateFormat> loader = new CacheLoader<String, DateFormat>() {
			@Override
			public DateFormat load(String key) throws Exception {
				try {
					return new SimpleDateFormat(key);
				} catch (Exception e) {
					return NULL;
				}
			}
		};

		LOCAL = new ThreadLocal<LoadingCache<String, DateFormat>>() {
			@Override
			protected LoadingCache<String, DateFormat> initialValue() {

				return CacheBuilder.newBuilder()
						.expireAfterAccess(DURATION, UNIT).maximumSize(MAX)
						.build(loader);

			}
		};

	}

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

		if (StringUtils.isBlank(format)) {
			return null;
		}

		DateFormat fmt = LOCAL.get().getUnchecked(format.intern());

		return fmt == NULL ? null : fmt;

	}

	public static Date parse(String value) {
		return parse(value, DEFAULT_FORMAT, DEFAULT_TIMEZONE);
	}

	public static Date parse(String value, String format) {
		return parse(value, format, DEFAULT_TIMEZONE);
	}

	public static Date parse(String value, String format, TimeZone timeZone) {

		if (StringUtils.isBlank(value) || timeZone == null) {
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
