package com.after_sunrise.commons.base.time;

import static java.lang.String.format;

import java.io.Serializable;
import java.util.TimeZone;

/**
 * @author takanori.takase
 */
public class ZoneTimeRange implements Serializable, Comparable<ZoneTimeRange> {

	private static final long serialVersionUID = -7007270029824693640L;

	private final TimeZone timeZone;

	private final ZoneTime start;

	private final ZoneTime end;

	public ZoneTimeRange(TimeZone tz, Time start, Time end) {
		this.timeZone = (TimeZone) tz.clone();
		this.start = new ZoneTime(start, tz);
		this.end = new ZoneTime(end, tz);
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
		if (o instanceof ZoneTimeRange) {
			return compareTo((ZoneTimeRange) o) == 0;
		}
		return false;
	}

	@Override
	public int compareTo(ZoneTimeRange o) {

		int comparison = start.compareTo(o.start);

		if (comparison != 0) {
			return comparison;
		}

		return end.compareTo(o.end);

	}

	public TimeZone getTimeZone() {
		return (TimeZone) timeZone.clone();
	}

	public String getTimeZoneId() {
		return timeZone.getID();
	}

	public Time getStart() {
		return start.getTime();
	}

	public Time getEnd() {
		return end.getTime();
	}

	public boolean inRange(long value) {
		return inRange(value, true, false);
	}

	public boolean inRange(long value, boolean includeStart, boolean includeEnd) {

		long s = start.adjust(value);

		long e = end.adjust(value);

		// Handle overnight time (cf: 16:00 ~ 01:30)
		if (e < s) {

			if (includeStart) {
				if (s <= value) {
					return true;
				}
			} else {
				if (s < value) {
					return true;
				}
			}

			if (includeEnd) {
				if (value <= e) {
					return true;
				}
			} else {
				if (value < e) {
					return true;
				}
			}

			return false;

		}

		if (includeStart) {
			if (includeEnd) {
				return s <= value && value <= e;
			} else {
				return s <= value && value < e;
			}
		} else {
			if (includeEnd) {
				return s < value && value <= e;
			} else {
				return s < value && value < e;
			}
		}

	}

}
