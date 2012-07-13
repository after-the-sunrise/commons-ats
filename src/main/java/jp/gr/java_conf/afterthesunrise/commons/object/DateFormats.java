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
					return null;
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

		if (StringUtils.isBlank(format)) {
			return null;
		}

		DateFormat raw = getRaw(format);

		if (raw == null) {
			return null;
		}

		// Defensive copy
		return (DateFormat) raw.clone();

	}

	private static DateFormat getRaw(String format) {

		if (StringUtils.isBlank(format)) {
			return null;
		}

		return LOCAL.get().getUnchecked(format.intern());

	}

	public static Date format(String format, String value) {
		return format(format, value, null);
	}

	public static Date format(String format, String value, TimeZone timeZone) {

		if (StringUtils.isBlank(value)) {
			return null;
		}

		DateFormat dateFormat = getRaw(format);

		if (dateFormat == null) {
			return null;
		}

		if (timeZone == null) {
			dateFormat.setTimeZone(TimeZone.getDefault());
		} else {
			dateFormat.setTimeZone(timeZone);
		}

		try {
			return dateFormat.parse(value);
		} catch (ParseException e) {
			return null;
		}

	}

}
