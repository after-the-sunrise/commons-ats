package com.after_sunrise.commons.base.object;

import static com.after_sunrise.commons.base.object.References.referecenCache;
import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.DOWN;

import java.math.BigDecimal;

import com.after_sunrise.commons.base.bean.Reference;
import com.after_sunrise.commons.base.bean.ReferenceCache;

/**
 * @author takanori.takase
 */
public final class Numbers {

	private Numbers() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	private static final int MAX = 1000;

	private static final ReferenceCache<String, Long> CACHE_LONG = referecenCache(MAX);

	private static final ReferenceCache<String, Double> CACHE_DOUBLE = referecenCache(MAX);

	private static final ReferenceCache<String, BigDecimal> CACHE_DECIMAL = referecenCache(MAX);

	public static Long convertLong(String value) {
		return convertLong(value, null);
	}

	public static Long convertLong(String value, Long defaultValue) {

		if (value == null || value.isEmpty()) {
			return defaultValue;
		}

		Reference<Long> ref = CACHE_LONG.get(value);

		Long val;

		if (ref == null) {

			try {
				val = Long.valueOf(value);
			} catch (NumberFormatException e) {
				val = null;
			}

			CACHE_LONG.put(value, val);

		} else {

			val = ref.get();

		}

		return val != null ? val : defaultValue;

	}

	public static Double convertDouble(String value) {
		return convertDouble(value, null);
	}

	public static Double convertDouble(String value, Double defaultValue) {

		if (value == null || value.isEmpty()) {
			return defaultValue;
		}

		Reference<Double> ref = CACHE_DOUBLE.get(value);

		Double val;

		if (ref == null) {

			try {
				val = Double.valueOf(value);
			} catch (NumberFormatException e) {
				val = null;
			}

			CACHE_DOUBLE.put(value, val);

		} else {

			val = ref.get();

		}

		return val != null ? val : defaultValue;

	}

	public static BigDecimal convert(String value) {
		return convert(value, null);
	}

	public static BigDecimal convert(String value, BigDecimal defaultValue) {

		if (value == null || value.isEmpty()) {
			return defaultValue;
		}

		Reference<BigDecimal> ref = CACHE_DECIMAL.get(value);

		BigDecimal val;

		if (ref == null) {

			try {
				val = new BigDecimal(value);
			} catch (NumberFormatException e) {
				val = null;
			}

			CACHE_DECIMAL.put(value, val);

		} else {

			val = ref.get();

		}

		return val != null ? val : defaultValue;
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
