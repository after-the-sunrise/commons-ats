package info.after_sunrise.commons.base.object;

import static java.lang.String.format;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author takanori.takase
 */
public final class Validations {

	private Validations() {
		throw new IllegalAccessError("Utility class shouldn't be instantiated.");
	}

	private static IllegalArgumentException make(String msg, Object... args) {

		String message = format(msg, args);

		return new IllegalArgumentException(message);

	}

	public static <V> V checkNotNull(V value) {
		return checkNotNull(value, "Null parameter.");
	}

	public static <V> V checkNotNull(V value, String message) {

		if (value != null) {
			return value;
		}

		throw new IllegalArgumentException(message);

	}

	public static void checkTimestamps(long start, long end) {

		if (start <= end) {
			return;
		}

		String s = DateFormats.format(start);

		String e = DateFormats.format(end);

		throw make("End cannot be before start : %s - %s", s, e);

	}

	public static void checkDates(Long start, Long end) {

		checkNotNull(start, "Start cannot be null.");

		checkNotNull(end, "End cannot be null.");

		checkTimestamps(start, end);

	}

	public static void checkDates(Date start, Date end) {

		checkNotNull(start, "Start cannot be null.");

		checkNotNull(end, "End cannot be null.");

		checkTimestamps(start.getTime(), end.getTime());

	}

	public static int checkPort(int port) {

		if (port < 0 || 0xFFFF < port) {
			throw make("Invalid port : %s", port);
		}

		return port;

	}

	public static int checkNotNegative(int value) {

		if (value >= 0) {
			return value;
		}

		throw make("Value cannot be negative : %s", value);

	}

	public static long checkNotNegative(long value) {

		if (value >= 0L) {
			return value;
		}

		throw make("Value cannot be negative : %s", value);

	}

	public static Long checkNotNegative(Long value) {

		checkNotNull(value, "Value cannot be null.");

		checkNotNegative(value.longValue());

		return value; // Return same instance

	}

	public static BigDecimal checkNotNegative(BigDecimal value) {

		checkNotNull(value, "Value cannot be null.");

		if (value.signum() < 0) {
			throw make("Value cannot be negative : %s", value);
		}

		return value; // Return same instance

	}

	public static int checkPositive(int value) {

		if (value > 0L) {
			return value;
		}

		throw make("Value must be positive : %s", value);

	}

	public static long checkPositive(long value) {

		if (value > 0L) {
			return value;
		}

		throw make("Value must be positive : %s", value);

	}

	public static Long checkPositive(Long value) {

		checkNotNull(value, "Value cannot be null.");

		checkPositive(value.longValue());

		return value; // Return same instance

	}

	public static BigDecimal checkPositive(BigDecimal value) {

		checkNotNull(value, "Value cannot be null.");

		if (value.signum() <= 0) {
			throw make("Value must be positive : %s", value);
		}

		return value;

	}

	public static <T extends Comparable<T>> T checkRange(T val, T min, T max) {
		return checkRange(val, min, max, true, true);
	}

	public static <T extends Comparable<T>> T checkRange(T val, T min, T max,
			boolean minInclusive, boolean maxInclusive) {

		checkNotNull(val, "Value cannot be null.");

		checkNotNull(min, "Minimum cannot be null.");

		checkNotNull(max, "Maximum cannot be null.");

		if (minInclusive) {
			if (val.compareTo(min) < 0) {
				throw make("Value[%s] is less than min[%s]", val, min);
			}
		} else {
			if (val.compareTo(min) <= 0) {
				throw make("Value[%s] must be greater than min[%s]", val, min);
			}
		}

		if (maxInclusive) {
			if (val.compareTo(max) > 0) {
				throw make("Value[%s] is greater than max[%s]", val, max);
			}
		} else {
			if (val.compareTo(max) >= 0) {
				throw make("Value[%s] must be smaller than max[%s]", val, max);
			}
		}

		return val;

	}

}
