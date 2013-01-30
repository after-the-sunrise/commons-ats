package jp.gr.java_conf.afterthesunrise.commons.time;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

import java.io.Serializable;

/**
 * @author takanori.takase
 */
public class TimeRange implements Serializable, Comparable<TimeRange> {

	private static final long serialVersionUID = -7007270029824693640L;

	private final Time start;

	private final Time end;

	public TimeRange(Time start, Time end) {
		this.start = checkNotNull(start);
		this.end = checkNotNull(end);
	}

	@Override
	public String toString() {
		return format("%s-%s", start, end);
	}

	@Override
	public int hashCode() {
		return start.hashCode() * end.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof TimeRange) {
			return compareTo((TimeRange) o) == 0;
		}
		return false;
	}

	@Override
	public int compareTo(TimeRange o) {

		int comparison = start.compareTo(o.start);

		if (comparison == 0) {
			comparison = end.compareTo(o.end);
		}

		return comparison;

	}

	public Time getStart() {
		return start;
	}

	public Time getEnd() {
		return end;
	}

	public boolean inRange(Time value) {
		return inRange(value, true, false);
	}

	public boolean inRange(Time value, boolean includeStart, boolean includeEnd) {

		if (value == null) {
			return false;
		}

		// Handle overnight time (cf: 16:00 ~ 01:30)
		if (start.compareTo(end) > 0) {

			int s = start.compareTo(value);

			if (includeStart) {
				if (0 >= s) {
					return true;
				}
			} else {
				if (0 > s) {
					return true;
				}
			}

			int e = value.compareTo(end);

			if (includeEnd) {
				if (0 >= e) {
					return true;
				}
			} else {
				if (0 > e) {
					return true;
				}
			}

			return false;

		}

		int s = start.compareTo(value);

		int e = value.compareTo(end);

		if (includeStart) {
			if (includeEnd) {
				return s <= 0 && e <= 0;
			} else {
				return s <= 0 && e < 0;
			}
		} else {
			if (includeEnd) {
				return s < 0 && e <= 0;
			} else {
				return s < 0 && e < 0;
			}
		}

	}

}
