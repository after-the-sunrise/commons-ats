package jp.gr.java_conf.afterthesunrise.commons.object;

import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.DOWN;
import static java.util.concurrent.TimeUnit.MINUTES;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * @author takanori.takase
 */
public final class Numbers {

	private Numbers() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	private static final int MAX = 1000;

	private static final long DURATION = 1L;

	private static final LoadingCache<String, Long> CACHE_LONG;

	private static final Long NULL_LONG = new Long("0");

	private static final LoadingCache<String, Double> CACHE_DOUBLE;

	private static final Double NULL_DOUBLE = new Double("0.0");

	private static final LoadingCache<String, BigDecimal> CACHE_DECIMAL;

	private static final BigDecimal NULL_DECIMAL = new BigDecimal("0.0");

	static {

		CACHE_LONG = CacheBuilder.newBuilder()
				.expireAfterAccess(DURATION, MINUTES).maximumSize(MAX)
				.build(new CacheLoader<String, Long>() {
					@Override
					public Long load(String key) throws Exception {
						try {
							return Long.valueOf(key);
						} catch (Exception e) {
							return NULL_LONG;
						}
					}
				});

		CACHE_DOUBLE = CacheBuilder.newBuilder()
				.expireAfterAccess(DURATION, MINUTES).maximumSize(MAX)
				.build(new CacheLoader<String, Double>() {
					@Override
					public Double load(String key) throws Exception {
						try {
							return Double.valueOf(key);
						} catch (Exception e) {
							return NULL_DOUBLE;
						}
					}
				});

		CACHE_DECIMAL = CacheBuilder.newBuilder()
				.expireAfterAccess(DURATION, MINUTES).maximumSize(MAX)
				.build(new CacheLoader<String, BigDecimal>() {
					@Override
					public BigDecimal load(String key) throws Exception {
						try {
							return new BigDecimal(key);
						} catch (Exception e) {
							return NULL_DECIMAL;
						}
					}
				});

	}

	private static <T> T getValue(T val, T nullVal, T defaultValue) {
		return val != nullVal ? val : defaultValue;
	}

	public static Long convertLong(String value) {
		return convertLong(value, null);
	}

	public static Long convertLong(String value, Long defaultValue) {

		if (StringUtils.isBlank(value)) {
			return defaultValue;
		}

		Long cached = CACHE_LONG.getUnchecked(value.intern());

		return getValue(cached, NULL_LONG, defaultValue);

	}

	public static Double convertDouble(String value) {
		return convertDouble(value, null);
	}

	public static Double convertDouble(String value, Double defaultValue) {

		if (StringUtils.isBlank(value)) {
			return defaultValue;
		}

		Double cached = CACHE_DOUBLE.getUnchecked(value.intern());

		return getValue(cached, NULL_DOUBLE, defaultValue);

	}

	public static BigDecimal convert(String value) {
		return convert(value, null);
	}

	public static BigDecimal convert(String value, BigDecimal defaultValue) {

		if (StringUtils.isBlank(value)) {
			return defaultValue;
		}

		BigDecimal cached = CACHE_DECIMAL.getUnchecked(value.intern());

		return getValue(cached, NULL_DECIMAL, defaultValue);

	}

	public static BigDecimal round(BigDecimal value) {
		return round(value, ONE, null);
	}

	public static BigDecimal round(BigDecimal value, BigDecimal unit) {
		return round(value, unit, null);
	}

	public static BigDecimal round(BigDecimal value, BigDecimal unit,
			BigDecimal defaultValue) {

		if (value == null || unit == null || unit.signum() == 0) {
			return defaultValue;
		}

		BigDecimal quotient = value.divide(unit, 0, DOWN);

		return unit.multiply(quotient);

	}

	public static String toPlainString(BigDecimal value) {
		return toPlainString(value, null);
	}

	public static String toPlainString(BigDecimal value, String nullvalue) {
		return value == null ? nullvalue : value.toPlainString();
	}

}
