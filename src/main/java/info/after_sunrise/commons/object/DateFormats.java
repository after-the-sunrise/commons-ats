package info.after_sunrise.commons.object;

import static java.util.concurrent.TimeUnit.MINUTES;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Optional;
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

	private static final TimeZone GMT = TimeZone.getTimeZone("GMT");

	private static final int CACHE_MAX = 20;

	private static final long CACHE_MINUTE = 3L;

	private static final ThreadLocal<LoadingCache<String, Optional<DateFormat>>> LOCAL;

	static {

		final CacheLoader<String, Optional<DateFormat>> loader = new CacheLoader<String, Optional<DateFormat>>() {
			@Override
			public Optional<DateFormat> load(String key) throws Exception {
				try {
					return Optional.<DateFormat> of(new SimpleDateFormat(key));
				} catch (Exception e) {
					return Optional.absent();
				}
			}
		};

		LOCAL = new ThreadLocal<LoadingCache<String, Optional<DateFormat>>>() {
			@Override
			protected LoadingCache<String, Optional<DateFormat>> initialValue() {
				return CacheBuilder.newBuilder().maximumSize(CACHE_MAX)
						.expireAfterAccess(CACHE_MINUTE, MINUTES).build(loader);
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

		return LOCAL.get().getUnchecked(format).orNull();

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
