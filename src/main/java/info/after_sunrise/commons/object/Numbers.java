package info.after_sunrise.commons.object;

import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.DOWN;
import static java.util.concurrent.TimeUnit.MINUTES;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Optional;
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

	private static final int CACHE_MAX = 1000;

	private static final int CACHE_MINUTE = 3;

	private static final LoadingCache<String, Optional<Long>> CACHE_LONG;

	private static final LoadingCache<String, Optional<Double>> CACHE_DOUBLE;

	private static final LoadingCache<String, Optional<BigDecimal>> CACHE_DECIMAL;

	static {

		CACHE_LONG = CacheBuilder.newBuilder()
				.expireAfterAccess(CACHE_MINUTE, MINUTES)
				.maximumSize(CACHE_MAX)
				.build(new CacheLoader<String, Optional<Long>>() {
					@Override
					public Optional<Long> load(String key) {
						try {
							return Optional.of(Long.valueOf(key));
						} catch (NumberFormatException e) {
							return Optional.absent();
						}
					}
				});

		CACHE_DOUBLE = CacheBuilder.newBuilder()
				.expireAfterAccess(CACHE_MINUTE, MINUTES)
				.maximumSize(CACHE_MAX)
				.build(new CacheLoader<String, Optional<Double>>() {
					@Override
					public Optional<Double> load(String key) {
						try {
							return Optional.of(Double.valueOf(key));
						} catch (NumberFormatException e) {
							return Optional.absent();
						}
					}
				});

		CACHE_DECIMAL = CacheBuilder.newBuilder()
				.expireAfterAccess(CACHE_MINUTE, MINUTES)
				.maximumSize(CACHE_MAX)
				.build(new CacheLoader<String, Optional<BigDecimal>>() {
					@Override
					public Optional<BigDecimal> load(String key) {
						try {
							return Optional.of(new BigDecimal(key));
						} catch (NumberFormatException e) {
							return Optional.absent();
						}
					}
				});

	}

	public static Long convertLong(String value) {
		return convertLong(value, null);
	}

	public static Long convertLong(String value, Long defaultValue) {

		if (StringUtils.isBlank(value)) {
			return defaultValue;
		}

		Optional<Long> cached = CACHE_LONG.getUnchecked(value);

		return cached.isPresent() ? cached.get() : defaultValue;

	}

	public static Double convertDouble(String value) {
		return convertDouble(value, null);
	}

	public static Double convertDouble(String value, Double defaultValue) {

		if (StringUtils.isBlank(value)) {
			return defaultValue;
		}

		Optional<Double> cached = CACHE_DOUBLE.getUnchecked(value);

		return cached.isPresent() ? cached.get() : defaultValue;

	}

	public static BigDecimal convert(String value) {
		return convert(value, null);
	}

	public static BigDecimal convert(String value, BigDecimal defaultValue) {

		if (StringUtils.isBlank(value)) {
			return defaultValue;
		}

		Optional<BigDecimal> cached = CACHE_DECIMAL.getUnchecked(value);

		return cached.isPresent() ? cached.get() : defaultValue;

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
