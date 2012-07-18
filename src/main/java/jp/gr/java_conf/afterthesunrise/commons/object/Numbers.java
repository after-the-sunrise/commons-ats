package jp.gr.java_conf.afterthesunrise.commons.object;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;

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

	private static final TimeUnit UNIT = TimeUnit.MINUTES;

	private static final LoadingCache<String, BigDecimal> CACHE;

	private static final BigDecimal NULL = new BigDecimal("0.0");

	static {

		CacheLoader<String, BigDecimal> loader = new CacheLoader<String, BigDecimal>() {
			@Override
			public BigDecimal load(String key) throws Exception {
				try {
					return new BigDecimal(key);
				} catch (Exception e) {
					return NULL;
				}
			}
		};

		CACHE = CacheBuilder.newBuilder().expireAfterAccess(DURATION, UNIT)
				.maximumSize(MAX).build(loader);

	}

	public static final BigDecimal convert(String value) {
		return convert(value, null);
	}

	public static final BigDecimal convert(String value, BigDecimal defaultValue) {

		if (StringUtils.isBlank(value)) {
			return defaultValue;
		}

		BigDecimal cached = CACHE.getUnchecked(value.intern());

		return cached != NULL ? cached : defaultValue;

	}

	public static BigDecimal round(BigDecimal value, BigDecimal unit) {
		return round(value, unit, null);
	}

	public static BigDecimal round(BigDecimal value, BigDecimal unit,
			BigDecimal defaultValue) {

		if (value == null || unit == null || unit.signum() == 0) {
			return defaultValue;
		}

		BigDecimal quotient = value.divide(unit, 0, RoundingMode.DOWN);

		return unit.multiply(quotient);

	}

}
